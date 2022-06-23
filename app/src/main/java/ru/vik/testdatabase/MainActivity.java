package ru.vik.testdatabase;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button getButton;

    //TODO Реализовать вывод данных(в виде списка как это было в проекте парсера ЦБ), удаление данных, изменение данных.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getButton = (Button) findViewById(R.id.getButton);
        FirebaseFirestore db = FirebaseFirestore.getInstance();


    }

    public void createBook(View view){
        Book book = new Book("Вишнёвый сад", "АСТ", "Чехов А.П.", 1904, 2.99, "ТРЦ «Ярмарка», пл. Вокзальная, 13", true, 20);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("books").document(book.getName()).set(book);
    }

    public void getData(View view){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("books")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}