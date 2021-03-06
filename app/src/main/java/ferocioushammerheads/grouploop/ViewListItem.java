package ferocioushammerheads.grouploop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.support.design.widget.FloatingActionButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static ferocioushammerheads.grouploop.MainActivity.currentGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewListItem.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewListItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewListItem extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;

    private OnFragmentInteractionListener mListener;

    ArrayList<String> listItems=new ArrayList<String>(); //found at source 1
    ArrayAdapter<String> adapter; //found at source 1
    private EditText temp;
    private static final String TAG = "List items";
    private Boolean createdOnce = false;

    private FloatingActionButton mAddItemMenu;
    private Button mAddNewItem;
    private ButtonClickListener mButtonClickListener;
    private ListView listView;
    private boolean removeFlag;
//    private SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0);

    public ViewListItem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewListItem.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewListItem newInstance(String param1, String param2) {
        ViewListItem fragment = new ViewListItem();
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
        view = inflater.inflate(R.layout.fragment_view_list_item, container, false);
        mAddItemMenu = view.findViewById(R.id.addItem);
        mAddNewItem = view.findViewById(R.id.newItem);
        if (mButtonClickListener == null) {
            mButtonClickListener = new ButtonClickListener();
        }
        mAddItemMenu.setOnClickListener(mButtonClickListener);
        mAddNewItem.setOnClickListener(mButtonClickListener);
        // Inflate the layout for this fragment
        /**
         * Assigning the list view
         * Binding the Adapter
         */

        /**
         * Event listener for children of items (currently only single group functionality works 11/5/2017)
         * Other onChild methods not defined for now, When child is added to the database it gets added to the listview
         */
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        removeFlag = true;
        SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0);
//        SharedPreferences.Editor editor = pref.edit();
        final String tempID = pref.getString("itemid", null);
        Log.d("Sharedpred", tempID);

        listView = (ListView) view.findViewById(R.id.List);
        adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);

        DatabaseReference textList = FirebaseDatabase.getInstance().getReference().child("groups").child(MainActivity.currentGroup.getGroupId()).child("textListChipItems").child(tempID).child("items");
        textList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String tempKey = (String) snapshot.getValue();
                    listItems.add(tempKey);
                    adapter.notifyDataSetChanged();
                }
                ArrayList<ChipItemTextList> chipitems = MainActivity.currentGroup.getTextListChipItems();
                int itemIndex = Integer.parseInt(tempID);
                chipitems.get(itemIndex).setItems(listItems);
                MainActivity.currentGroup.setTextListChipItems(chipitems);
                MainActivity.mDatabase.child("groups").child(currentGroup.getGroupId()).child("textListChipItems").setValue(currentGroup.getTextListChipItems());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Database Error", databaseError.toString());

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0);
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                removeFlag = false;
                ArrayList<ChipItemTextList> chipitems = MainActivity.currentGroup.getTextListChipItems();
                int itemIndex = Integer.parseInt(pref.getString("itemid", null));
                listItems.remove(i);
                chipitems.get(itemIndex).setItems(listItems);
                MainActivity.currentGroup.setTextListChipItems(chipitems);
                MainActivity.mDatabase.child("groups").child(currentGroup.getGroupId()).child("textListChipItems").setValue(currentGroup.getTextListChipItems());


                return true;
            }
        });




    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
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
        void onFragmentInteraction();
    }
    /**
     * display menu when add item button is clicked
     * @param v
     */
    public void addItemMenu(View v){
        view.findViewById(R.id.listLayout).setVisibility(View.GONE);
        view.findViewById(R.id.addItem).setVisibility(View.GONE);
        view.findViewById(R.id.add_menu_item).setVisibility(View.VISIBLE);
    }

    /**
     * When an item is submitted, it retrieves the text name from the textview
     * Create a reference to the database and push the value
     * The textview was is reset to empty string
     * Return view to original list of of objects
     * @param v
     */
    public void addItems(View v) {
        SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0);
        EditText tempname   = (EditText)view.findViewById(R.id.itemEdit);
        ArrayList<ChipItemTextList> chipitems = MainActivity.currentGroup.getTextListChipItems();
        int itemIndex = Integer.parseInt(pref.getString("itemid", null));
        String temptext = tempname.getText().toString();
        chipitems.get(itemIndex).addItem(temptext);
//        adapter.clear();
        MainActivity.currentGroup.setTextListChipItems(chipitems);
        MainActivity.mDatabase.child("groups").child(currentGroup.getGroupId()).child("textListChipItems").setValue(currentGroup.getTextListChipItems());



        TextView editText = (TextView)view.findViewById(R.id.itemEdit);
        editText.setText("");



        view.findViewById(R.id.add_menu_item).setVisibility(View.GONE);
        view.findViewById(R.id.listLayout).setVisibility(View.VISIBLE);
        view.findViewById(R.id.addItem).setVisibility(View.VISIBLE);
    }
    private class ButtonClickListener implements View.OnClickListener{
        ButtonClickListener(){}

        @Override
        public void onClick(View view){
//            if (mListener != null) {
                int clickedId = view.getId();
                if(clickedId == R.id.addItem){
                    addItemMenu(view);
                }
                else if(clickedId == R.id.newItem){
                    addItems(view);
                }
//            }

        }
    }
}
