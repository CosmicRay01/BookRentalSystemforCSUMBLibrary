package edu.csumb.jomijangos.bookrentalsystemforcsumblibrary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    boolean wrongInput = false;
    private AccountDbHelper mHelper​;
    private HoldDbHelper tHelper;
    private TransactionDbHelper rHelper;
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm(a)");
    DecimalFormat dec = new DecimalFormat("#.00");
    private ArrayAdapter<Account> tAdapter​;
    private String username;
    String pickUpdate;
    String returnDate;
    String title;
    int reservationNum;
    double fee = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        String feeS = intent.getStringExtra(SelectBookActivity.FEE);
        fee = Double.parseDouble(feeS);
        // Click Listeners
        View confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(this);
        // Database connection
        mHelper​ = new AccountDbHelper(this);
        tHelper= new HoldDbHelper(this);
        rHelper = new TransactionDbHelper(this);
    }

    // Open create account activity
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_button:
                // Sets toast
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                // Get username and password
                EditText usernameInput = (EditText)findViewById(R.id.username_input);
                username = usernameInput.getText().toString();
                EditText passwordInput = (EditText)findViewById(R.id.password_input);
                String password = passwordInput.getText().toString();
                // Verify username and password

                if(!verify(username,password)){
                    String text = "Invalid Input";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    if(wrongInput) {
                        openMainActivity();
                        break;
                    }
                    wrongInput = true;
                    break;
                }
                Account account = new Account(username, password);
                Log.d ("new Account", account.toString());
                //mHelper​.addAccount(account);
                String type = "Account Creation";
                Date date = new Date();
                List<Hold> list = tHelper.getAllHolds();
                Hold transaction = list.get(list.size()-1);
                pickUpdate = transaction.getPickUpDate();
                returnDate = transaction.getReturnDate();
                title = transaction.getBookTitle();
                reservationNum = transaction.getId();

                Date date1;
                Date date2;
                try {
                    date1 = dateFormat.parse(pickUpdate);
                    date2 = dateFormat.parse(returnDate);
                } catch (ParseException e) {
                    date1 = null;
                    date2 = null;
                    e.printStackTrace();
                }

                double days = (double)( (date2.getTime() - date1.getTime()) / (1000 * 60 * 60));
                final double total = days * fee;

                AlertDialog dialog = new AlertDialog.Builder(this)

                        .setTitle("Confirm Reservation")
                        .setMessage("Username: " + username + "\nPickup date: "
                                + pickUpdate + "\nReturn date: " + returnDate
                                + "\nBook Title: " + title + "\nReservation Number: "
                                + reservationNum + "\nTotal Amount: $" + dec.format(total) + "\n")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Context context = getApplicationContext();
                                int duration = Toast.LENGTH_LONG;
                                List<Hold> list = tHelper.getAllHolds();
                                Hold transaction = list.get(list.size()-1);
                                transaction.setUsername(username);
                                String totalAmount = transaction.getReturnDate();
                                totalAmount += "\n       Total charge: $" + dec.format(total);
                                tHelper.addHold(transaction);
                                String type = "Hold Reservation"+ "\n       Pickup date: "
                                        + pickUpdate + "\n       Return date: " + returnDate
                                        + "\n       Book Title: " + title + "\n       Reservation Number: "
                                        + reservationNum + "\n       Total Amount: $" + dec.format(total);
                                Date date = new Date();
                                Transaction trans = new Transaction(type, username, date.toString());
                                rHelper.addTransaction(trans);
                                String text = "Hold Placed!";
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                openMainActivity();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                break;

        }
    }

    // Verify string has at least one special symbol, one number, and three letters
    public boolean verify(String username, String password) {
        List<Account> TransactionList = mHelper​.getAllAccounts();
        for (int i = 0; i < TransactionList.size(); i++){
            if (username.equals(TransactionList.get(i).getUsername())) {
                if (password.equals(TransactionList.get(i).getPassword())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static long getDayCount(String start, String end) {
        long diff = -1;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm(a)");
            Date dateStart = dateFormat.parse(start);
            Date dateEnd = dateFormat.parse(end);

            //time is always 00:00:00 so rounding should help to ignore the missing hour when going from winter to summer time as well as the extra hour in the other direction
            diff = Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000);
        } catch (Exception e) {
            //handle the exception according to your own situation
        }
        return diff;
    }
}