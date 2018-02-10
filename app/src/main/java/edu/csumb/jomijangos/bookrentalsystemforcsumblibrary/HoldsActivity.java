package edu.csumb.jomijangos.bookrentalsystemforcsumblibrary;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HoldsActivity extends AppCompatActivity {
    private static final String TAG = "TransactionActivity";

    private TransactionDbHelper mHelper;
    private HoldDbHelper hHelper;
    private ListView TransactionView​;
    private ArrayAdapter<Hold> tAdapter​;
    private String username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holds);
        Intent intent = getIntent();
        username = intent.getStringExtra(SignInActivity.EXTRA_MESSAGE);
        // Database connection
        mHelper = new TransactionDbHelper(this);
        mHelper.getAllTransactions();

        hHelper = new HoldDbHelper(this);
        hHelper.getAllHolds(username);

        ListView TransactionView​ = (ListView) findViewById(R.id.list_hold);

        List<Hold> TransactionList = hHelper.getAllHolds(username);
        if (TransactionList.size() == 0) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("No holds placed with this account")
                    .setMessage("Would you like exit?")
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
            tAdapter​ = new ArrayAdapter<>(this, R.layout.item_hold,
                    R.id.hold, TransactionList);
            TransactionView​.setAdapter(tAdapter​);
        } else {
            tAdapter​.clear();
            tAdapter​.addAll(TransactionList);
            tAdapter​.notifyDataSetChanged();
        }
    }

    // Open create account activity
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void deleteTask (View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.hold);

        String task = String.valueOf(taskTextView.getText());
        int start = 3;
        int end = task.indexOf("\n");
        task = task.substring(start, end);


        String str = "";
        List <Hold> myT = hHelper.getAllHolds(username);
        str = hHelper.getHold(task).toString();



        hHelper.deleteById(task);

        String type = "Hold Cancellation" + "\n     " + str;
        Date date = new Date();
        Transaction transaction = new Transaction(type, username, date.toString());
        mHelper.addTransaction(transaction);

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        String text = "Hold Canceled!";
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        List<Hold> taskList = hHelper.getAllHolds(username);
        if(tAdapter​ == null) {
// attach adapter to ListView
            tAdapter​ = new ArrayAdapter<>(this, R.layout.item_hold,
                    R.id.hold, taskList);
            TransactionView​.setAdapter(tAdapter​);
        } else {
            tAdapter​.clear();
            tAdapter​.addAll(taskList);
            tAdapter​.notifyDataSetChanged();
        }
    }
}