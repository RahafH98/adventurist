package com.adventurist.adventurist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "SignUpActivity";

    public static final String SIGN_UP_EMAIL_TAG = "Sign_Up_Email_Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setUpSignUpButton();
    }

    public void setUpSignUpButton (){
        Button signUpSubmitButton = (Button) findViewById(R.id.signUpSubmitButton);
        signUpSubmitButton.setOnClickListener(v -> {
            String email = ((EditText)findViewById(R.id.signUpEmailEdit)).getText().toString();
            String nickName = ((EditText)findViewById(R.id.sginUpNickNameEdit)).getText().toString();
            String password = ((EditText)findViewById(R.id.signUpPasswordEdit)).getText().toString();

            Amplify.Auth.signUp(email,
                    password,
                    AuthSignUpOptions.builder()
                            .userAttribute(AuthUserAttributeKey.email(),email)
                            .userAttribute(AuthUserAttributeKey.nickname(), nickName)
                            .build(),
                    success -> {
                        Log.i(TAG, "Sign Up Succeeded :D" + success.toString());
                        Intent goToVerifyIntent = new Intent(this, VerifyAccActivity.class);
                        goToVerifyIntent.putExtra(SIGN_UP_EMAIL_TAG, email);
                        startActivity(goToVerifyIntent);
                    },
                    fail -> {
                        Log.i(TAG, "Sign Up fail with this email: " + email + "with a message" + fail.toString());
                        runOnUiThread(()->{
                            Toast.makeText(this, "SignUp failed :(!!", Toast.LENGTH_LONG);
                        });
                    }
            );
        });
    }
}