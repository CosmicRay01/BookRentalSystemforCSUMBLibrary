package edu.csumb.jomijangos.bookrentalsystemforcsumblibrary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Click Listeners
        View createButton = findViewById(R.id.account_create);
        createButton.setOnClickListener(this);

        View holdButton = findViewById(R.id.hold_cancel);
        holdButton.setOnClickListener(this);

        View cancelButton = findViewById(R.id.hold_place);
        cancelButton.setOnClickListener(this);

        View manageButton = findViewById(R.id.system_manage);
        manageButton.setOnClickListener(this);
    }

    // Open create account activity
    public void openCreateActivity(View view) {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }

    // Open place hold activity
    public void openPlaceHoldActivity(View view) {
        Intent intent = new Intent(this, PlaceHoldActivity.class);
        startActivity(intent);
    }

    // Open cancel hold activity
    public void openCancelHoldActivity(View view) {
        Intent intent = new Intent(this, CancelHoldActivity.class);
        startActivity(intent);
    }

    // Open manage system activity
    public void openManageActivity(View view) {
        Intent intent = new Intent(this, ManageActivity.class);
        startActivity(intent);
    }

    // Open login system activity
    public void openLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // Open login system activity
    public void openSignInActivity(View view) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    // Each button opens a new activitiy
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.account_create:
                openCreateActivity(view);
                break;
            case R.id.hold_place:
                openPlaceHoldActivity(view);
                break;
            case R.id.hold_cancel:
                openSignInActivity(view);
                break;
            case R.id.system_manage:
                openManageActivity(view);
                break;
        }
    }
}
