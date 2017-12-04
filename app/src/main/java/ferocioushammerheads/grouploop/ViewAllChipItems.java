package ferocioushammerheads.grouploop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static ferocioushammerheads.grouploop.MainActivity.currentGroup;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link ViewAllChipItems.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewAllChipItems#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewAllChipItems extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FloatingActionButton mAddChipItemButton;
    private Button ChipItemSearchButton;

    private OnFragmentInteractionListener mListener;
    private ButtonClickListener mButtonClickListener;
    private EditText chipSearch;
    private ListView chipItems;
//    private ArrayAdapter<String> listAdapter;
    private ListAdapter listAdapter;
    private ArrayList<AdapterChipItem> list = new ArrayList<AdapterChipItem>();
    private int textListNum, scheduleListNum;



    public ViewAllChipItems() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewAllChipItems.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewAllChipItems newInstance(String param1, String param2) {
        ViewAllChipItems fragment = new ViewAllChipItems();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_all_chip_items, container, false);

        //Find the +1 button
        mAddChipItemButton = view.findViewById(R.id.addChipItem);
        ChipItemSearchButton = view.findViewById(R.id.ChipItemSearch);

        if (mButtonClickListener == null) {
            mButtonClickListener = new ButtonClickListener();
        }
        mAddChipItemButton.setOnClickListener(mButtonClickListener);
        ChipItemSearchButton.setOnClickListener(mButtonClickListener);

        chipSearch = view.findViewById(R.id.editText);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh the state of the +1 button each time the activity receives focus.
//        mButton.addOnAttachStateChangeListener();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        chipItems = (ListView) view.findViewById(R.id.textListChipItems);
        listAdapter = new ListAdapter(view.getContext(), list);
        chipItems.setAdapter(listAdapter);

//        DatabaseReference textList = FirebaseDatabase.getInstance().getReference().child("groups").child(MainActivity.currentGroup.getGroupId()).child("textListChipItems");
//        DatabaseReference schedules = FirebaseDatabase.getInstance().getReference().child("groups").child(MainActivity.currentGroup.getGroupId()).child("scheduleChipItems");
        DatabaseReference groupRef = FirebaseDatabase.getInstance().getReference().child("groups").child(MainActivity.currentGroup.getGroupId());

        groupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listAdapter.clear();
                UserGroup tempGroup = dataSnapshot.getValue(UserGroup.class);
                MainActivity.currentGroup.setTextListChipItems(tempGroup.getTextListChipItems());
                MainActivity.currentGroup.setScheduleChipItems(tempGroup.getScheduleChipItems());
