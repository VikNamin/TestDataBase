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
    EditText publisherEditText;
    Button updateButton;
    boolean onSave = false;

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
        nameTextView.setText(getIntent().getExtras().getString("name"));
        publisherNameTextView.setText(getIntent().getExtras().getString("publisherName"));
        authorTextView.setText(getIntent().getExtras().getString("author"));
        yearPublishingTextView.setText(String.valueOf(getIntent().getExtras().getInt("yearPublishing")));
        priceTextView.setText(Double.toString(getIntent().getExtras().getDouble("price")));
        shopAddressTextView.setText(getIntent().getExtras().getString("shopAddress"));
        availableTextView.setText(Boolean.toString(getIntent().getExtras().getBoolean("available")));
        amountNumTextView.setText(String.valueOf(getIntent().getExtras().getInt("amountNum")));
    }

    public void updateData(View view){
//        if (!onSave){
//            publisherNameTextView.setVisibility(View.GONE);
//            publisherEditText.setVisibility(View.VISIBLE);
//            publisherEditText.setText(publisherNameTextView.getText());
//
//            DatabaseReference reference = FirebaseDatabase.getInstance()
//                    .getReference(uid)
//                    .child("operations")
//                    .child(uidFromPush);
//
//            updateButton.setText("Save Data");
//            onSave = true;
//        }
//        else {
//            publisherEditText.setVisibility(View.GONE);
//            publisherNameTextView.setVisibility(View.VISIBLE);
//            publisherNameTextView.setText(publisherEditText.getText());
//
//            updateButton.setText("Update Data");
//            onSave = false;
//        }
    }
}