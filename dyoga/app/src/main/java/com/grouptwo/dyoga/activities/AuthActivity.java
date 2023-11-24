package com.grouptwo.dyoga.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.grouptwo.dyoga.R;
import com.grouptwo.dyoga.data.repositories.ClassInstanceRepository;
import com.grouptwo.dyoga.data.repositories.CourseRepository;
import com.grouptwo.dyoga.interfaces.Callback;
import com.grouptwo.dyoga.utils.Constants;
import com.grouptwo.dyoga.utils.SharedPreferenceManager;

/**
 * The implementation of this authentication mechanism is based on Firebase Auth Documentation
 * available at <a href="https://firebase.google.com/docs/auth/android/password-auth">
 *     Authenticate with Firebase using Password-Based Accounts on Android</a>
 */
public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private EditText emailEditText, passcodeEditText;
    private Button continueButton;
    private ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        firebaseAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        passcodeEditText = findViewById(R.id.passwordEditText);
        continueButton = findViewById(R.id.continueButton);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);

        emailEditText.requestFocus();

        setClickListeners();
    }

    private void setClickListeners() {
        continueButton.setOnClickListener(v -> {
            final String emailAddress = emailEditText.getText().toString();
            final String passcode = passcodeEditText.getText().toString();

            if (validate(emailAddress, passcode)){
                // show loader
                v.setVisibility(View.GONE);
                loadingProgressBar.setVisibility(View.VISIBLE);
                createAccount(emailAddress, passcode, new Callback<Boolean>() {
                    @Override
                    public void onResult(Boolean result) {
                        if (!result) {
                            // hide loader
                            loadingProgressBar.setVisibility(View.GONE);
                            v.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

    private boolean validate(String emailAddress, String passcode) {
        if (emailAddress.isEmpty()) {
            Toast.makeText(this, R.string.please_enter_a_valid_email_address, Toast.LENGTH_LONG).show();
            return false;
        }

        if (passcode.isEmpty() || passcode.length() < 6) {
            Toast.makeText(this, R.string.please_enter_a_valid_6_digit_passcode, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void createAccount(final String email, final String password, final Callback<Boolean> callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Account creation is success, navigate to dashboard
                startActivity(new Intent(this, DashboardActivity.class));
                finishAfterTransition();
                return;
            }

            final Exception taskException = task.getException();
            if (taskException instanceof FirebaseAuthUserCollisionException) {
                // attempt to log user in
                attemptSignInUser(email, password, callback);
                return;
            }

            // If account creation fails fails, display a message to the user.
            Log.d(AuthActivity.this.getLocalClassName(), "Account creation failed", taskException);
            Toast.makeText(AuthActivity.this,
                    R.string.ops_your_account_creation_failed_please_try_again,
                    Toast.LENGTH_LONG
            ).show();

            callback.onResult(false);
        });
    }

    private void attemptSignInUser(final String email, final String password, Callback<Boolean> callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if(currentUser != null) {
                    SharedPreferenceManager.saveData(this, Constants.USERNAME, currentUser.getUid());
                }

                // Sign in is success, pull user remote information
                CourseRepository.sync(getApplicationContext(), new Callback<Boolean>() {
                    @Override
                    public void onResult(Boolean result) {
                        syncNextData();
                    }
                });
                return;
            }

            // If sign in fails, display a message to the user.
            Log.d(AuthActivity.this.getLocalClassName(), "Account sign in failed", task.getException());
            Toast.makeText(AuthActivity.this,
                    R.string.ops_authentication_failed_kindly_try_another_login_credentials,
                    Toast.LENGTH_LONG
            ).show();

            callback.onResult(false);
        });
    }

    private void syncNextData() {
        ClassInstanceRepository.sync(getApplicationContext(), new Callback<Boolean>() {
            @Override
            public void onResult(Boolean result) {
                // Sign in is success, finish the current activity to navigate to dashboard
                finishAfterTransition();
            }
        });
    }
}