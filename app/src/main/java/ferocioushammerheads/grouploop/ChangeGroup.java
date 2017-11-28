package ferocioushammerheads.grouploop;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ArrayAdapter<String> listAdapter;

    private ConstraintLayout mChangeGroupOptionsLayout, mChangeGroupMainLayout;

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

        mGroupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Object o = prestListView.getItemAtPosition(position);
//                String tmp = "" + position;
//                Toast.makeText(view.getContext(),tmp,Toast.LENGTH_SHORT).show();
//                change_group_option_layout
                groupOptionsMenu(view, position, id);
            }
        });

        mChangeGroupOptionsLayout = (ConstraintLayout) view.findViewById(R.id.change_group_option_layout);
        mChangeGroupMainLayout = (ConstraintLayout) view.findViewById(R.id.change_group_main_layout);

        mChangeGroupMainLayout.setOnClickListener(mButtonClickListener);
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

//    TODO: add user to group
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
                    mGroupName.setText("");
                } else if(view.getId() == R.id.change_group_main_layout){
                    mChangeGroupOptionsLayout.setVisibility(View.GONE);
                }
            }
        }
    }

    public void groupOptionsMenu(View view, int position, long id){
        String tmp = "" + position;
//        Toast.makeText(view.getContext(),tmp,Toast.LENGTH_SHORT).show();
        mChangeGroupOptionsLayout.setVisibility(View.VISIBLE);

        mChangeGroupMainLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Toast.makeText(view.getContext(), "Got the focus", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(view.getContext(), "Lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });
        mChangeGroupOptionsLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Toast.makeText(view.getContext(), "Got the focus", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(view.getContext(), "Lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
