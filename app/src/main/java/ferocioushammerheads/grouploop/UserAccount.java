package ferocioushammerheads.grouploop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAccount extends AppCompatActivity
        implements Login.OnFragmentInteractionListener,
        UserAccountPreferences.OnFragmentInteractionListener,
        ChangeGroup.OnFragmentInteractionListener,
        NotificationSettings.OnFragmentInteractionListener,
        CreateGroup.OnFragmentInteractionListener,
        AccountToolsHelper {
    public static DatabaseReference mDatabaseRef;

    private TextView mUserName, mGroupList, mActiveGroup, mEmail;

    private int externChangeGroup = -1;

//    moved to MainActivity
//    public static AccountTools firebaseTools;

    FirebaseUser user;


    private static final String TAG = "UserAccount";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        // [START toolbar_setup]
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // [END toolbar_setup]

        // [START initialize_auth]
        if(MainActivity.mAuth == null) {
            MainActivity.mAuth = FirebaseAuth.getInstance();
            MainActivity.mAuth.getCurrentUser();
        }
        // [END initialize_auth]

        MainActivity.firebaseTools.getInstance().setupTools(this,MainActivity.mAuth, MainActivity.mDatabase);

//        MainActivity.firebaseTools.signOut();

        Bundle initFrag = getIntent().getExtras();
        int value = -1;
        if (initFrag != null) {
            value = initFrag.getInt("key");
        }

//        use bundle value to determine initial fragment
        if (value == 1) {
            fragmentChanger(UserAccountPreferences.class, R.id.user_account_frag_frame, "UserAccountPreferences");
        } else if(value == 2) {
            fragmentChanger(ChangeGroup.class, R.id.user_account_frag_frame, "ChangeGroup");
        } else if(value == 3) { //special case for when change group was loaded from chip items
            externChangeGroup = 3;
            fragmentChanger(ChangeGroup.class, R.id.user_account_frag_frame, "ChangeGroup");
        }else {
            fragmentChanger(Login.class, R.id.user_account_frag_frame, "Login");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    //    override back button in toolbar to swap fragment instead of restart activity
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment myFragment = fragmentManager.findFragmentByTag("UserAccountPreferences");

        user = MainActivity.mAuth.getCurrentUser();
        switch (item.getItemId()) {
            case R.id.action_logout:
                if (user != null) {
                    AccountTools tmpTools = MainActivity.firebaseTools.getInstance();
                    tmpTools.signOut();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Not logged in.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_change_group:
                if (user != null) {
                    fragmentChanger(ChangeGroup.class, R.id.user_account_frag_frame, "ChangeGroup");
                } else {
                    Toast.makeText(getApplicationContext(), "Not logged in.", Toast.LENGTH_SHORT).show();
                }
                break;
            case android.R.id.home:
                // special case. returns to ChipItems
                if(externChangeGroup == 3){
                    Intent intent = new Intent(this, ChipItems.class);
                    startActivity(intent);

                    break;
                }

                if (myFragment != null && myFragment.isVisible()) {
                    Toast.makeText(getApplicationContext(), "Default behavior", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Overriding default", Toast.LENGTH_SHORT).show();
                    if (fragmentManager.findFragmentByTag("CreateGroup") != null && fragmentManager.findFragmentByTag("CreateGroup").isVisible()) {
                        fragmentChanger(UserAccountPreferences.class, R.id.user_account_frag_frame, "UserAccountPreferences");
                    } else if (fragmentManager.findFragmentByTag("ChangeGroup") != null && fragmentManager.findFragmentByTag("ChangeGroup").isVisible()) {
                        fragmentChanger(UserAccountPreferences.class, R.id.user_account_frag_frame, "UserAccountPreferences");
                    } else {
                        fragmentChanger(ChangeGroup.class, R.id.user_account_frag_frame, "ChangeGroup");
                    }
                }
                break;
        }

        return true;
    }

    @Override
    //    override back button to swap fragment instead of restart activity
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment myFragment = fragmentManager.findFragmentByTag("UserAccountPreferences");

        // special case. returns to ChipItems
        if(externChangeGroup == 3){
            Intent intent = new Intent(this, ChipItems.class);
            startActivity(intent);
        }

        if (myFragment != null && myFragment.isVisible()) {
            Toast.makeText(getApplicationContext(), "Default behavior", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Overriding default", Toast.LENGTH_SHORT).show();
            if(fragmentManager.findFragmentByTag("CreateGroup") != null && fragmentManager.findFragmentByTag("CreateGroup").isVisible()) {
                fragmentChanger(UserAccountPreferences.class, R.id.user_account_frag_frame, "UserAccountPreferences");
            } else if(fragmentManager.findFragmentByTag("ChangeGroup") != null && fragmentManager.findFragmentByTag("ChangeGroup").isVisible()){
                fragmentChanger(UserAccountPreferences.class, R.id.user_account_frag_frame, "UserAccountPreferences");
            } else {
                fragmentChanger(ChangeGroup.class, R.id.user_account_frag_frame, "ChangeGroup");
            }
        }
    }

    public void fragmentChanger(Class newFragment, int containerName, String fragName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment, UserAccountPreferencesFrag, LoginFrag, ChangeGroupFrag, NotificationSettingsFrag, CreateGroupFrag, tmpFrag;

        fragment = null;
        UserAccountPreferencesFrag = fragmentManager.findFragmentByTag("UserAccountPreferences");
        LoginFrag = fragmentManager.findFragmentByTag("Login");
        ChangeGroupFrag = fragmentManager.findFragmentByTag("ChangeGroup");
        NotificationSettingsFrag = fragmentManager.findFragmentByTag("NotificationSettings");
        CreateGroupFrag = fragmentManager.findFragmentByTag("CreateGroup");


        try {
            fragment = (Fragment) newFragment.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (UserAccountPreferencesFrag != null && UserAccountPreferencesFrag.isVisible()) {
            fragmentManager.beginTransaction().hide(UserAccountPreferencesFrag).commit();

        } else if (LoginFrag != null && LoginFrag.isVisible()) {
            fragmentManager.beginTransaction().hide(LoginFrag).commit();

        } else if (ChangeGroupFrag != null && ChangeGroupFrag.isVisible()) {
            fragmentManager.beginTransaction().hide(ChangeGroupFrag).commit();

        } else if (NotificationSettingsFrag != null && NotificationSettingsFrag.isVisible()) {
            fragmentManager.beginTransaction().hide(NotificationSettingsFrag).commit();

        } else if (CreateGroupFrag != null && CreateGroupFrag.isVisible()) {
//            TODO: go back to change groups page rather than UserProfile?
            fragmentManager.beginTransaction().hide(CreateGroupFrag).commit();

        }

        tmpFrag = fragmentManager.findFragmentByTag(fragName);


        if (tmpFrag == null) {
            fragmentManager.beginTransaction().add(containerName, fragment, fragName).commit();
        } else {
            fragmentManager.beginTransaction().show(tmpFrag).commit();
        }


        if (fragName == "UserAccountPreferences") {
            getSupportActionBar().setTitle("Account Preferences");
        } else if (fragName == "Login") {
            getSupportActionBar().setTitle("Login");
        } else if (fragName == "ChangeGroup") {
            getSupportActionBar().setTitle("Change Group");
        } else if (fragName == "NotificationSettings") {
            getSupportActionBar().setTitle("Notification Settings");
        } else if (fragName == "CreateGroup") {
            getSupportActionBar().setTitle("Create Group");
        }
    }

    public void onFragmentInteraction() {
    }

    public void onFragmentInteraction(UserProfile profile) {}

    public void onFragmentInteraction(View view) {
        user = MainActivity.mAuth.getCurrentUser();
        if (view.getId() == R.id.pref_login_button) {
//            switch to add chip item fragment
            if (user == null) {
                fragmentChanger(Login.class, R.id.user_account_frag_frame, "Login");
            } else {
                AccountTools tmpTools = AccountTools.getInstance();
                tmpTools.signOut();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        } else if (view.getId() == R.id.pref_verify_email_button) {
            if (user != null) {
                AccountTools tmpTools = AccountTools.getInstance();
                tmpTools.sendEmailVerification();
            }
        } else if (view.getId() == R.id.pref_change_add_group_button) {
            if (user != null) {
                fragmentChanger(ChangeGroup.class, R.id.user_account_frag_frame, "ChangeGroup");
            }
        } else if (view.getId() == R.id.pref_notification_settings_button) {
            if (user != null) {
                fragmentChanger(NotificationSettings.class, R.id.user_account_frag_frame, "NotificationSettings");
            }
        } else if (view.getId() == R.id.create_new_group) {
            if (user != null) {
                fragmentChanger(CreateGroup.class, R.id.user_account_frag_frame, "CreateGroup");
            }
        }

    }

    // Define the actual handler for the event.
    public void loggedInEvent() {
        MainActivity.user = MainActivity.mAuth.getCurrentUser();
//        fragmentChanger(UserAccountPreferences.class, R.id.user_account_frag_frame, "UserAccountPreferences");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    // Define the actual handler for the event.
    public void loadProfileEvent() {
        String userRefString = "/users/" + MainActivity.user.getUid();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(userRefString);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile tmpProfile = dataSnapshot.getValue(UserProfile.class);
                MainActivity.userProfile = tmpProfile;
                Log.d(TAG, "email from snapshot:" + tmpProfile.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabaseRef.addValueEventListener(postListener);
    }

    public void toastUp(String toastText){
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();

    }
}
