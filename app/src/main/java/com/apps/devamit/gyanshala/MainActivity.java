package com.apps.devamit.gyanshala;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private static FirebaseUser thisUser;
    private DatabaseReference mDatabase;
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thisUser=FirebaseAuth.getInstance().getCurrentUser();
        //if this User==null the user hasn't signed in even once
        if(thisUser==null) {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setLogo(R.mipmap.ic_launcher_round)
                    .build(), RC_SIGN_IN);
            onPause();
        } else
            loggedIn();
    }

    private void loggedIn() {
        Toast.makeText(this, "the current user is : " + thisUser.getDisplayName(), Toast.LENGTH_SHORT).show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("gyanshala-hackathon");
        myRef.setValue("let's get this done");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_OK) {
            thisUser= FirebaseAuth.getInstance().getCurrentUser();
            loggedIn();
        } else
            Toast.makeText(this, "sign-in failed", Toast.LENGTH_SHORT).show();
    }
}
