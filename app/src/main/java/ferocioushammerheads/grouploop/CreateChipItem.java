package ferocioushammerheads.grouploop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import static ferocioushammerheads.grouploop.MainActivity.currentGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateChipItem.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateChipItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateChipItem extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private Button mAddNewChipItem;
    private EditText editText;
    private Spinner spinner;

//    private ButtonClickListener mButtonClickListener;

    private OnFragmentInteractionListener mListener;

    public CreateChipItem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateChipItem.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateChipItem newInstance(String param1, String param2) {
        CreateChipItem fragment = new CreateChipItem();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
        view = inflater.inflate(R.layout.fragment_create_chip_item, container, false);
        this.mAddNewChipItem = view.findViewById(R.id.createChipItem);
//        if (mButtonClickListener == null) {
//            mButtonClickListener = new ButtonClickListener();
//        }
        mAddNewChipItem.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.editText = view.findViewById(R.id.newChipItemName);
        this.spinner = view.findViewById(R.id.newChipItemType);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("List");
        categories.add("Schedule");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(view.getContext(),
                android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }


    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed() {
//        if (mListener != null) {
//            mListener.onFragmentInteraction();
//        }
//
//
//
//
//    }

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
        void onFragmentInteraction(String test);
    }

    public void onClick(View v){
//        Spinner spinner = view.findViewById(R.id.newChipItemType);
//        EditText editText = view.findViewById(R.id.newChipItemName);
        String itemType = spinner.getSelectedItem().toString();
        String itemName = editText.getText().toString();
        Log.d("Adding user", itemName);
        SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();


        switch (itemType){
            case "List":
                ChipItemTextList tmp = new ChipItemTextList(itemName, MainActivity.user.getUid());
                MainActivity.currentGroup.addTextListChipItem(tmp);
                MainActivity.mDatabase.child("groups").child(currentGroup.getGroupId()).child("textListChipItems").setValue(currentGroup.getTextListChipItems());
                String tempID = String.valueOf(MainActivity.currentGroup.getTextListChipItems().size() - 1);
                editor.putString("itemid", tempID);
                editor.commit();
                if(mListener != null){
                    mListener.onFragmentInteraction("List");
                }

                break;

            case "Schedule":
                ChipItemSchedule tmpSchedule = new ChipItemSchedule(itemName, MainActivity.user.getUid());
                MainActivity.currentGroup.addScheduleChipItem(tmpSchedule);
                MainActivity.mDatabase.child("groups").child(currentGroup.getGroupId()).child("scheduleChipItems").setValue(currentGroup.getScheduleChipItems());
                MainActivity.mDatabase.child("groups").child(currentGroup.getGroupId()).child("textListChipItems").setValue(currentGroup.getTextListChipItems());
                String tempCalID = String.valueOf(MainActivity.currentGroup.getScheduleChipItems().size() - 1);
                editor.putString("calID", tempCalID);
                editor.commit();
                if(mListener != null){
                    mListener.onFragmentInteraction("Schedule");
                }
                break;

        }



    }

//    private class ButtonClickListener implements View.OnClickListener {
//        ButtonClickListener() {
//        }
//
//        @Override
//        public void onClick(View view) {
//            int clickedId = view.getId();
//            if (clickedId == R.id.createChipItem) {
//                //TODO: send info from newChipItemType and newChipItemName
//                Spinner spinner = findViewById(R.id.newChipItemType);
//                EditText editText = findViewById(R.id.newChipItemName);
//                String itemType = spinner.getSelectedItem().toString();
//                String itemName = editText.getText().toString();
//                Log.d("Adding user", itemName+" "+itemType);
//
//
//            }
//        }
//    }
}
