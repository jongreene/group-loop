package ferocioushammerheads.grouploop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;

public class UserAccount extends AppCompatActivity
        implements Login.OnFragmentInteractionListener,
        UserAccountPreferences.OnFragmentInteractionListener
{

    public static DatabaseReference mDatabaseRef;

    public TextView mUserName, mGroupList, mActiveGroup, mEmail;



    private static final String TAG = "snapshotTest";

    Task<AuthResult> task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        fragmentChanger(Login.class,R.id.user_account_frag_frame, "Login");
        fragmentChanger(UserAccountPreferences.class,R.id.user_account_frag_frame, "UserAccountPreferences");
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

        fragmentManager.beginTransaction().replace(containerName, fragment, fragName).commit();
    }

    public void onFragmentInteraction(){

    }

    public void onFragmentInteraction(UserProfile profile){
        updateUserProfileVariable(profile);
    }

    public void onFragmentInteraction(View view){
        if (view.getId() == R.id.pref_login_button) {
//            switch to add chip item fragment
            fragmentChanger(Login.class,R.id.user_account_frag_frame,"Login");
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

        }

    }

    public void updateUserProfileVariable(UserProfile profile){
        MainActivity.userProfile = new UserProfile(profile);

        MainActivity.userProfile.setCurrentGroup(0);

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
