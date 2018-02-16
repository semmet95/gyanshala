package com.apps.devamit.gyanshala;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ActionBar actionBar;
    private NavigationView navigationView;
    private View nav_header_main;
    private ImageView headerImageView;

    private static final int RC_SIGN_IN = 123, RC_GOOGLE_DETAILS=234;
    private SharedPreferences sharedPreferences;

    private static FirebaseUser thisUser;
    private DatabaseReference mDatabase;
    List<AuthUI.IdpConfig> providers = Collections.singletonList(
            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences=getSharedPreferences("gyanshalaprefs", MODE_PRIVATE);
        mDrawerLayout= findViewById(R.id.drawer_layout);
        mDrawerToggle=new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_opened, R.string.drawer_closed) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            mDrawerToggle.syncState();
        }
        navigationView=findViewById(R.id.navigation);
        nav_header_main=getLayoutInflater().inflate(R.layout.nav_header_main, navigationView, false);
        headerImageView=nav_header_main.findViewById(R.id.userImage);
        navigationView.addHeaderView(nav_header_main);

        thisUser=FirebaseAuth.getInstance().getCurrentUser();
        //if this User==null the user hasn't signed in even once
        if(thisUser==null) {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setLogo(R.mipmap.ic_launcher_round)
                    .build(), RC_SIGN_IN);
        } else
            loggedIn();
    }

    //call when the user has logged in
    private void loggedIn() {
        Toast.makeText(this, "the current user is : " + thisUser.getDisplayName(), Toast.LENGTH_SHORT).show();

        //Glide.with(this).load(thisUser.getPhotoUrl()).into((ImageView)nav_header_main.findViewById(R.id.userImage));
        ((TextView)nav_header_main.findViewById(R.id.userName)).setText(thisUser.getDisplayName());
        ((TextView)nav_header_main.findViewById(R.id.userEmail)).setText(thisUser.getEmail());

        String imageurl=sharedPreferences.getString("imageurl", "NA");
        if(!imageurl.equals("NA")) {
            headerImageView.setBackground(null);
            Glide.with(this).load(imageurl).into((ImageView) nav_header_main.findViewById(R.id.userImage));
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("gyanshala-hackathon");
        myRef.setValue("hope it works 2");
    }

    public void loadGoogleUserDetails() {
        Log.e("google details :", "trying to get google details");
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.e("onConnectionFailed :", "connection failure at trying to download image");
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_GOOGLE_DETAILS);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                thisUser = FirebaseAuth.getInstance().getCurrentUser();
                loadGoogleUserDetails();
                loggedIn();
            } else {
                //handle this condition
                Toast.makeText(this, "sign-in failed", Toast.LENGTH_SHORT).show();
            }
        } else if(requestCode==RC_GOOGLE_DETAILS) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                // Get account information
                String PhotoUrl = acct.getPhotoUrl().toString();
                Log.e("google details :", "in google details's with photo url = "+PhotoUrl);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("imageurl", PhotoUrl);
                editor.apply();
                headerImageView.setBackground(null);
                Glide.with(this).load(PhotoUrl).into(headerImageView);
            } else
                Log.e("google details :", "failed to get photo");
        }
    }
}
