package ferocioushammerheads.grouploop;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserAccountPreferences.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserAccountPreferences#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAccountPreferences extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button ChipItemSearchButton;

    private OnFragmentInteractionListener mListener;
    private ButtonClickListener mButtonClickListener;

    private static final String TAG = "UserAccountPreferences";



    public UserAccountPreferences() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserAccountPreferences.
     */
    // TODO: Rename and change types and number of parameters
    public static UserAccountPreferences newInstance(String param1, String param2) {
        UserAccountPreferences fragment = new UserAccountPreferences();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        mAuth.signOut();

        mListener.onFragmentInteraction(0);

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        loadUserProfile();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_account_preferences, container, false);

        ChipItemSearchButton = view.findViewById(R.id.pref_login_button);

        if (mButtonClickListener == null) {
            mButtonClickListener = new ButtonClickListener();
        }
        ChipItemSearchButton.setOnClickListener(mButtonClickListener);



        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
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

            }
        }
    }

    //    TODO: do a try catch incase the database doesnt have the correct structure
    public void loadUserProfile(){
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
