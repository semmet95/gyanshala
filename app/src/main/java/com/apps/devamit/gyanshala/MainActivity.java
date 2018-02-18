package com.apps.devamit.gyanshala;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ActionBar actionBar;
    private NavigationView navigationView;
    private View nav_header_main;
    private ImageView headerImageView;

    private static final int RC_SIGN_IN = 123, RC_GOOGLE_DETAILS=234;
    private String[] action_titles;
    private SharedPreferences sharedPreferences;

    private static FirebaseUser thisUser;
    List<AuthUI.IdpConfig> providers = Collections.singletonList(
            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        action_titles=new String[]{getResources().getString(R.string.my_feed),
                getResources().getString(R.string.my_questions), getResources().getString(R.string.my_answers),
                getResources().getString(R.string.scholarships)};

        DatabaseDownloader.obj=this;
        DatabaseDownloader.refresh();

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
        navigationView.setCheckedItem(R.id.myFeed);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new MyFeedFragment(), "current_fragment").commit();
        actionBar.setTitle(action_titles[0]);

        //adding listeners to items in navigation drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawer(Gravity.START);
                navigationView.setCheckedItem(item.getItemId());
                FragmentManager fragmentManager = getSupportFragmentManager();
                if(item.getItemId()==R.id.signOut) {
                    FirebaseAuth.getInstance().signOut();
                    item.setCheckable(false);
                } else if(item.getItemId()==R.id.signIn) {
                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setLogo(R.mipmap.ic_launcher_round)
                            .build(), RC_SIGN_IN);
                    item.setCheckable(false);
                } else if(item.getItemId()==R.id.myFeed) {
                    actionBar.setTitle(action_titles[0]);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new MyFeedFragment(), "current_fragment").commit();
                    return true;
                } else if(item.getItemId()==R.id.myQuestions) {
                    actionBar.setTitle(action_titles[1]);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new MyQuestionsFragment(), "current_fragment").commit();
                    return true;
                } else if(item.getItemId()==R.id.myAnswers) {
                    actionBar.setTitle(action_titles[2]);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new MyAnswersFragment(), "current_fragment").commit();
                    return true;
                } else if(item.getItemId()==R.id.scholarships) {
                    actionBar.setTitle(action_titles[3]);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new ScholarshipFragment(), "current_fragment").commit();
                    return true;
                }
                return false;
            }
        });

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
        //Log.e("google details :", "trying to get google details");
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        //Log.e("onConnectionFailed :", "connection failure at trying to download image");
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_GOOGLE_DETAILS);
    }

    void refreshUI() {
        //Log.e("main activity :", "here in refresh UI");
        Fragment currFragment=getSupportFragmentManager().findFragmentByTag("current_fragment");
        if(currFragment instanceof MyFeedFragment) {
            //Log.e("instance is :", "true");
            ((MyFeedFragment) currFragment).refreshUI();
        } else if(currFragment instanceof MyQuestionsFragment)
            ((MyQuestionsFragment)currFragment).refreshUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
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
        if(item.getItemId()==R.id.addQuestion) {
            if(FirebaseAuth.getInstance().getCurrentUser()==null) {
                Toast.makeText(this, "You need to login first", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, AddQuestionActivity.class);
                startActivity(intent);
            }
            return true;
        }
        if(item.getItemId()==R.id.refresh) {
            DatabaseDownloader.obj=this;
            DatabaseDownloader.refresh();
            return true;
        }
        if(item.getItemId()==R.id.language) {

            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.radiobutton_dialog);

            List<String> stringList=new ArrayList<>();  // here is list
            stringList.add("Hindi");
            stringList.add("English");
            RadioGroup rg = dialog.findViewById(R.id.radio_group);

            for(int i=0;i<stringList.size();i++){
                RadioButton rb=new RadioButton(this); // dynamically creating RadioButton and adding to RadioGroup.
                rb.setId(i);
                rb.setText(stringList.get(i));
                rg.addView(rb);
            }
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    dialog.dismiss();
                    //Log.e("checked :", "checkId = "+checkedId);
                    if(checkedId==0) {
                        //user selected hindi
                        Locale myLocale = new Locale("hi");
                        Resources res = getResources();
                        DisplayMetrics dm = res.getDisplayMetrics();
                        Configuration conf = res.getConfiguration();
                        conf.locale = myLocale;
                        res.updateConfiguration(conf, dm);
                        /*Intent refresh = new Intent(dialog.getContext(), MainActivity.class);
                        startActivity(refresh);
                        finish();*/
                    } else {
                        //user selected english
                        Locale myLocale = new Locale("en");
                        Resources res = getResources();
                        DisplayMetrics dm = res.getDisplayMetrics();
                        Configuration conf = res.getConfiguration();
                        conf.locale = myLocale;
                        res.updateConfiguration(conf, dm);
                        /*Intent refresh = new Intent(dialog.getContext(), MainActivity.class);
                        startActivity(refresh);
                        finish();*/
                    }
                    /*DatabaseDownloader.quesMetadata.clear();
                    DatabaseDownloader.quesAnswers.clear();
                    DatabaseDownloader.questionTitleList.clear();
                    DatabaseDownloader.questionDescriptionList.clear();
                    DatabaseDownloader.myQuestionTitleList.clear();
                    DatabaseDownloader.myQuestionDescriptionList.clear();*/
                    recreate();
                }
            });
            dialog.show();
            return true;
        }
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
                if(acct.getPhotoUrl()!=null) {
                    String PhotoUrl = acct.getPhotoUrl().toString();
                    //Log.e("google details :", "in google details's with photo url = " + PhotoUrl);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("imageurl", PhotoUrl);
                    editor.apply();

                    headerImageView.setBackground(null);
                    Glide.with(this).load(PhotoUrl).into(headerImageView);
                } else {
                    //Log.e("photo :", "photo not added to the account");
                    //Photo not added to the account case
                }
            } else
                Log.e("google details :", "failed to get photo");
        }
    }
}
