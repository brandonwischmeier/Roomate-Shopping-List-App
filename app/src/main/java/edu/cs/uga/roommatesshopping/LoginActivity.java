package edu.cs.uga.roommatesshopping;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final int SIGN_IN_REQUEST_CODE = 123;
    List<AuthUI.IdpConfig> providers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            // already signed in
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this,
                    "Welcome " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName() + "!", Toast.LENGTH_LONG).show();
            finish();
        } else {
            // no user signed in
            setContentView(R.layout.activity_login);
            providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build()
            );
            createSignInIntent();
        }
    }

    /*
     * Creates the sign-in flow
     * Includes sign-in options for email and google services
     * Result of this intent will be received in onActivityResult
     */
    public void createSignInIntent() {
        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.handshake)
                        .build(), SIGN_IN_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Sign-in is successful
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                assert user != null;
                Toast.makeText(this,
                        "Welcome " + user.getDisplayName() + "!", Toast.LENGTH_LONG).show();
            } else {
                // Sign-in failed
                // If response is null the user cancelled the sign-in flow using the back button
                // Otherwise check response.getError().getErrorCode()
                if (response != null) {
                    Toast.makeText(this, "" + response.getError().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
