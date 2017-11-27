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
        AccountToolsHelper

{
    // Define the actual handler for the event.
    public void loggedInEvent () {
        System.out.println("logged in");
        MainActivity.user = mAuth.getCurrentUser();
        fragmentChanger(UserAccountPreferences.class, R.id.user_account_frag_frame, "UserAccountPreferences");

    }

    // Define the actual handler for the event.
    public void loadProfileEvent() {
        String userRefString = "/users/" + MainActivity.user.getUid();
        UserAccount.mDatabaseRef = FirebaseDatabase.getInstance().getReference(userRefString);
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
        UserAccount.mDatabaseRef.addValueEventListener(postListener);
    }


    public static DatabaseReference mDatabaseRef;

    public TextView mUserName, mGroupList, mActiveGroup, mEmail;

    public static AccountTools firebaseTools;

    FirebaseUser user;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private DatabaseReference mDatabase;

    private View view;

    private static final String TAG = "UserAccount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        view = this.findViewById(android.R.id.content).getRootView();

        firebaseTools = new AccountTools(this,mAuth, mDatabase, view);
//        firebaseTools.signOut();

        Bundle b = getIntent().getExtras();
        int value = -1; // or other values
        if(b != null) {
            value = b.getInt("key");
        }

//        use bundle value to determine initial fragment
        if(value == 1) {
            fragmentChanger(UserAccountPreferences.class, R.id.user_account_frag_frame, "UserAccountPreferences");
        } else {
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
        if (myFragment != null && myFragment.isVisible()) {
            Toast.makeText(getApplicationContext(), "Default behavior", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
//            return super.onOptionsItemSelected(item);
        }
        else {
            user = mAuth.getCurrentUser();
            switch (item.getItemId()) {
                case R.id.action_logout:
                    if(user != null){
                        firebaseTools.signOut();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Not logged in.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case android.R.id.home:
                    Toast.makeText(getApplicationContext(), "Overriding default", Toast.LENGTH_SHORT).show();
                    fragmentChanger(UserAccountPreferences.class, R.id.user_account_frag_frame, "UserAccountPreferences");
                    break;
            }
        }
        return true;
    }

    @Override
    //    override back button to swap fragment instead of restart activity
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment myFragment = fragmentManager.findFragmentByTag("UserAccountPreferences");
        if (myFragment != null && myFragment.isVisible()) {
            Toast.makeText(getApplicationContext(), "Default behavior", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), "Overriding default", Toast.LENGTH_SHORT).show();
            fragmentChanger(UserAccountPreferences.class, R.id.user_account_frag_frame, "UserAccountPreferences");
        }
    }

    public void fragmentChanger(Class newFragment, int containerName, String fragName){
        Fragment fragment = null;
        Class fragmentClass = newFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }



        Fragment UserAccountPreferencesFrag = fragmentManager.findFragmentByTag("UserAccountPreferences");
        Fragment LoginFrag = fragmentManager.findFragmentByTag("Login");
        Fragment ChangeGroupFrag = fragmentManager.findFragmentByTag("ChangeGroup");
        Fragment NotificationSettingsFrag = fragmentManager.findFragmentByTag("NotificationSettings");
        Fragment tmpFrag = fragmentManager.findFragmentByTag(fragName);

        if (UserAccountPreferencesFrag != null && UserAccountPreferencesFrag.isVisible()) {
            fragmentManager.beginTransaction().hide(UserAccountPreferencesFrag).commit();

        } else if (LoginFrag != null && LoginFrag.isVisible()){
            fragmentManager.beginTransaction().hide(LoginFrag).commit();

        } else if (ChangeGroupFrag != null && ChangeGroupFrag.isVisible()){
            fragmentManager.beginTransaction().hide(ChangeGroupFrag).commit();

        } else if (NotificationSettingsFrag != null && NotificationSettingsFrag.isVisible()){
            fragmentManager.beginTransaction().hide(NotificationSettingsFrag).commit();

        }

        if(tmpFrag == null){
            fragmentManager.beginTransaction().add(containerName, fragment, fragName).commit();
        } else {
            fragmentManager.beginTransaction().show(tmpFrag).commit();
        }


        if (fragName == "UserAccountPreferences") {
            getSupportActionBar().setTitle("Account Preferences");
        } else if(fragName == "Login"){
            getSupportActionBar().setTitle("Login");
        } else if(fragName == "ChangeGroup"){
            getSupportActionBar().setTitle("Change Group");
        } else if(fragName == "NotificationSettings"){
            getSupportActionBar().setTitle("Notification Settings");
        }
    }

    public void onFragmentInteraction(){}

    public void onFragmentInteraction(UserProfile profile){
        updateUserProfileVariable(profile);
    }

    public void onFragmentInteraction(View view){
        user = mAuth.getCurrentUser();
        if (view.getId() == R.id.pref_login_button) {
//            switch to add chip item fragment
            if(user==null) {
                fragmentChanger(Login.class, R.id.user_account_frag_frame, "Login");
            } else{
                firebaseTools.signOut();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        } else if(view.getId() == R.id.pref_verify_email_button){
            if(user != null) {
                firebaseTools.sendEmailVerification();
            }
        } else if(view.getId() == R.id.pref_change_add_group_button){
            if(user != null) {
                fragmentChanger(ChangeGroup.class, R.id.user_account_frag_frame, "ChangeGroup");
            }
        }
        else if(view.getId() == R.id.pref_notification_settings_button){
            if(user != null) {
                fragmentChanger(NotificationSettings.class, R.id.user_account_frag_frame, "NotificationSettings");
            }
        }

    }

//    call back functionality for fragments
    public void onFragmentInteraction(int operation){
        if(operation==0){
            //        check if user is logged in and load profile if they are
            if(MainActivity.user != null){
//                loadUserProfile();
            }
        } else if(operation==1){
//            firebaseTools.signOut();
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
        }

    }

//    TODO: move most of this into UserAccountPreferences since thats where these exist
    public void updateUserProfileVariable(UserProfile profile){
        // Views
        mUserName = this.findViewById(R.id.pref_username);
        mGroupList = this.findViewById(R.id.pref_group_list);
        mActiveGroup = this.findViewById(R.id.pref_active_group);
        mEmail = this.findViewById(R.id.pref_email);

        mUserName.setText(MainActivity.userProfile.getUsername());
        mGroupList.setText(MainActivity.userProfile.getGroupList().toString());
        mActiveGroup.setText(MainActivity.userProfile.getCurrentGroup());
        mEmail.setText(MainActivity.userProfile.getEmail());

    }

}
