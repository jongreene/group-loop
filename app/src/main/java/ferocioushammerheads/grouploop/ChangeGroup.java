package ferocioushammerheads.grouploop;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class ChangeGroup extends Fragment {


    private ButtonClickListener mButtonClickListener;
    private OnFragmentInteractionListener mListener;
    private DatabaseReference mDatabase;

    private Button mAddGroupButton;
    private TextView mGroupName;

    private ListView mGroupList;
    private ArrayAdapter<String> listAdapter ;

    private View view;


    public ChangeGroup() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_change_group, container, false);



        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mAddGroupButton = view.findViewById(R.id.add_group_button);
        mGroupName = view.findViewById(R.id.new_group_name);

        if (mButtonClickListener == null) {
            mButtonClickListener = new ButtonClickListener();
        }

        mAddGroupButton.setOnClickListener(mButtonClickListener);

        mGroupList = (ListView) view.findViewById( R.id.change_group_list );
        ArrayList<String> groupList;// = new ArrayList<String>();
        groupList = (ArrayList<String>) MainActivity.userProfile.getGroupList();

        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1, groupList);

        // Set the ArrayAdapter as the ListView's adapter.
        mGroupList.setAdapter( listAdapter );
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
    }

    public void addGroup(String newGroup){
        MainActivity.userProfile.addNewGroup(newGroup);
//        listAdapter.add( "Ceres" );

    }

    private class ButtonClickListener implements View.OnClickListener {
        ButtonClickListener() {}

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                if(view.getId() == R.id.add_group_button){
                    String tmp = mGroupName.getText().toString();
                    addGroup( tmp );
                    UserAccount.firebaseTools.updateUser(MainActivity.userProfile);
                } else {
//                    mListener.onFragmentInteraction(view);
                }
            }
        }
    }
}
