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
import android.widget.Toast;

public class CreateGroup extends Fragment {


    private OnFragmentInteractionListener mListener;
    private ButtonClickListener mButtonClickListener;

    public CreateGroup() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_group, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (mButtonClickListener == null) {
            mButtonClickListener = new ButtonClickListener();
        }

        MainActivity.currentGroup = new UserGroup(MainActivity.user.getUid(),"testGroup1");
        UserAccount.mDatabase.child("groups").child("testGroup1").setValue(MainActivity.currentGroup);

        MainActivity.currentGroup = new UserGroup(MainActivity.user.getUid(),"testGroup2");
        UserAccount.mDatabase.child("groups").child("testGroup2").setValue(MainActivity.currentGroup);

//        MainActivity.currentGroup = new UserGroup(MainActivity.user.getUid(),"testGroup2");
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

        }
    }
}
