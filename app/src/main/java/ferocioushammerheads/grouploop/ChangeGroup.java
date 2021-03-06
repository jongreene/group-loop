package ferocioushammerheads.grouploop;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChangeGroup extends Fragment {

    private static final String TAG = "ChangeGroup";

    private ButtonClickListener mButtonClickListener;
    private OnFragmentInteractionListener mListener;
    private DatabaseReference mDatabaseRef;

    private Button mAddGroupButton, mChangeGroupSetActive, mChangeGroupRemoveGroup;

    private FloatingActionButton mCreateGroup;

    private ImageButton mChangeGroupCancelSelect;
    private TextView mGroupName;

    private ListView mGroupList;
    private ArrayAdapter<String> listAdapter;
    private ArrayList<String> groupList;

    private ConstraintLayout mChangeGroupOptionsLayout, mChangeGroupMainLayout;

    private View view;

    private int itemSelected;

    public ChangeGroup() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

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

//        menu buttons
        mChangeGroupCancelSelect = view.findViewById(R.id.change_group_cancel_select);
        mChangeGroupSetActive = view.findViewById(R.id.change_group_set_active);
        mChangeGroupRemoveGroup = view.findViewById(R.id.change_group_remove_group);
        mCreateGroup = view.findViewById(R.id.create_new_group);

        if (mButtonClickListener == null) {
            mButtonClickListener = new ButtonClickListener();
        }

        mAddGroupButton.setOnClickListener(mButtonClickListener);

        mChangeGroupCancelSelect.setOnClickListener(mButtonClickListener);
        mChangeGroupSetActive.setOnClickListener(mButtonClickListener);
        mChangeGroupRemoveGroup.setOnClickListener(mButtonClickListener);
        mCreateGroup.setOnClickListener(mButtonClickListener);

        mGroupList = (ListView) view.findViewById( R.id.change_group_list );
        groupList = (ArrayList<String>) MainActivity.userProfile.getGroupList();

        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1, groupList);

        // Set the ArrayAdapter as the ListView's adapter.
        mGroupList.setAdapter( listAdapter );


        mGroupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mChangeGroupOptionsLayout.getVisibility() != View.VISIBLE){
                    showGroupOptionsMenu(true, view, position, id);
                } else{
                    mChangeGroupOptionsLayout.setVisibility(View.GONE);
                    enableDisableView(mChangeGroupMainLayout, true);
                }

            }
        });

        mChangeGroupOptionsLayout = (ConstraintLayout) view.findViewById(R.id.change_group_option_layout);
        mChangeGroupMainLayout = (ConstraintLayout) view.findViewById(R.id.change_group_main_layout);
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

    private class ButtonClickListener implements View.OnClickListener {
        ButtonClickListener() {}

        @Override
        public void onClick(View view) {
            groupList = MainActivity.userProfile.getGroupList();
            if (mListener != null) {
                if(view.getId() == R.id.add_group_button){

                    // this checks to see if the group exists in the database before adding
                    String userRefString = "/groups/";
                    mDatabaseRef = FirebaseDatabase.getInstance().getReference(userRefString);
                    mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.hasChild(mGroupName.getText().toString())) {
                                MainActivity.userProfile.addNewGroup(mGroupName.getText().toString());
                                MainActivity.firebaseTools.getInstance().updateUser(MainActivity.userProfile);

                                // workaround for listAdapter getting out of sync with groupList
                                if(groupList.size() != listAdapter.getCount())
                                    listAdapter.add(mGroupName.getText().toString());

                                MainActivity.firebaseTools.setSomethingHappened(true);
                                MainActivity.firebaseTools.toastUp("Group added!");
                                MainActivity.firebaseTools.setSomethingHappened(false);

                            }
                            else {
                                MainActivity.firebaseTools.setSomethingHappened(true);
                                MainActivity.firebaseTools.toastUp("Group not exist..");
                                MainActivity.firebaseTools.setSomethingHappened(false);
                            }
                            mGroupName.setText("");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

                } else if(view.getId() == R.id.change_group_cancel_select){
                    showGroupOptionsMenu(false, null, 0, 0);

                } else if(view.getId() == R.id.change_group_set_active){
                    String group = MainActivity.userProfile.getGroupList().get(itemSelected);

                    MainActivity.firebaseTools.setSomethingHappened(true);
                    MainActivity.firebaseTools.toastUp("Set '" + group + "' as the active group.");
                    MainActivity.firebaseTools.setSomethingHappened(false);

                    MainActivity.userProfile.setCurrentGroup(itemSelected);
                    MainActivity.firebaseTools.updateUser(MainActivity.userProfile);
                    showGroupOptionsMenu(false, null, 0, 0);

                    MainActivity.firebaseTools.loadGroup(MainActivity.userProfile.getCurrentGroup());

                } else if(view.getId() == R.id.change_group_remove_group){
                    String group = MainActivity.userProfile.getGroupList().get(itemSelected);

                    MainActivity.firebaseTools.setSomethingHappened(true);
                    MainActivity.firebaseTools.toastUp("Removed '" + group + "' from group list.");
                    MainActivity.firebaseTools.setSomethingHappened(false);

                    MainActivity.userProfile.removeGroup(itemSelected);
                    MainActivity.firebaseTools.updateUser(MainActivity.userProfile);

//                    workaround for listAdapter getting out of sync with groupList
                    if(groupList.size() != listAdapter.getCount())
                        listAdapter.remove(listAdapter.getItem(itemSelected));

                    showGroupOptionsMenu(false, null, 0, 0);

                } else if(view.getId() == R.id.create_new_group){
                    mListener.onFragmentInteraction(view);
                }
            }
        }
    }

    public void showGroupOptionsMenu(boolean enabled, View view, int position, long id){
        if(enabled) {
            itemSelected = position;
            mChangeGroupOptionsLayout.setVisibility(View.VISIBLE);
            enableDisableView(mChangeGroupMainLayout, false);

        } else {
            mChangeGroupOptionsLayout.setVisibility(View.GONE);
            enableDisableView(mChangeGroupMainLayout, true);

        }
        listAdapter.notifyDataSetChanged();
    }

    public void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if ( view instanceof ViewGroup ) {
            ViewGroup group = (ViewGroup)view;

            for ( int idx = 0 ; idx < group.getChildCount() ; idx++ ) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }
}
