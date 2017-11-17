package ferocioushammerheads.grouploop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAccount extends MainActivity
        implements Login.OnFragmentInteractionListener,
        UserAccountPreferences.OnFragmentInteractionListener
{

    private DatabaseReference mDatabaseRef;


    private static final String TAG = "snapshotTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        check if user is logged in and load profile if they are
        if(user != null){
            loadUserProfile();
        }


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

    public void onFragmentInteraction(View view){
        if (view.getId() == R.id.account_pref_login) {
//            switch to add chip item fragment
            fragmentChanger(Login.class,R.id.user_account_frag_frame,"Login");
        }

    }

//    TODO: do a try catch incase the database doesnt have the correct structure
    public void loadUserProfile(){
        String userRefString = "/users/" + user.getUid();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(userRefString);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserProfile tmpProfile = dataSnapshot.getValue(UserProfile.class);
                Log.d(TAG, "email from snapshot:" + tmpProfile.getEmail());
                updateUserProfileVariable(tmpProfile);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };

        mDatabaseRef.addValueEventListener(postListener);
    }

    public void updateUserProfileVariable(UserProfile profile){
        userProfile = profile;
        Log.d(TAG, "email from snapshot2:" + userProfile.getEmail());

    }
}
