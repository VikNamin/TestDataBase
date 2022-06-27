package ru.vik.testdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //TODO Реализовать добавление, удаление, изменение данных. Сделать более красивый RecyclerView. Переделать архитектуру БД.
    protected static String collectionName = "books";
    public static ArrayList<Book> books = new ArrayList<Book>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Book book = new Book("Понедельник начинается в субботу", "АСТ", "Стругацкий Б.Н.", 1965, 3.99, "ТРЦ «Ярмарка», пл. Вокзальная, 13", true, 10);
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("books").document().set(book);

        db.collection(collectionName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Book book = document.toObject(Book.class);
                            book.setUid(document.getId());
                            books.add(book);
                        }
                        BookAdapter.OnBookClickListener bookClickListener = new BookAdapter.OnBookClickListener() {
                            @Override
                            public void onBookClick(Book book, int position) {
                                Intent intent = new Intent(MainActivity.this, FullBookActivity.class);
                                intent.putExtra("name", book.getName());
                                intent.putExtra("publisherName", book.getPublisherName());
                                intent.putExtra("author", book.getAuthor());
                                intent.putExtra("yearPublishing", book.getYearPublishing());
                                intent.putExtra("price", book.getPrice());
                                intent.putExtra("shopAddress", book.getShopAddress());
                                intent.putExtra("available", book.isAvailable());
                                intent.putExtra("amountNum", book.getAmountNum());

                                intent.putExtra("uid", book.getUid());

                                startActivity(intent);
                            }
                        };
                        RecyclerView recyclerView = findViewById(R.id.list);
                        // создаем адаптер
                        BookAdapter adapter = new BookAdapter(MainActivity.this, books, bookClickListener);
                        // устанавливаем для списка адаптер
                        recyclerView.setAdapter(adapter);
                    }
                });
    }

    public void toAdmin(View view){
        Intent intent = new Intent(this, BookAddActivity.class);
        startActivity(intent);
    }
}


