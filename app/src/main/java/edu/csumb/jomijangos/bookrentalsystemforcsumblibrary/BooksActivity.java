package edu.csumb.jomijangos.bookrentalsystemforcsumblibrary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity {
    private static final String TAG = "TransactionActivity";

    private BookDbHelper mHelper;
    private ListView TransactionView​;
    private ArrayAdapter<Book> tAdapter​;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        Intent intent = getIntent();
        // Database connection
        mHelper = new BookDbHelper(this);
        mHelper.getAllBooks();
        ListView TransactionView​ = (ListView) findViewById(R.id.list_book);

        List<Book> TransactionList = mHelper.getAllBooks();
        if (TransactionList.size() == 0) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("No books in Library")
                    .setMessage("Would you like to add one?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            openAddBookActivity();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();
        }

        if (tAdapter​ == null) {
            // attach adapter to ListView
            tAdapter​ = new ArrayAdapter<>(this, R.layout.item_book,
                    R.id.book, TransactionList);
            TransactionView​.setAdapter(tAdapter​);
        } else {
            tAdapter​.clear();
            tAdapter​.addAll(TransactionList);
            tAdapter​.notifyDataSetChanged();
        }
    }

    // Open add book activity
    public void openAddBookActivity() {
        Intent intent = new Intent(this, AddBookActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_book:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new Book")
                        .setMessage("Ready to input book information?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                openAddBookActivity();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}