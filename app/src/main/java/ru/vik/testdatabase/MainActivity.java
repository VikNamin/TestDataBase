package ru.vik.testdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //TODO Реализовать проверку при создании. Добавить Toast при удалении.
    // Распределить EditView по типам данных. Переделать поле available в checkBox.
    // Сделать более красивый RecyclerView. Доработать интерфейс.
    protected static String collectionName = "books";
    public static ArrayList<Book> books = new ArrayList<Book>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    BookAdapter.OnBookClickListener bookClickListener;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        bookClickListener = new BookAdapter.OnBookClickListener() {
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

                        recyclerView.setAdapter(new BookAdapter(MainActivity.this, books, bookClickListener));
                    }
                });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                books = new ArrayList<Book>();
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
                                recyclerView.setAdapter(new BookAdapter(MainActivity.this, books, bookClickListener));
                                recyclerView.invalidate();
                            }
                        });
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void createBook(View view) {
        Intent intent = new Intent(MainActivity.this, CreateBookActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart(){
        recyclerView.setAdapter(new BookAdapter(MainActivity.this, books, bookClickListener));
        recyclerView.invalidate();
        super.onRestart();
    }
}


