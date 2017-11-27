package ferocioushammerheads.grouploop;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAccountPreferences extends Fragment {
    private OnFragmentInteractionListener mListener;

    private ButtonClickListener mButtonClickListener;

    private Button mVerifyEmailButton, mLoginButton, mChangeGroupButton, mNotificationSettings;

    private static final String TAG = "UserAccountPreferences";

    private View view;

    private FirebaseUser user;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    public TextView mUserName, mGroupList, mActiveGroup, mEmail;

    public UserAccountPreferences() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        user = mAuth.getCurrentUser();
        mListener.onFragmentInteraction(0);

        super.onCreate(savedInstanceState);

        if(user!=null) {
            loadUserProfile();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_account_preferences, container, false);

        mLoginButton = view.findViewById(R.id.pref_login_button);
        mVerifyEmailButton = view.findViewById(R.id.pref_verify_email_button);
        mChangeGroupButton = view.findViewById(R.id.pref_change_add_group_button);
        mNotificationSettings = view.findViewById(R.id.pref_notification_settings_button);

        if (mButtonClickListener == null) {
            mButtonClickListener = new ButtonClickListener();
        }
        mLoginButton.setOnClickListener(mButtonClickListener);
        mVerifyEmailButton.setOnClickListener(mButtonClickListener);
        mChangeGroupButton.setOnClickListener(mButtonClickListener);
        mNotificationSettings.setOnClickListener(mButtonClickListener);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupFragment();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(View view);
        void onFragmentInteraction(int operation);
        void onFragmentInteraction(UserProfile profile);
    }

    private class ButtonClickListener implements View.OnClickListener {
        ButtonClickListener() {}

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onFragmentInteraction(view);

                // Update the fragment after logging out
                setupFragment();
            }
        }
    }

    public void loadUserProfile(){
        if(user != null && MainActivity.user != null) {
            String userRefString = "/users/" + MainActivity.user.getUid();
            UserAccount.mDatabaseRef = FirebaseDatabase.getInstance().getReference(userRefString);
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    MainActivity.userProfile = dataSnapshot.getValue(UserProfile.class);
                    Log.d(TAG, "email from snapshot:" + MainActivity.userProfile.getEmail());
//                    mListener.onFragmentInteraction(MainActivity.userProfile);
                    updateUserProfileVariable();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                }
            };

            UserAccount.mDatabaseRef.addValueEventListener(postListener);
        }
    }

    public void setupFragment(){
        if(user!=null) {
            mLoginButton.setText(R.string.button_Logout);
            mVerifyEmailButton.setVisibility(View.VISIBLE);
            if(!user.isEmailVerified()) {
                mVerifyEmailButton.setVisibility(View.VISIBLE);
            }
        }
        else{
            mLoginButton.setText(R.string.button_login_logout_logged_out);
        }
    }

    public void updateUserProfileVariable(){
        // Views
        mUserName = view.findViewById(R.id.pref_username);
        mGroupList = view.findViewById(R.id.pref_group_list);
        mActiveGroup = view.findViewById(R.id.pref_active_group);
        mEmail = view.findViewById(R.id.pref_email);

        mUserName.setText(MainActivity.userProfile.getUsername());
        mGroupList.setText(MainActivity.userProfile.getGroupList().toString());
        mActiveGroup.setText(MainActivity.userProfile.getCurrentGroup());
        mEmail.setText(MainActivity.userProfile.getEmail());

    }
}
