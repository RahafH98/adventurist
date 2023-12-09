package com.adventurist.adventurist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;

public class VerifyAccActivity extends AppCompatActivity {

    public static final String TAG = "VerifyAccActivity";

    public static final String VERIFY_ACC_EMAIL_TAG = "Verify_Acc_Email_Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_acc);

        Intent callingIntent = getIntent();
        String email = callingIntent.getStringExtra(SignUpActivity.SIGN_UP_EMAIL_TAG);

        EditText emailEditText = (EditText) findViewById(R.id.vertifyEmailEdit);
        emailEditText.setText(email);

        Button verifyAccButton = findViewById(R.id.verifySubmitButton);
        verifyAccButton.setOnClickListener(v -> {
            String userEmail = emailEditText.getText().toString();
            String verificationCode = ((EditText)findViewById(R.id.verificationCodeEdit)).getText().toString();

            Amplify.Auth.confirmSignUp(userEmail,
                    verificationCode,
                    success -> {
                        Log.i(TAG, "Verification Succeeded :D" + success.toString());
                        Intent goToSignIntent = new Intent(this, signInActivity.class);
                        goToSignIntent.putExtra(VERIFY_ACC_EMAIL_TAG, userEmail);
                        startActivity(goToSignIntent);
                    },
                    fail -> {
                        Log.i(TAG,"Verification failed" + fail.toString());

                        runOnUiThread(() -> {
                            Toast.makeText(this, "Verify Acc Failed :(!!", Toast.LENGTH_LONG);
                        });
                    }
            );
        });

    }
}