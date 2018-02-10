package edu.csumb.jomijangos.bookrentalsystemforcsumblibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddBookActivity extends AppCompatActivity implements View.OnClickListener {
    boolean wrongInput = false;
    private BookDbHelper mHelper​;
    private TransactionDbHelper tHelper;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        Intent intent = getIntent();
        // Click Listeners
        View confirmButton = findViewById(R.id.add_button);
        confirmButton.setOnClickListener(this);
        // Database connection
        mHelper​ = new BookDbHelper(this);
        tHelper= new TransactionDbHelper(this);
    }

    // Open create account activity
    public void openBooksActivity(View view) {
        Intent intent = new Intent(this, BooksActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_button:
                // Sets toast
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                // Get username and password
                EditText titleInput = (EditText)findViewById(R.id.title_input);
                String title = titleInput.getText().toString();
                EditText authorInput = (EditText)findViewById(R.id.author_input);
                String author = authorInput.getText().toString();
                EditText ISBNInput = (EditText)findViewById(R.id.ISBN_input);
                String ISBN = ISBNInput.getText().toString();
                EditText feeInput = (EditText)findViewById(R.id.fee_input);
                String feeS = feeInput.getText().toString();
                double fee = Double.parseDouble(feeS);

                if(!verifyTitle(title) ){
                    String text = "Book Already in Library!";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    break;
                }

                Book account = new Book(title, author, ISBN, fee);
                Log.d ("new Account", account.toString());
                mHelper​.addBook(account);
                String type = "Add Book";
                Date date = new Date();
                Transaction transaction = new Transaction(type, "librarian", date.toString());
                tHelper.addTransaction(transaction);
                String text = "Book Added!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                openBooksActivity(view);
                break;
        }
    }

    public boolean verifyTitle(String str) {
        List<Book> TransactionList = mHelper​.getAllBooks();
        for (int i = 0; i < TransactionList.size(); i++){
            if (str.equals(TransactionList.get(i).getTitle())) {
                return false;
            }
        }
        return true;
    }
}