//                MainActivity.mDatabase.child("groups").child(currentGroup.getGroupId()).child("textListChipItems").setValue(currentGroup.getTextListChipItems());
//                MainActivity.mDatabase.child("groups").child(currentGroup.getGroupId()).child("scheduleChipItems").setValue(currentGroup.getTextListChipItems());

                int i = 0;
                for(ChipItemTextList textList : tempGroup.getTextListChipItems()){
                    String tempKey = String.valueOf(i);
                    AdapterChipItem tempItem = new AdapterChipItem(textList.getName(), tempKey, "List");
                    list.add(tempItem);
                    listAdapter.notifyDataSetChanged();
                    i = i+1;

                }


                i = 0;
                for(ChipItemSchedule schedule : tempGroup.getScheduleChipItems()){
                    String tempKey = String.valueOf(i);
                    AdapterChipItem tempItem = new AdapterChipItem(schedule.getName(), tempKey, "Schedule");
                    list.add(tempItem);
                    listAdapter.notifyDataSetChanged();
                    i = i+1;

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        textList.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                ArrayList<ChipItemTextList> templist = (ArrayList<ChipItemTextList>) dataSnapshot.getValue();
//                if(templist == null){
//                    templist = new ArrayList<>();
//                }
//                listAdapter.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    String tempKey = snapshot.getKey();
//                    ChipItemTextList temp = snapshot.getValue(ChipItemTextList.class);
//                    AdapterChipItem tempItem = new AdapterChipItem(temp.getName(), tempKey, "List");
//                    list.add(tempItem);
//                    listAdapter.notifyDataSetChanged();
//
//
//                }
//                ArrayList<ChipItemSchedule> sList = MainActivity.currentGroup.getScheduleChipItems();
//                int i = 0;
//                for(ChipItemSchedule schedule : sList){
//                    String tempKey = String.valueOf(i);
//                    AdapterChipItem tempItem = new AdapterChipItem(schedule.getName(), tempKey, "Schedule");
//                    list.add(tempItem);
//                    listAdapter.notifyDataSetChanged();
//
//                }
//
////                Log.d("List Data", dataSnapshot.getValue().toString());
//                MainActivity.currentGroup.setTextListChipItems(templist);
//                MainActivity.mDatabase.child("groups").child(currentGroup.getGroupId()).child("textListChipItems").setValue(currentGroup.getTextListChipItems());
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d("Database Error", databaseError.toString());
//            }
//        });

//        schedules.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                ArrayList<ChipItemSchedule> templist = (ArrayList<ChipItemSchedule>) dataSnapshot.getValue();
//                if(templist == null){
//                    templist = new ArrayList<>();
//                }
//                ArrayList<ChipItemTextList> textList = MainActivity.currentGroup.getTextListChipItems();
//                listAdapter.clear();
//                int i = 0;
//                for(ChipItemTextList item : textList){
//                    String tempKey = String.valueOf(i);
//                    AdapterChipItem tempItem = new AdapterChipItem(item.getName(), tempKey, "List");
//                    list.add(tempItem);
//                    listAdapter.notifyDataSetChanged();
//
//                }
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    String tempKey = snapshot.getKey();
//                    ChipItemSchedule temp = snapshot.getValue(ChipItemSchedule.class);
//                    AdapterChipItem tempItem = new AdapterChipItem(temp.getName(), tempKey, "Schedule");
//                    list.add(tempItem);
//                    listAdapter.notifyDataSetChanged();
//
//                }
////                Log.d("List Data", dataSnapshot.getValue().toString());
//                MainActivity.currentGroup.setScheduleChipItems(templist);
//                MainActivity.mDatabase.child("groups").child(currentGroup.getGroupId()).child("scheduleChipItems").setValue(currentGroup.getTextListChipItems());
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d("Database Error", databaseError.toString());
//            }
//        });




        //TODO: convert childeventlistener back to valueEventListener
//        textList.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
//                    String tempKey = dataSnapshot.getKey();
//                    ChipItemTextList temp = dataSnapshot.getValue(ChipItemTextList.class);
//                    AdapterChipItem tempItem = new AdapterChipItem(temp.getName(), tempKey, "List");
//                    list.add(tempItem);
//                    listAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {}
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        });

        chipItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Item clicked", Integer.toString(position));
                AdapterChipItem temp = list.get(position);
                Log.d("Item name", temp.getName());
                Log.d("View id", Integer.toString(view.getId()));
                Log.d("View ID", temp.getID());

                SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                if(temp.getType().equals("List")){
                    editor.putString("itemid", temp.getID());
                    editor.commit();
                    Log.d("sharedpref",pref.getString("itemid", null));
                    mListener.onFragmentInteraction("List");
                }
                else{
                    editor.putString("calID", temp.getID());
                    editor.commit();
                    mListener.onFragmentInteraction("Schedule");
                }


            }
        });

    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction("asdf");
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String frag);
        void onFragmentInteraction(View view);
        void onFragmentInteraction();
    }

    private class ButtonClickListener implements View.OnClickListener {
        ButtonClickListener() {}

        @Override
        public void onClick(View view) {
            int clickedId = view.getId();
            if (mListener != null) {
                if (clickedId == R.id.addChipItem) {
                    mListener.onFragmentInteraction(view);
                } else if (clickedId == R.id.ChipItemSearch) {
                    String value = chipSearch.getText().toString();

                    mListener.onFragmentInteraction(value);
                }
                else if (clickedId == R.id.textListChipItems){
                    mListener.onFragmentInteraction(view);
                }
            }
        }
    }
}
