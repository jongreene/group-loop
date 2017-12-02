package ferocioushammerheads.grouploop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class UserAccount extends AppCompatActivity
        implements Login.OnFragmentInteractionListener,
        UserAccountPreferences.OnFragmentInteractionListener,
        ChangeGroup.OnFragmentInteractionListener,
        NotificationSettings.OnFragmentInteractionListener,
        CreateGroup.OnFragmentInteractionListener,
        AccountToolsHelper {
    public static DatabaseReference mDatabaseRef;

    private int externChangeGroup = -1;

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
            fragmentChanger(UserAccountPreferences.class, R.id.user_account_frag_frame, "User Account Preferences");
        } else if(value == 2) {
            fragmentChanger(ChangeGroup.class, R.id.user_account_frag_frame, "Change Group");
        } else if(value == 3) { //special case for when change group was loaded from chip items
            externChangeGroup = 3;
            fragmentChanger(ChangeGroup.class, R.id.user_account_frag_frame, "Change Group");
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
        Fragment myFragment = fragmentManager.findFragmentByTag("User Account Preferences");

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
                    fragmentChanger(ChangeGroup.class, R.id.user_account_frag_frame, "Change Group");
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
                } if (myFragment != null && myFragment.isVisible()) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    checkCurrentFragment();
                }
                break;
        }
        return true;
    }

    @Override
    //    override back button to swap fragment instead of restart activity
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment myFragment = fragmentManager.findFragmentByTag("User Account Preferences");

        // special case. returns to ChipItems
        if(externChangeGroup == 3){
            Intent intent = new Intent(this, ChipItems.class);
            startActivity(intent);
        }

        if (myFragment != null && myFragment.isVisible()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            checkCurrentFragment();
        }
    }

    public void fragmentChanger(Class newFragment, int containerName, String fragName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;

        ArrayList<Fragment> newFrag = new ArrayList<Fragment>();

        fragment = null;

        Fragment[] thisFrag = new Fragment[]{
                fragmentManager.findFragmentByTag("User Account Preferences"),
                fragmentManager.findFragmentByTag("Login"),
                fragmentManager.findFragmentByTag("Change Group"),
                fragmentManager.findFragmentByTag("Notification Settings"),
                fragmentManager.findFragmentByTag("Create Group"),
                fragmentManager.findFragmentByTag(fragName)};

        newFrag.addAll(Arrays.asList(thisFrag));

        try {
            fragment = (Fragment) newFragment.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(Fragment tmp : newFrag) {
            hideFrag(fragmentManager, tmp);
        }

        buildFrag(fragmentManager, containerName, fragment, fragName);
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
                fragmentChanger(ChangeGroup.class, R.id.user_account_frag_frame, "Change Group");
            }
        } else if (view.getId() == R.id.pref_notification_settings_button) {
            if (user != null) {
                fragmentChanger(NotificationSettings.class, R.id.user_account_frag_frame, "Notification Settings");
            }
        } else if (view.getId() == R.id.create_new_group) {
            if (user != null) {
                fragmentChanger(CreateGroup.class, R.id.user_account_frag_frame, "Create Group");
            }
        }

    }

    // Define the actual handler for the event.
    public void loggedInEvent() {
        MainActivity.user = MainActivity.mAuth.getCurrentUser();
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

    public void checkCurrentFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentByTag("Create Group") != null && fragmentManager.findFragmentByTag("Create Group").isVisible()) {
            fragmentChanger(UserAccountPreferences.class, R.id.user_account_frag_frame, "User Account Preferences");
        } else if (fragmentManager.findFragmentByTag("Change Group") != null && fragmentManager.findFragmentByTag("Change Group").isVisible()) {
            fragmentChanger(UserAccountPreferences.class, R.id.user_account_frag_frame, "User Account Preferences");
        } else if(fragmentManager.findFragmentByTag("Notification Settings") != null && fragmentManager.findFragmentByTag("Notification Settings").isVisible()){
            fragmentChanger(UserAccountPreferences.class, R.id.user_account_frag_frame, "User Account Preferences");
        } else {
            fragmentChanger(ChangeGroup.class, R.id.user_account_frag_frame, "Change Group");
        }
    }

    public void hideFrag(FragmentManager fragmentManager, Fragment fragment){
        if (fragment != null && fragment.isVisible()) {
            fragmentManager.beginTransaction().hide(fragment).commit();
        }
    }

    public void buildFrag(FragmentManager fragmentManager, int containerName, Fragment fragment, String fragName){
        Fragment tmpFrag = fragmentManager.findFragmentByTag(fragName);

        if (tmpFrag == null) {
            fragmentManager.beginTransaction().add(containerName, fragment, fragName).commit();
        } else {
            fragmentManager.beginTransaction().show(tmpFrag).commit();
        }
        getSupportActionBar().setTitle(fragName);
    }
}
