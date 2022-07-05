package ru.vik.testdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //TODO
    // Распределить String величины по values/strings
    // Релизовать поиск
    protected static String collectionName = "books";
    public static ArrayList<Book> books = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    BookAdapter.OnBookClickListener bookClickListener;
    RecyclerView recyclerView;
    BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        adapter = new BookAdapter(this, books, bookClickListener);

        bookClickListener = (book, position) -> {
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
        };

        db.collection(collectionName)
                .get()
                .addOnCompleteListener(task -> {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Book book = document.toObject(Book.class);
                        book.setUid(document.getId());
                        books.add(book);
                    }
                    adapter = new BookAdapter(MainActivity.this, books, bookClickListener);
                    recyclerView.setAdapter(adapter);
                });

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            books = new ArrayList<>();
            db.collection(collectionName)
                    .get()
                    .addOnCompleteListener(task -> {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Book book = document.toObject(Book.class);
                            book.setUid(document.getId());
                            books.add(book);
                        }
                        adapter = new BookAdapter(MainActivity.this, books, bookClickListener);
                        recyclerView.setAdapter(adapter);
                        recyclerView.invalidate();
                    });
            mSwipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        return true;
    }

    private void filter(String text){
        ArrayList<Book> filteredList = new ArrayList<>();

        for (Book book : books) {
            if (book.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(book);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(this, "Не найдено", Toast.LENGTH_SHORT).show();
        }
        else{
            adapter.filterList(filteredList);
        }
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


