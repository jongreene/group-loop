package ferocioushammerheads.grouploop;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static ferocioushammerheads.grouploop.MainActivity.currentGroup;

public class CreateGroup extends Fragment {


    private static final String TAG = "CreateGroup";
    private OnFragmentInteractionListener mListener;
    private ButtonClickListener mButtonClickListener;
    private Button mMakeGroup;
    private EditText mGroupUID;
    private View view;
    private DatabaseReference mDatabaseRef;

    public CreateGroup() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_group, container, false);


        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mMakeGroup = view.findViewById(R.id.makeGroup);
        mGroupUID = view.findViewById(R.id.groupUID);
        if (mButtonClickListener == null) {
            mButtonClickListener = new ButtonClickListener();
        }
        mMakeGroup.setOnClickListener(mButtonClickListener);

        //MainActivity.currentGroup = new UserGroup(MainActivity.user.getUid(),"testGroup1");
        //UserAccount.mDatabase.child("groups").child("testGroup1").setValue(MainActivity.currentGroup);

        //MainActivity.currentGroup = new UserGroup(MainActivity.user.getUid(),"testGroup2");
        //UserAccount.mDatabase.child("groups").child("testGroup2").setValue(MainActivity.currentGroup);


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
    }

    private class ButtonClickListener implements View.OnClickListener {
        ButtonClickListener() {}

        @Override
        public void onClick(View view) {
            int clickedId = view.getId();
            if (clickedId == R.id.makeGroup) {
                //MainActivity.currentGroup = new UserGroup(MainActivity.user.getUid(),"testGroup3");
                //UserAccount.mDatabase.child("groups").child("testGroup3").setValue(MainActivity.currentGroup);
                String userRefString = "/groups/";
                mDatabaseRef = FirebaseDatabase.getInstance().getReference(userRefString);
                MainActivity.currentGroup = new UserGroup(MainActivity.user.getUid(),mGroupUID.getText().toString());
                mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (!snapshot.hasChild(mGroupUID.getText().toString())) {
                            AccountTools tmpTools = AccountTools.getInstance();
                            UserAccount.mDatabase.child("groups").child(currentGroup.getGroupId()).setValue(MainActivity.currentGroup);
                            MainActivity.userProfile.addNewGroup(currentGroup.getGroupId());
                            tmpTools.updateUser(MainActivity.userProfile);


                            Log.w(TAG, "Group doesn't exist");
                        }
                        else {
                            Log.w(TAG, "Group exists");
                        }
                        mGroupUID.setText("");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                getActivity().onBackPressed();
                //todo swap fragment changeGroup
            }
        }
    }
}
