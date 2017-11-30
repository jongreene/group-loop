package ferocioushammerheads.grouploop;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.plus.PlusOneButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
        chipItems = (ListView) view.findViewById(R.id.chipItems);
//        list = new ArrayList<String>();
//        list.add("Hello, World");
//        listAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, list);
        listAdapter = new ListAdapter(view.getContext(), list);
        chipItems.setAdapter(listAdapter);
        FirebaseDatabase.getInstance().getReference().child("items")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d("Tag", "==============");
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String someitem = (String) snapshot.getValue();
                            Log.d("Snapshot", someitem);
//                            list.add(someitem);
                        }
                        listAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
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
        void onFragmentInteraction(String test);
        void onFragmentInteraction(View view);
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
            }
        }
    }
}
