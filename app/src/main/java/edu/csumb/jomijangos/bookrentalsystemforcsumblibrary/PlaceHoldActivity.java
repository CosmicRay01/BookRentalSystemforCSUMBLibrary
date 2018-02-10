package edu.csumb.jomijangos.bookrentalsystemforcsumblibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Math.abs;

public class PlaceHoldActivity extends AppCompatActivity implements View.OnClickListener {
    boolean wrongInput = false;
    private HoldDbHelper mHelper​;
    private TransactionDbHelper tHelper;
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm(a)");
    public static final String EXTRA_PICKUP = "";
    public static final String EXTRA_RETURN = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        Intent intent = getIntent();
        // Click Listeners
        View confirmButton = findViewById(R.id.place_button);
        confirmButton.setOnClickListener(this);
        // Database connection
        mHelper​ = new HoldDbHelper(this);
        tHelper = new TransactionDbHelper(this);
    }

    // Open create account activity
    public void openMainActivity(View view, String pickupDate, String returnDate) {
        Intent intent = new Intent(this, SelectBookActivity.class);
        intent.putExtra(EXTRA_PICKUP, pickupDate);
        intent.putExtra(EXTRA_RETURN, returnDate);
        startActivity(intent);
    }

    // Open create account activity
    public void openMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.place_button:
                // Sets toast
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                // Get username and password
                EditText usernameInput = (EditText) findViewById(R.id.pickup_input);
                String username = usernameInput.getText().toString();
                EditText passwordInput = (EditText) findViewById(R.id.return_input);
                String password = passwordInput.getText().toString();
                // Verify username and password
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = new SimpleDateFormat("MM/dd/yyyy hh:mm(a)").parse(username);
                    date2 = new SimpleDateFormat("MM/dd/yyyy hh:mm(a)").parse(password);
                } catch (ParseException e) {
                    String text = "Invalid format";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    if (wrongInput) {
                        openMainActivity(view);
                        break;
                    }
                    wrongInput = true;
                    break;

                }
                if (!verifyDate(date1, date2)) {
                    String text = "Dates can not be over a week apart";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    if (wrongInput) {
                        openMainActivity(view);
                        break;
                    }
                    wrongInput = true;
                    break;
                }
                Hold account = new Hold("title", "username", username, password);
                Log.d("new Account", account.toString());
                mHelper​.addHold(account);
                String type = "Place Hold";
                Date date = new Date();
                Transaction transaction = new Transaction(type, "username", date.toString());
                tHelper.addTransaction(transaction);
                String text = "Success!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                openMainActivity(view, dateFormat.format(date1), dateFormat.format(date1));
                break;
        }
    }

    // Verify string has at least one special symbol, one number, and three letters
    public boolean verifyDate(Date time1, Date time2) {
        int daysApart = (int)((time2.getTime() - time1.getTime()) / (1000*60*60*24l));
        if (abs(daysApart) > 7)
            return false;
        else {
            return true;
        }
    }
}