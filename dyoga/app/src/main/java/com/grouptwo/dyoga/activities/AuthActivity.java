package com.grouptwo.dyoga.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.grouptwo.dyoga.R;

/**
 * The implementation of this authentication mechanism is based on Firebase Auth Documentation
 * available at <a href="https://firebase.google.com/docs/auth/android/password-auth">
 *     Authenticate with Firebase using Password-Based Accounts on Android</a>
 */
public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        firebaseAuth = FirebaseAuth.getInstance();

        createAccount("ue1734y@gre.ac.uk", "randompassword");
    }

    private void createAccount(final String email, final String password) {
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
                attemptSignInUser(email, password);
                return;
            }

            // If account creation fails fails, display a message to the user.
            Log.d(AuthActivity.this.getLocalClassName(), "Account creation failed", taskException);
            Toast.makeText(AuthActivity.this,
                    R.string.ops_your_account_creation_failed_please_try_again,
                    Toast.LENGTH_LONG
            ).show();
        });
    }

    private void attemptSignInUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Sign in is success, navigate to dashboard
                startActivity(new Intent(this, DashboardActivity.class));
                finishAfterTransition();
                return;
            }

            // If sign in fails, display a message to the user.
            Log.d(AuthActivity.this.getLocalClassName(), "Account sign in failed", task.getException());
            Toast.makeText(AuthActivity.this,
                    R.string.ops_authentication_failed_kindly_try_another_login_credentials,
                    Toast.LENGTH_LONG
            ).show();
        });
    }
}