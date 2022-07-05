package ru.vik.testdatabase;

import static ru.vik.testdatabase.MainActivity.books;
import static ru.vik.testdatabase.MainActivity.collectionName;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;

public class FullBookActivity extends AppCompatActivity {

    TextView nameTextView, publisherNameTextView, authorTextView, yearPublishingTextView, priceTextView, shopAddressTextView, availableTextView, amountNumTextView;
    EditText publisherEditText, authorEditText, yearEditText, priceEditText, addressEditText, amountNumEditText;
    Button updateButton;
    CheckBox availableCheckBox;
    boolean onSave = false;
    private String uid;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<EditText> checkerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_book);

        updateButton  =  findViewById(R.id.updateButton);

        nameTextView =  findViewById(R.id.fullNameTextView);
        publisherNameTextView =  findViewById(R.id.publisherNameTextViewChosen);
        authorTextView =  findViewById(R.id.authorTextViewChosen);
        yearPublishingTextView =  findViewById(R.id.yearTextViewChosen);
        priceTextView =  findViewById(R.id.priceTextViewChosen);
        shopAddressTextView = findViewById(R.id.addressTextViewChosen);
        availableTextView =  findViewById(R.id.availableTextView);
        amountNumTextView =  findViewById(R.id.amountNumTextViewChosen);

        publisherEditText =  findViewById(R.id.publisherEditText);
        authorEditText =  findViewById(R.id.authorEditText);
        yearEditText =  findViewById(R.id.yearEditText);
        priceEditText =  findViewById(R.id.priceEditText);
        addressEditText =  findViewById(R.id.addressEditText);
        availableCheckBox =  findViewById(R.id.availableCheckBox);
        amountNumEditText =  findViewById(R.id.amountNumEditText);

        nameTextView.setText(getIntent().getExtras().getString("name"));
        publisherNameTextView.setText(getIntent().getExtras().getString("publisherName"));
        authorTextView.setText(getIntent().getExtras().getString("author"));
        yearPublishingTextView.setText(String.valueOf(getIntent().getExtras().getInt("yearPublishing")));
        priceTextView.setText(Double.toString(getIntent().getExtras().getDouble("price")));
        shopAddressTextView.setText(getIntent().getExtras().getString("shopAddress"));
        availableCheckBox.setChecked(getIntent().getExtras().getInt("amountNum") > 0);
        amountNumTextView.setText(String.valueOf(getIntent().getExtras().getInt("amountNum")));

        uid = getIntent().getExtras().getString("uid");

        checkerList.add(yearEditText);
        checkerList.add(priceEditText);
        checkerList.add(amountNumEditText);
    }

    public void updateData(View view){
        if (!onSave){

            publisherNameTextView.setVisibility(View.GONE);
            publisherEditText.setVisibility(View.VISIBLE);
            publisherEditText.setText(publisherNameTextView.getText());

            authorTextView.setVisibility(View.GONE);
            authorEditText.setVisibility(View.VISIBLE);
            authorEditText.setText(authorTextView.getText());

            yearPublishingTextView.setVisibility(View.GONE);
            yearEditText.setVisibility(View.VISIBLE);
            yearEditText.setText(yearPublishingTextView.getText());

            priceTextView.setVisibility(View.GONE);
            priceEditText.setVisibility(View.VISIBLE);
            priceEditText.setText(priceTextView.getText());

            shopAddressTextView.setVisibility(View.GONE);
            addressEditText.setVisibility(View.VISIBLE);
            addressEditText.setText(shopAddressTextView.getText());

            amountNumTextView.setVisibility(View.GONE);
            amountNumEditText.setVisibility(View.VISIBLE);
            amountNumEditText.setText(amountNumTextView.getText());

            updateButton.setText(R.string.saveButtonOnUpdate);
            onSave = true;
        }
        else {
            if(!isNormal()){
                return;
            }
            availableCheckBox.setChecked(Integer.parseInt(amountNumEditText.getText().toString()) > 0);

            updateDB();

            publisherEditText.setVisibility(View.GONE);
            publisherNameTextView.setVisibility(View.VISIBLE);
            publisherNameTextView.setText(publisherEditText.getText());

            authorEditText.setVisibility(View.GONE);
            authorTextView.setVisibility(View.VISIBLE);
            authorTextView.setText(authorEditText.getText());

            yearEditText.setVisibility(View.GONE);
            yearPublishingTextView.setVisibility(View.VISIBLE);
            yearPublishingTextView.setText(yearEditText.getText());

            priceEditText.setVisibility(View.GONE);
            priceTextView.setVisibility(View.VISIBLE);
            priceTextView.setText(priceEditText.getText());

            addressEditText.setVisibility(View.GONE);
            shopAddressTextView.setVisibility(View.VISIBLE);
            shopAddressTextView.setText(addressEditText.getText());

            amountNumEditText.setVisibility(View.GONE);
            amountNumTextView.setVisibility(View.VISIBLE);
            amountNumTextView.setText(amountNumEditText.getText());

            updateButton.setText(R.string.updateButtonOnUpdate);
            onSave = false;
        }
    }

    public void updateDB(){
        db
                .collection(collectionName)
                .document(uid)
                .update(
                        "amountNum", Integer.valueOf(amountNumEditText.getText().toString()),
                            "author", authorEditText.getText().toString(),
                            "available", Boolean.parseBoolean(availableCheckBox.getText().toString()),
                            "price", Double.valueOf(priceEditText.getText().toString()),
                            "publisherName", publisherEditText.getText().toString(),
                            "shopAddress", addressEditText.getText().toString(),
                            "yearPublishing", Integer.valueOf(yearEditText.getText().toString())
                );
        books = new ArrayList<>();
        db.collection(collectionName)
                .get()
                .addOnCompleteListener(task -> {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Book book = document.toObject(Book.class);
                        book.setUid(document.getId());
                        books.add(book);
                    }
                });
    }

    public void deleteBook(View view){
        db.collection(collectionName).document(uid)
                .delete()
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Книга " + nameTextView.getText() + " успешно удалена", Toast.LENGTH_SHORT).show());
    }

    private boolean isNormal(){
        for (TextView view : checkerList) {
            if (view.getText().toString().equals("")) {
                Toast.makeText(this, "Поле " + view.getText() + " не может быть пустым!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}