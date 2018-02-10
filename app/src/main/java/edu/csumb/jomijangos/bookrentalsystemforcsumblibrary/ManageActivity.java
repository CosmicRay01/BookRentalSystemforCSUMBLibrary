package edu.csumb.jomijangos.bookrentalsystemforcsumblibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

public class ManageActivity extends AppCompatActivity implements View.OnClickListener {
    boolean wrongInput = false;
    private AccountDbHelper mHelper​;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        Intent intent = getIntent();
        // Click Listeners
        View loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
        // Database connection
        mHelper​ = new AccountDbHelper(this);
    }

    // Open main activity
    public void openMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Open transactions activity
    public void openTransactionsActivity(View view) {
        Intent intent = new Intent(this, TransactionsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                // Sets toast
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;
                // Get username and password
                EditText usernameInput = (EditText)findViewById(R.id.username_input);
                String username = usernameInput.getText().toString();
                EditText passwordInput = (EditText)findViewById(R.id.password_input);
                String password = passwordInput.getText().toString();
                // Verify username and password
                if(!verify(username, password)){
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

                String text = "Success!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                openTransactionsActivity(view);
                break;
        }
    }

    public boolean verify(String username, String password) {
        if(username.equals("admin") && password.equals("secret")) {
            return true;
        }
        return false;
    }

}