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

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SelectBookActivity extends AppCompatActivity {
    private static final String TAG = "TransactionActivity";
    public static final String FEE = "";

    private BookDbHelper mHelper;
    private HoldDbHelper hHelper;
    private ListView TransactionView​;
    private ArrayAdapter<Book> tAdapter​;
    private ArrayAdapter<Hold> hAdapter​;
    private String pickupDate;
    private String returnDate;
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm(a)");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_book);
        Intent intent = getIntent();

        pickupDate = intent.getStringExtra(PlaceHoldActivity.EXTRA_PICKUP);
        returnDate = intent.getStringExtra(PlaceHoldActivity.EXTRA_RETURN);

        // Database connection
        mHelper = new BookDbHelper(this);
        mHelper.getAllBooks();
        hHelper = new HoldDbHelper(this);
        hHelper.getAllHolds();
        ListView TransactionView​ = (ListView) findViewById(R.id.list_select_book);

        List<Book> TransactionList = mHelper.getAllBooks();
        List<Hold> HoldList = hHelper.getAllCompleteHolds();

        try {
            TransactionList = verify(pickupDate, returnDate, HoldList, TransactionList);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (TransactionList.size() == 0) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("No books available at this time.")
                    .setMessage("Would you like to Exit?")
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

        if (tAdapter​ == null) {
            // attach adapter to ListView
            tAdapter​ = new ArrayAdapter<>(this, R.layout.item_select_book,
                    R.id.select_book, TransactionList);
            TransactionView​.setAdapter(tAdapter​);
        } else {
            tAdapter​.clear();
            tAdapter​.addAll(TransactionList);
            tAdapter​.notifyDataSetChanged();
        }
    }

    // Open add book activity
    public void openAddBookActivity(String fee) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(FEE, fee);
        startActivity(intent);
    }

    // Open add book activity
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void deleteTask (View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.select_book);
        String task = String.valueOf(taskTextView.getText());
        int a = task.indexOf("$")+1;
        int b = task. indexOf("/hour");
        String fee = task.substring(a,b);
        int start = 7;
        int end = task.indexOf("\n");
        task = task.substring(start,end);

        List<Hold> list = hHelper.getAllHolds();
        Hold transaction = list.get(list.size()-1);
        transaction.setBookTitle(task);
        hHelper.addHold(transaction);
        openAddBookActivity(fee);
    }

    public List<Book> verify(String date1, String date2, List<Hold> TransactionList, List<Book> BookList) throws ParseException {
        String text = "";

        for (int i = 0; i < TransactionList.size(); i++) {
            if (overlap(date1, date2, TransactionList.get(i).getPickUpDate(), TransactionList.get(i).getReturnDate())) {
                for (int ii = 0; ii < BookList.size(); ii++) {
                    if (BookList.get(ii).getTitle().equals(TransactionList.get(i).getBookTitle())) {
                        //text += "Cant select book " + BookList.get(ii).getTitle() + "\n";
                        BookList.remove(ii);
                    }
                }
            }
            if (overlap(TransactionList.get(i).getPickUpDate(), TransactionList.get(i).getReturnDate(), date1, date2)) {
                for (int ii = 0; ii < BookList.size(); ii++) {
                    if (BookList.get(ii).getTitle().equals(TransactionList.get(i).getBookTitle())) {
                        //text += "Cant select book " + BookList.get(ii).getTitle() + "\n";
                        BookList.remove(ii);
                    }
                }
            }
        }
        return BookList;
    }

    boolean overlap(String start11, String end11, String start22, String end22){
        Date start1;
        Date end1;
        Date start2;
        Date end2;
        try {
            // Dates the user paced
            start1 = dateFormat.parse(start11);
            end1 = dateFormat.parse(end11);
            //Dates from current hold iteration
            start2 = dateFormat.parse(start22);
            end2 = dateFormat.parse(end22);
            //text = dateFormat.format(start1) + "\n" + end1.toString() + "\n" + start2.toString() + "\n" + end2.toString();
        } catch (ParseException e) {
            //text = "FAILURE";
            start1 = null;
            end1 = null;
            start2 = null;
            end2 = null;
            return false;
        }
        double s1 = start1.getTime();
        double e1 = end1.getTime();
        double s2 = start2.getTime();
        double e2 = end2.getTime();

        if (s1 < s2 && e2 < e1){
            return true;
        }

        if (s1 < s2 && e2 < e1){
            return true;
        }

        if(!((e1 < s2)||(e2 < s1))){
            return true;
        }
        return false;
    }
}