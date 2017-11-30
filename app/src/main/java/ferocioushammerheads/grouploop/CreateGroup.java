package ferocioushammerheads.grouploop;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import static ferocioushammerheads.grouploop.MainActivity.currentGroup;

public class CreateGroup extends Fragment {


    private OnFragmentInteractionListener mListener;
    private ButtonClickListener mButtonClickListener;
    private Button mMakeGroup;
    private EditText mGroupUID;
    private View view;

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
            AccountTools tmpTools = AccountTools.getInstance();
            int clickedId = view.getId();
            if (clickedId == R.id.makeGroup) {
                //MainActivity.currentGroup = new UserGroup(MainActivity.user.getUid(),"testGroup3");
                //UserAccount.mDatabase.child("groups").child("testGroup3").setValue(MainActivity.currentGroup);

                MainActivity.currentGroup = new UserGroup(MainActivity.user.getUid(),mGroupUID.getText().toString());
                UserAccount.mDatabase.child("groups").child(currentGroup.getGroupId()).setValue(MainActivity.currentGroup);
                MainActivity.userProfile.addNewGroup(currentGroup.getGroupId());
                tmpTools.updateUser(MainActivity.userProfile);
                //todo swap fragment changeGroup
            }
        }
    }
}
