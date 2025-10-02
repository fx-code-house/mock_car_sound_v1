package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // Configure Google Sign-In (basic email and profile only)
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set up sign-in button click listeners
        View googleSignInButton = findViewById(R.id.google_sign_in_button);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        View appleSignInButton = findViewById(R.id.apple_sign_in_button);
        appleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithApple();
            }
        });

        // Check for existing sign-in
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            // User is already signed in, go to main activity
            updateUI(account);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, "Sign in failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            // User is signed in, navigate to MainActivity
            Log.d(TAG, "User signed in: " + account.getDisplayName());
            Log.d(TAG, "User email: " + account.getEmail());

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("USER_NAME", account.getDisplayName());
            intent.putExtra("USER_EMAIL", account.getEmail());
            if (account.getPhotoUrl() != null) {
                intent.putExtra("USER_PHOTO", account.getPhotoUrl().toString());
            }
            startActivity(intent);
            finish();
        } else {
            // User is not signed in, show sign-in button
            Log.d(TAG, "User not signed in");
        }
    }

    private void signInWithApple() {
        // Apple Sign-In implementation placeholder
        // For demo purposes, simulate successful Apple login
        Toast.makeText(this, "Apple Sign-In would be implemented here", Toast.LENGTH_SHORT).show();

        // Simulate successful Apple login for demo
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("USER_NAME", "Apple User");
        intent.putExtra("USER_EMAIL", "user@icloud.com");
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing sign-in
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }
}