package ru.vik.testdatabase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>{

    interface OnBookClickListener{
        void onBookClick(Book book, int position);
    }

    private final LayoutInflater inflater;
    private final List<Book> books;
    private final OnBookClickListener onClickListener;



    BookAdapter(Context context, List<Book> books, OnBookClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.books = books;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Book book = books.get(position);
        holder.nameView.setText(book.getName());
        holder.authorView.setText(book.getAuthor());
        holder.priceView.setText(String.valueOf(book.getPrice()));

        holder.itemView.setOnClickListener(v -> {
            // вызываем метод слушателя, передавая ему данные
            onClickListener.onBookClick(book, position);
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView, authorView, priceView;
        ViewHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.name);
            authorView = view.findViewById(R.id.author);
            priceView = view.findViewById(R.id.price);
        }
    }
}