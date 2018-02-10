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
import java.util.Collections;
import java.util.List;

public class TransactionsActivity extends AppCompatActivity {
    private static final String TAG = "TransactionActivity";

    private TransactionDbHelper mHelper;
    private ListView TransactionView​;
    private ArrayAdapter<Transaction> tAdapter​;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        Intent intent = getIntent();
        // Database connection
        mHelper = new TransactionDbHelper(this);
        mHelper.getAllTransactions();
        ListView TransactionView​ = (ListView) findViewById(R.id.list_transaction);

        List<Transaction> TransactionList = mHelper.getAllTransactions();


        if (TransactionList.size() == 0) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("No Transactions")
                    .setMessage("Woud you like to exit?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            openMainActivity();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();
        }
        Collections.reverse(TransactionList);

        if (tAdapter​ == null) {
            // attach adapter to ListView
            tAdapter​ = new ArrayAdapter<>(this, R.layout.item_transaction,
                    R.id.transaction_type, TransactionList);
            TransactionView​.setAdapter(tAdapter​);
        } else {
            tAdapter​.clear();
            tAdapter​.addAll(TransactionList);
            tAdapter​.notifyDataSetChanged();
        }
    }

    // Open main activity
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Open books activity
    public void openBooksActivity() {
        Intent intent = new Intent(this, BooksActivity.class);
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
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new Book")
                        .setMessage("What do you want open Book Manager?")
                        //.setView(taskEditText)
                        .setPositiveButton("Open", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String task = String.valueOf(taskEditText.getText());
                                Log.d(TAG, "Task to add: " + task);
                                openBooksActivity();
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

    public List <Transaction> cleanUp(List<Transaction> myList){
        String user = "username";
        for(int i = 0; i < myList.size(); i++) {
            if (myList.get(i).getUsername().equals(user)){
                myList.remove(i);
            }
        }
        return myList;
    }
}