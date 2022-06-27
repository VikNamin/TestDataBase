package ru.vik.testdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class FullBookActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    TextView nameTextView, publisherNameTextView, authorTextView, yearPublishingTextView, priceTextView, shopAddressTextView, availableTextView, amountNumTextView;
    EditText publisherEditText, authorEditText, yearEditText, priceEditText, addressEditText, availableEditText, amountNumEditText;
    Button updateButton;
    boolean onSave = false;
    private String uid;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        mDatabase = FirebaseDatabase.getInstance().getReference(MainActivity.collectionName);
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_book);

        updateButton  = (Button) findViewById(R.id.updateButton);

        nameTextView = (TextView) findViewById(R.id.fullNameTextView);
        publisherNameTextView = (TextView) findViewById(R.id.publisherNameTextViewChosen);
        authorTextView = (TextView) findViewById(R.id.authorTextViewChosen);
        yearPublishingTextView = (TextView) findViewById(R.id.yearTextViewChosen);
        priceTextView = (TextView) findViewById(R.id.priceTextViewChosen);
        shopAddressTextView = (TextView) findViewById(R.id.addressTextViewChosen);
        availableTextView = (TextView) findViewById(R.id.availableTextViewChosen);
        amountNumTextView = (TextView) findViewById(R.id.amountNumTextViewChosen);

        publisherEditText = (EditText) findViewById(R.id.publisherEditText);
        authorEditText = (EditText) findViewById(R.id.authorEditText);
        yearEditText = (EditText) findViewById(R.id.yearEditText);
        priceEditText = (EditText) findViewById(R.id.priceEditText);
        addressEditText = (EditText) findViewById(R.id.addressEditText);
        availableEditText = (EditText) findViewById(R.id.availableEditText);
        amountNumEditText = (EditText) findViewById(R.id.amountNumEditText);

        nameTextView.setText(getIntent().getExtras().getString("name"));
        publisherNameTextView.setText(getIntent().getExtras().getString("publisherName"));
        authorTextView.setText(getIntent().getExtras().getString("author"));
        yearPublishingTextView.setText(String.valueOf(getIntent().getExtras().getInt("yearPublishing")));
        priceTextView.setText(Double.toString(getIntent().getExtras().getDouble("price")));
        shopAddressTextView.setText(getIntent().getExtras().getString("shopAddress"));
        availableTextView.setText(Boolean.toString(getIntent().getExtras().getBoolean("available")));
        amountNumTextView.setText(String.valueOf(getIntent().getExtras().getInt("amountNum")));

        uid = getIntent().getExtras().getString("uid");


    }

    public void updateData(View view){
        if (!onSave){
//            updateDB();


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

            availableTextView.setVisibility(View.GONE);
            availableEditText.setVisibility(View.VISIBLE);
            availableEditText.setText(availableTextView.getText());

            amountNumTextView.setVisibility(View.GONE);
            amountNumEditText.setVisibility(View.VISIBLE);
            amountNumEditText.setText(amountNumTextView.getText());

            System.out.println("Объект EditText1: " + amountNumEditText.getText());

            updateButton.setText("Save Data");
            onSave = true;
        }
        else {
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

            availableEditText.setVisibility(View.GONE);
            availableTextView.setVisibility(View.VISIBLE);
            availableTextView.setText(availableEditText.getText());

            amountNumEditText.setVisibility(View.GONE);
            amountNumTextView.setVisibility(View.VISIBLE);
            amountNumTextView.setText(amountNumEditText.getText());

            System.out.println("Объект EditText2: " + amountNumEditText.getText());

            updateButton.setText("Update Data");
            onSave = false;
        }
    }

    public void updateDB(){
        db
                .collection(MainActivity.collectionName)
                .document(uid)
                .update(
                        "amountNum", Integer.valueOf(amountNumEditText.getText().toString()),
                            "author", authorEditText.getText().toString(),
                            "available", Boolean.parseBoolean(availableEditText.getText().toString()),
                            "price", Double.valueOf(priceEditText.getText().toString()),
                            "publisherName", publisherEditText.getText().toString(),
                            "shopAddress", addressEditText.getText().toString(),
                            "yearPublishing", Integer.valueOf(yearEditText.getText().toString())
                );
    }
}