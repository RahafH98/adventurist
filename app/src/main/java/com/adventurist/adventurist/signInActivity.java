package com.adventurist.adventurist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;

import java.util.Objects;

public class signInActivity extends AppCompatActivity {
    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Intent callingIntent = getIntent();
       String emailverified = callingIntent.getStringExtra(VerifyAccActivity.VERIFY_ACC_EMAIL_TAG);
        EditText emailEditText = (EditText) findViewById(R.id.emailSignInField);
       emailEditText.setText(emailverified);
        findViewById(R.id.loginBtn).setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = ((EditText) findViewById(R.id.passwordSignInfield)).getText().toString();

            Amplify.Auth.signIn(email, password, success -> {
                Log.i(TAG, "Login succeeded: " + success.toString());
                Intent goToHomePage = new Intent(signInActivity.this, MainActivity.class);
                startActivity(goToHomePage);
            }, fail -> {
                Log.i(TAG, "Login failed: " + fail.toString());
                runOnUiThread(() ->
                {
                    Toast.makeText(signInActivity.this, "Login failed", Toast.LENGTH_LONG);
                });
            });
        });
        findViewById(R.id.signUpBtn).setOnClickListener(v -> {
            Intent goToSignUpIntent = new Intent(signInActivity.this, SignUpActivity.class);
           startActivity(goToSignUpIntent);
        });
    }


}