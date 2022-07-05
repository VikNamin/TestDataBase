package ru.vik.testdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CreateBookActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<EditText> checkerList = new ArrayList<>();
    EditText fullNameCreateEditText, publisherCreateEditText, authorCreateEditText, yearCreateEditText, priceCreateEditText, addressCreateEditText, amountNumCreateEditText;
    CheckBox availableCreateCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);
        fullNameCreateEditText = (EditText) findViewById(R.id.fullNameCreateEditText);
        publisherCreateEditText = (EditText) findViewById(R.id.publisherCreateEditText);
        authorCreateEditText = (EditText) findViewById(R.id.authorCreateEditText);
        yearCreateEditText = (EditText) findViewById(R.id.yearCreateEditText);
        priceCreateEditText = (EditText) findViewById(R.id.priceCreateEditText);
        addressCreateEditText = (EditText) findViewById(R.id.addressCreateEditText);
        availableCreateCheckBox = (CheckBox) findViewById(R.id.availableCreateCheckBox);
        amountNumCreateEditText = (EditText) findViewById(R.id.amountNumCreateEditText);

        checkerList.add(yearCreateEditText);
        checkerList.add(priceCreateEditText);
        checkerList.add(amountNumCreateEditText);
    }

    public void createNewBook(View view){
        if(!isNormal()){
            return;
        }
        Book book = new Book(
                fullNameCreateEditText.getText().toString(),
                publisherCreateEditText.getText().toString(),
                authorCreateEditText.getText().toString(),
                Integer.parseInt(yearCreateEditText.getText().toString()),
                Double.parseDouble(priceCreateEditText.getText().toString()),
                addressCreateEditText.getText().toString(),
                Boolean.parseBoolean(availableCreateCheckBox.getText().toString()),
                Integer.parseInt(amountNumCreateEditText.getText().toString()));
        db.collection(MainActivity.collectionName).document().set(book);
        Toast.makeText(this, "Книга " + book.getName() + " успешно создана", Toast.LENGTH_SHORT).show();
    }

    private boolean isNormal(){
        for (TextView view : checkerList) {
            if (view.getText().toString().equals("")) {
                Toast.makeText(this, "Поле " + view.getHint()  + " не может быть пустым!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}