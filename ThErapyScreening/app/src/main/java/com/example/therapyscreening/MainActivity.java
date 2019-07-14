package com.example.therapyscreening;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class MainActivity extends AppCompatActivity implements ViewSwitcher.OnClickListener {
    // Objects to match to their respective UI objects
    boolean userSignedIn = false;
    SignInButton signInButton;
    Button signOutButton;
    Button continueButton;

    // SignInClient used to let the user sign in using his/her google account
    GoogleSignInClient mGoogleSignInClient; //Make it static? How can other activities use the info from this object??
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private static GoogleSignInAccount account; //Used to store the info of the signed in Google account

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               // .requestIdToken(getString(R.string.default_web_client_id)) MIGHT ME NEEDED LATER
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso); // Along with this. Some documentation is OUTDATED!!!

        // Setting up textView and Button using their ids from the activity_main.xml file

        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);

        signOutButton = findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(this);

        continueButton = findViewById(R.id.continue_button);
        continueButton.setVisibility(View.GONE);
        continueButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.continue_button:
                continueToQuestionnarieActvity();
                break;
            case R.id.sign_in_button:
                signIn();
                continueButton.setVisibility(View.VISIBLE);
                signInButton.setVisibility(View.INVISIBLE);
                break;
            case R.id.signOutButton:
                if (userSignedIn) {
                    signOut();
                    signInButton.setVisibility(View.VISIBLE);
                    continueButton.setVisibility(View.INVISIBLE);
                }
                else {
                    disPlayMessage("No user signed in");
                }
                break;
        }
    }

    public static GoogleSignInAccount getGoogleAccount(){ // Returns the GoogleAccount that was signed in. Used in the QuestionnarieActivity
        return account;
    }

    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        disPlayMessage("User signed out");
                        userSignedIn = false;
                    }
                });
    }

    private void continueToQuestionnarieActvity(){
        Intent intent = new Intent (this , QuestionnarieActivity.class);
        startActivity (intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            this.account = account; // save the Google account that just signed in.
            // Signed in successfully, Display the name of the user
            userSignedIn = true;
            disPlayMessage("Hello " + account.getDisplayName());
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            disPlayMessage("Failure signing in");
        }
    }

    private void disPlayMessage(CharSequence text){
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }


}
