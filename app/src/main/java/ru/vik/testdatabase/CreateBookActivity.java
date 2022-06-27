package ru.vik.testdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class CreateBookActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText fullNameCreateEditText, publisherCreateEditText, authorCreateEditText, yearCreateEditText, priceCreateEditText, addressCreateEditText, availableCreateEditText, amountNumCreateEditText;

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
        availableCreateEditText = (EditText) findViewById(R.id.availableCreateEditText);
        amountNumCreateEditText = (EditText) findViewById(R.id.amountNumCreateEditText);

    }

    public void createNewBook(View view){
        Book book = new Book(
                fullNameCreateEditText.getText().toString(),
                publisherCreateEditText.getText().toString(),
                authorCreateEditText.getText().toString(),
                Integer.valueOf(yearCreateEditText.getText().toString()),
                Double.valueOf(priceCreateEditText.getText().toString()),
                addressCreateEditText.getText().toString(),
                Boolean.parseBoolean(availableCreateEditText.getText().toString()),
                Integer.valueOf(amountNumCreateEditText.getText().toString()));
        db.collection(MainActivity.collectionName).document().set(book);
        Toast.makeText(this, "Книга " + book.getName() + " успешно создана", Toast.LENGTH_SHORT).show();
    }
}