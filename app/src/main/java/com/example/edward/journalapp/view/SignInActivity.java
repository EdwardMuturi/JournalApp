package com.example.edward.journalapp.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.edward.journalapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG= "SignActivity";
    private static final int RC_SIGN_SIGN_IN= 9001;
    private String userEmail;

//    firebase declaration
    private FirebaseAuth firebaseAuth;

    private GoogleSignInClient googleSignInClient;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        findViewById(R.id.sign_in_button).setOnClickListener(this);

        //[START] configure sign in
        GoogleSignInOptions googleSignInOptions= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build(); //[END] configure sign in

        googleSignInClient= GoogleSignIn.getClient(this, googleSignInOptions);

        //firebase initialization
        firebaseAuth= FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //check user sign in state
        FirebaseUser activeUser= firebaseAuth.getCurrentUser();
        updateUI(activeUser);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //intent result after GoogleSignInApi.getSignInIntent() launch
        if (requestCode == RC_SIGN_SIGN_IN){
            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                //sign in success, authenticate with firebase
                GoogleSignInAccount account= task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e){
                //Sign in failed, update UI
                Log.i(TAG, getString(R.string.prompt_sign_in_failed),e);
                updateUI(null);
            }
        }
    }

//    [START] google auth
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
        showProgressDialog();

        AuthCredential authCredential= GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
                            updateUI(firebaseUser);
                        } else {
                            Log.w(TAG, "signInWithCredential: failure", task.getException());
                            Snackbar.make(findViewById(R.id.layout_sign_in), getString(R.string.prompt_sign_in_failed), Snackbar.LENGTH_LONG).show();
                            updateUI(null);
                        }

                        hideProgressDialog();
                    }
                });
    } //[END] google auth

    //sign in
    private void signIn(){
        Intent signInIntent= googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_SIGN_IN);

        Intent viewEntriesIntent= new Intent(this, JournalEntriesActivity.class);
        viewEntriesIntent.putExtra("Email" ,userEmail);
        startActivity(viewEntriesIntent);
        finish();

        Log.d(TAG, "Active user: " + userEmail);
    }

    private void updateUI(FirebaseUser activeUser) {
        hideProgressDialog();
        if (activeUser != null){
            userEmail = activeUser.getEmail();
        }
    }

    @Override
    public void onClick(View view) {
        int id= view.getId();
        if (id == R.id.sign_in_button){
            signIn();
        }

    }
}
