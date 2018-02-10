package edu.csumb.jomijangos.bookrentalsystemforcsumblibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    boolean wrongInput = false;
    private AccountDbHelper mHelper​;
    private HoldDbHelper tHelper;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private ArrayAdapter<Account> tAdapter​;
    public static final String EXTRA_MESSAGE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        // Click Listeners
        View confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(this);
        // Database connection
        mHelper​ = new AccountDbHelper(this);
        tHelper= new HoldDbHelper(this);
    }

    // Open create account activity
    public void openMainActivity(View view) {
        Intent intent = new Intent(this, CancelHoldActivity.class);
        startActivity(intent);
    }

    // Open create account activity
    public void openHoldsActivity(View view, String username) {
        Intent intent = new Intent(this, HoldsActivity.class);
        intent.putExtra(EXTRA_MESSAGE, username);
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
                String username = usernameInput.getText().toString();
                EditText passwordInput = (EditText)findViewById(R.id.password_input);
                String password = passwordInput.getText().toString();
                // Verify username and password

                if(!verify(username,password)){
                    String text = "Invalid Input";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    if(wrongInput) {
                        openMainActivity(view);
                        break;
                    }
                    wrongInput = true;
                    break;
                }
                Account account = new Account(username, password);
                Log.d ("new Account", account.toString());
                String text = "Signed In!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                openHoldsActivity(view, username);
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
}