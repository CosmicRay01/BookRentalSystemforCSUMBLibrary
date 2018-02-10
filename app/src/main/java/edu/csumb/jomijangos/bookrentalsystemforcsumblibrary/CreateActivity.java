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
import java.util.Date;
import java.util.List;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {
    boolean wrongInput = false;
    private AccountDbHelper mHelper​;
    private TransactionDbHelper tHelper;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Intent intent = getIntent();
        // Click Listeners
        View confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(this);
        // Database connection
        mHelper​ = new AccountDbHelper(this);
        tHelper= new TransactionDbHelper(this);
    }

    // Open create account activity
    public void openMainActivity(View view) {
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
                String username = usernameInput.getText().toString();
                EditText passwordInput = (EditText)findViewById(R.id.password_input);
                String password = passwordInput.getText().toString();
                // Verify username and password
                if(!verify(username)){
                    String text = "Incorrect Format";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    if(wrongInput) {
                        openMainActivity(view);
                        break;
                    }
                    wrongInput = true;
                    break;
                }
                if(!verify(password)){
                    String text = "Incorrect Format";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    if(wrongInput) {
                        openMainActivity(view);
                        break;
                    }
                    wrongInput = true;
                    break;
                }
                if(!checkDuplicate(username)) {
                    String text = "Username Taken";
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
                mHelper​.addAccount(account);
                String type = "Account Creation";
                Date date = new Date();
                Transaction transaction = new Transaction(type, username, date.toString());
                tHelper.addTransaction(transaction);
                String text = "Account Created!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                openMainActivity(view);
                break;
        }
    }

    // Verify string has at least one special symbol, one number, and three letters
    public boolean verify(String str) {
        boolean foundSpecial = false;
        boolean foundNum = false;
        int lettersFound = 0;
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == '!' || str.charAt(i) == '@' ||
                    str.charAt(i) == '#' || str.charAt(i) == '$') {
                foundSpecial = true;
            }
            if(Character.isDigit(str.charAt(i))){
                foundNum = true;
            }
            if(Character.isAlphabetic(str.charAt(i))){
                lettersFound++;
            }
        }
        if(foundSpecial && foundNum && lettersFound >= 3) {
            return true;
        }
        return false;
    }

    public boolean checkDuplicate(String str) {
        List<Account> TransactionList = mHelper​.getAllAccounts();
        for (int i = 0; i < TransactionList.size(); i++){
            if (str.equals(TransactionList.get(i).getUsername())) {
                return false;
            }
        }
        return true;
    }
}