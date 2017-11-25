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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;
    private ButtonClickListener mButtonClickListener;
    private Button mVerifyEmailButton;
    private Button mLoginButton;
    private Button mChangeGroupButton;
    private Button mNotificationSettings;

    private static final String TAG = "UserAccountPreferences";

    private View view;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private FirebaseUser user;

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

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
        void onFragmentInteraction();
        void onFragmentInteraction(View view);
        void onFragmentInteraction(int operation);
        void onFragmentInteraction(UserProfile profile);
    }

    private class ButtonClickListener implements View.OnClickListener {
        ButtonClickListener() {}

        @Override
        public void onClick(View view) {
            int clickedId = view.getId();
            if (mListener != null) {
                mListener.onFragmentInteraction(view);

                // Update the fragment after logging out
                setupFragment();
            }
        }
    }

    //    TODO: do a try catch incase the database doesnt have the correct structure
    public void loadUserProfile(){
        if(user != null && MainActivity.user != null) {
//        TODO: fix issue when trying to load after login
            String userRefString = "/users/" + MainActivity.user.getUid();
            UserAccount.mDatabaseRef = FirebaseDatabase.getInstance().getReference(userRefString);
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserProfile tmpProfile = dataSnapshot.getValue(UserProfile.class);
                    Log.d(TAG, "email from snapshot:" + tmpProfile.getEmail());
                    mListener.onFragmentInteraction(tmpProfile);
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
}
