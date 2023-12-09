package com.adventurist.adventurist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.core.Amplify;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    AuthUser authUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpSignInAndSignOutButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();
        authUser = Amplify.Auth.getCurrentUser();
        String email= "";
        if (authUser == null){
            Button signInButton = (Button) findViewById(R.id.signInMainActivity);
            signInButton.setVisibility(View.VISIBLE);
            Button signOutButton = (Button) findViewById(R.id.logOutMainActivity);
            signOutButton.setVisibility(View.INVISIBLE);
        }else {
            email = authUser.getUsername();
            Log.i(TAG, "User Email is: " + email);

            Button signInButton = (Button) findViewById(R.id.signInMainActivity);
            signInButton.setVisibility(View.INVISIBLE);

            Button signOutButton = (Button) findViewById(R.id.logOutMainActivity);
            signOutButton.setVisibility(View.VISIBLE);

            String visibleUserEmail = email;
            Amplify.Auth.fetchUserAttributes(
                    success -> {
                        Log.i(TAG, "Fetching user email: " + visibleUserEmail);
                        for (AuthUserAttribute userAttribute : success){
                            if (userAttribute.getKey().getKeyString().equals("email")){
                                String userEmail = userAttribute.getValue();
                                runOnUiThread(() ->{
                                    ((TextView)findViewById(R.id.usernameTextView)).setText(userEmail);
                                });
                            }
                        }
                    },
                    fail -> {
                        Log.i(TAG, "Fetching user email failed: " + fail.toString());
                    }
            );
        }
    }

    private void setUpSignInAndSignOutButtons(){
        Button signInButton = (Button) findViewById(R.id.signInMainActivity);
        signInButton.setOnClickListener(v -> {
            Intent goToSignInIntent = new Intent(this, signInActivity.class);
            startActivity(goToSignInIntent);
        });

        Button signOutButton = (Button) findViewById(R.id.logOutMainActivity);
        signOutButton.setOnClickListener(v -> {
            Amplify.Auth.signOut(()->{
                        Log.i(TAG, "Log Out Succeeded :D");
                        runOnUiThread(() -> {
                            ((TextView)findViewById(R.id.usernameTextView)).setText("");
                        });
                        Intent goToSignInIntent = new Intent(this, signInActivity.class);
                        startActivity(goToSignInIntent);
                    },
                    fail -> {
                        Log.i(TAG, "Log Out failed");
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Log Out failed", Toast.LENGTH_LONG);
                        });
                    });
        });
    }
}