package ferocioushammerheads.grouploop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static ferocioushammerheads.grouploop.MainActivity.currentGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewCalendarItem.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewCalendarItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewCalendarItem extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View view;

    private OnFragmentInteractionListener mListener;

    //calendar view
    private DatePicker calendar;
    private FloatingActionButton dateSelection;
    //add description view
    //private EditText description;
    //private Button addDescription;
    //checkbox view
    private FloatingActionButton checkBoxSelection;
    private ScrollView checkboxStuff;
    private HashMap<String, String> myMap = new HashMap<>();

    private ButtonClickListener myBCL;
    private FirebaseUser user;
    private HashMap<String, HashMap<String, String>> schedule = new HashMap<>();
    private String dayID;

    private CheckBox cb12am;
    private CheckBox cb01am;
    private CheckBox cb02am;
    private CheckBox cb03am;
    private CheckBox cb04am;
    private CheckBox cb05am;
    private CheckBox cb06am;
    private CheckBox cb07am;
    private CheckBox cb08am;
    private CheckBox cb09am;
    private CheckBox cb10am;
    private CheckBox cb11am;
    private CheckBox cb12pm;
    private CheckBox cb01pm;
    private CheckBox cb02pm;
    private CheckBox cb03pm;
    private CheckBox cb04pm;
    private CheckBox cb05pm;
    private CheckBox cb06pm;
    private CheckBox cb07pm;
    private CheckBox cb08pm;
    private CheckBox cb09pm;
    private CheckBox cb10pm;
    private CheckBox cb11pm;

    private TextView tv12am;
    private TextView tv01am;
    private TextView tv02am;
    private TextView tv03am;
    private TextView tv04am;
    private TextView tv05am;
    private TextView tv06am;
    private TextView tv07am;
    private TextView tv08am;
    private TextView tv09am;
    private TextView tv10am;
    private TextView tv11am;

    private TextView tv12pm;
    private TextView tv01pm;
    private TextView tv02pm;
    private TextView tv03pm;
    private TextView tv04pm;
    private TextView tv05pm;
    private TextView tv06pm;
    private TextView tv07pm;
    private TextView tv08pm;
    private TextView tv09pm;
    private TextView tv10pm;
    private TextView tv11pm;

    public ViewCalendarItem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewCalendarItem.
     */
    public static ViewCalendarItem newInstance(String param1, String param2) {
        ViewCalendarItem fragment = new ViewCalendarItem();
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
        view = inflater.inflate(R.layout.fragment_view_calendar_item, container, false);
        //calendar view
        calendar = view.findViewById(R.id.datePicker);
        dateSelection = view.findViewById(R.id.FABdateAdd);
        //checkbox view
        checkBoxSelection = view.findViewById(R.id.FABcheckbox);
        checkboxStuff = view.findViewById(R.id.scrollCheckBox);
        cb12am=view.findViewById(R.id.checkBox12am);
        cb01am=view.findViewById(R.id.checkBox01am);
        cb02am=view.findViewById(R.id.checkBox02am);
        cb03am=view.findViewById(R.id.checkBox03am);
        cb04am=view.findViewById(R.id.checkBox04am);
        cb05am=view.findViewById(R.id.checkBox05am);
        cb06am=view.findViewById(R.id.checkBox06am);
        cb07am=view.findViewById(R.id.checkBox07am);
        cb08am=view.findViewById(R.id.checkBox08am);
        cb09am=view.findViewById(R.id.checkBox09am);
        cb10am=view.findViewById(R.id.checkBox10am);
        cb11am=view.findViewById(R.id.checkBox11am);

        cb12pm=view.findViewById(R.id.checkBox12pm);
        cb01pm=view.findViewById(R.id.checkBox01pm);
        cb02pm=view.findViewById(R.id.checkBox02pm);
        cb03pm=view.findViewById(R.id.checkBox03pm);
        cb04pm=view.findViewById(R.id.checkBox04pm);
        cb05pm=view.findViewById(R.id.checkBox05pm);
        cb06pm=view.findViewById(R.id.checkBox06pm);
        cb07pm=view.findViewById(R.id.checkBox07pm);
        cb08pm=view.findViewById(R.id.checkBox08pm);
        cb09pm=view.findViewById(R.id.checkBox09pm);
        cb10pm=view.findViewById(R.id.checkBox10pm);
        cb11pm=view.findViewById(R.id.checkBox11pm);

        tv12am=view.findViewById(R.id.textView12am);
        tv01am=view.findViewById(R.id.textView01am);
        tv02am=view.findViewById(R.id.textView02am);
        tv03am=view.findViewById(R.id.textView03am);
        tv04am=view.findViewById(R.id.textView04am);
        tv05am=view.findViewById(R.id.textView05am);
        tv06am=view.findViewById(R.id.textView06am);
        tv07am=view.findViewById(R.id.textView07am);
        tv08am=view.findViewById(R.id.textView08am);
        tv09am=view.findViewById(R.id.textView09am);
        tv10am=view.findViewById(R.id.textView10am);
        tv11am=view.findViewById(R.id.textView11am);

        tv12pm=view.findViewById(R.id.textView12pm);
        tv01pm=view.findViewById(R.id.textView01pm);
        tv02pm=view.findViewById(R.id.textView02pm);
        tv03pm=view.findViewById(R.id.textView03pm);
        tv04pm=view.findViewById(R.id.textView04pm);
        tv05pm=view.findViewById(R.id.textView05pm);
        tv06pm=view.findViewById(R.id.textView06pm);
        tv07pm=view.findViewById(R.id.textView07pm);
        tv08pm=view.findViewById(R.id.textView08pm);
        tv09pm=view.findViewById(R.id.textView09pm);
        tv10pm=view.findViewById(R.id.textView10pm);
        tv11pm=view.findViewById(R.id.textView11pm);
//        String keyTemp;
        user = MainActivity.mAuth.getCurrentUser();
        if(myBCL == null) {
            myBCL = new ButtonClickListener();
        }
        /*if(myBCL2 == null) {
            myBCL2 = new ButtonClickListener();
        }*/
        dateSelection.setOnClickListener(myBCL);
        checkBoxSelection.setOnClickListener(myBCL);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0);
        final String tempID = pref.getString("calID", null);

        DatabaseReference textList = FirebaseDatabase.getInstance().getReference().child("groups").child(MainActivity.currentGroup.getGroupId()).child("scheduleChipItems").child(tempID).child("schedule");
        textList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                schedule = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
                if(schedule == null){
                    schedule = new HashMap<>();
                }
                Log.d("===Hash Map===", schedule.toString());

                Log.d("===View Created===", "success");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Database Error", databaseError.toString());

            }
        });



    }

    public void toCalendarView(View v) {
        view.findViewById(R.id.datePicker).setVisibility(View.VISIBLE);
        view.findViewById(R.id.FABdateAdd).setVisibility(View.VISIBLE);

        //view.findViewById(R.id.editTextUserDescription).setVisibility(View.GONE);
        //view.findViewById(R.id.button).setVisibility(View.GONE);

        view.findViewById(R.id.FABcheckbox).setVisibility(View.GONE);
        view.findViewById(R.id.scrollCheckBox).setVisibility(View.GONE);
    }

    private void calendarToDatabase(View view) {
        int yy = calendar.getYear();
        int mm = calendar.getMonth();
        int dd = calendar.getDayOfMonth();
        //string builder
        /*
        TODO database stuff
         */

        String tmp = stringBuilder(yy, mm ,dd);
        Log.d("cal txt", tmp);
        dayID = tmp;
    }

    private String stringBuilder(int yy, int mm, int dd){
        String tmpYear = String.valueOf(yy);
        String tmpMon = String.valueOf(mm+1);
        String tmpDay = String.valueOf(dd);
        if(mm < 10){
            tmpMon = "0" + tmpMon;
        }
        if(dd < 10){
            tmpDay = "0" + tmpDay;
        }

        String toReturn = tmpMon+tmpDay+tmpYear;

        return toReturn;
    }

    private HashMap<String, String> emptyDayBuilder(HashMap<String, String> myMap){
        String keyTemp;
        for(int i=0; i<24; i++) {
            if(i==0) {
                myMap.put("12am", "not scheduled");
            }
            else if(i<12) {
                keyTemp = ""+i+"am";
                myMap.put(keyTemp, "not scheduled");
            }
            else if(i==12) {
                myMap.put("12pm", "not scheduled");
            }
            else {
                int temp = i-12;
                keyTemp = ""+temp+"pm";
                myMap.put(keyTemp, "not scheduled");
            }
        }

        return myMap;
    }
    public void toCheckboxView(View v) {
        view.findViewById(R.id.datePicker).setVisibility(View.GONE);
        view.findViewById(R.id.FABdateAdd).setVisibility(View.GONE);

        //view.findViewById(R.id.editTextUserDescription).setVisibility(View.GONE);
        //view.findViewById(R.id.button).setVisibility(View.GONE);

        view.findViewById(R.id.FABcheckbox).setVisibility(View.VISIBLE);
        view.findViewById(R.id.scrollCheckBox).setVisibility(View.VISIBLE);

        if(schedule.get(dayID) == null){
            myMap = emptyDayBuilder(myMap);
        }
        else{
            myMap = schedule.get(dayID);
        }


        tv12am.setText(myMap.get("12am"));
        tv01am.setText(myMap.get("1am"));
        tv02am.setText(myMap.get("2am"));
        tv03am.setText(myMap.get("3am"));
        tv04am.setText(myMap.get("4am"));
        tv05am.setText(myMap.get("5am"));
        tv06am.setText(myMap.get("6am"));
        tv07am.setText(myMap.get("7am"));
        tv08am.setText(myMap.get("8am"));
        tv09am.setText(myMap.get("9am"));
        tv10am.setText(myMap.get("10am"));
        tv11am.setText(myMap.get("11am"));

        tv12pm.setText(myMap.get("12pm"));
        tv01pm.setText(myMap.get("1pm"));
        tv02pm.setText(myMap.get("2pm"));
        tv03pm.setText(myMap.get("3pm"));
        tv04pm.setText(myMap.get("4pm"));
        tv05pm.setText(myMap.get("5pm"));
        tv06pm.setText(myMap.get("6pm"));
        tv07pm.setText(myMap.get("7pm"));
        tv08pm.setText(myMap.get("8pm"));
        tv09pm.setText(myMap.get("9pm"));
        tv10pm.setText(myMap.get("10pm"));
        tv11pm.setText(myMap.get("11pm"));


        if(!myMap.get("12am").equals("not scheduled")){
            cb12am.setChecked(true);
            if( !myMap.get("12am").equals(MainActivity.userProfile.getUsername())){
                cb12am.setEnabled(false);
            }
        }
        if(!myMap.get("1am").equals("not scheduled")){
            cb01am.setChecked(true);
            if( !myMap.get("1am").equals(MainActivity.userProfile.getUsername())){
                cb01am.setEnabled(false);
            }
        }
        if(!myMap.get("2am").equals("not scheduled")){
            cb02am.setChecked(true);
            if( !myMap.get("2am").equals(MainActivity.userProfile.getUsername())){
                cb02am.setEnabled(false);
            }
        }
        if(!myMap.get("3am").equals("not scheduled")){
            cb03am.setChecked(true);
            if( !myMap.get("3am").equals(MainActivity.userProfile.getUsername())){
                cb03am.setEnabled(false);
            }
        }
        if(!myMap.get("4am").equals("not scheduled")){
            cb04am.setChecked(true);
            if( !myMap.get("4am").equals(MainActivity.userProfile.getUsername())){
                cb04am.setEnabled(false);
            }
        }
        if(!myMap.get("5am").equals("not scheduled")){
            cb05am.setChecked(true);
            if( !myMap.get("5am").equals(MainActivity.userProfile.getUsername())){
                cb05am.setEnabled(false);
            }
        }
        if(!myMap.get("6am").equals("not scheduled")){
            cb06am.setChecked(true);
            if( !myMap.get("6am").equals(MainActivity.userProfile.getUsername())){
                cb06am.setEnabled(false);
            }
        }
        if(!myMap.get("7am").equals("not scheduled")){
            cb07am.setChecked(true);
            if( !myMap.get("7am").equals(MainActivity.userProfile.getUsername())){
                cb07am.setEnabled(false);
            }
        }
        if(!myMap.get("8am").equals("not scheduled")){
            cb08am.setChecked(true);
            if( !myMap.get("8am").equals(MainActivity.userProfile.getUsername())){
                cb08am.setEnabled(false);
            }
        }
        if(!myMap.get("9am").equals("not scheduled")){
            cb09am.setChecked(true);
            if( !myMap.get("9am").equals(MainActivity.userProfile.getUsername())){
                cb09am.setEnabled(false);
            }
        }
        if(!myMap.get("10am").equals("not scheduled")){
            cb10am.setChecked(true);
            if( !myMap.get("10am").equals(MainActivity.userProfile.getUsername())){
                cb10am.setEnabled(false);
            }
        }
        if(!myMap.get("11am").equals("not scheduled")){
            cb11am.setChecked(true);
            if( !myMap.get("11am").equals(MainActivity.userProfile.getUsername())){
                cb11am.setEnabled(false);
            }
        }
        if(!myMap.get("12pm").equals("not scheduled")){
            cb12pm.setChecked(true);
            if( !myMap.get("12pm").equals(MainActivity.userProfile.getUsername())){
                cb12pm.setEnabled(false);
            }
        }
        if(!myMap.get("1pm").equals("not scheduled")){
            cb01pm.setChecked(true);
            if( !myMap.get("1pm").equals(MainActivity.userProfile.getUsername())){
                cb01pm.setEnabled(false);
            }
        }
        if(!myMap.get("2pm").equals("not scheduled")){
            cb02pm.setChecked(true);
            if( !myMap.get("2pm").equals(MainActivity.userProfile.getUsername())){
                cb02pm.setEnabled(false);
            }
        }
        if(!myMap.get("3pm").equals("not scheduled")){
            cb03pm.setChecked(true);
            if( !myMap.get("3pm").equals(MainActivity.userProfile.getUsername())){
                cb03pm.setEnabled(false);
            }
        }
        if(!myMap.get("4pm").equals("not scheduled")){
            cb04pm.setChecked(true);
            if( !myMap.get("4pm").equals(MainActivity.userProfile.getUsername())){
                cb04pm.setEnabled(false);
            }
        }
        if(!myMap.get("5pm").equals("not scheduled")){
            cb05pm.setChecked(true);
            if( !myMap.get("5pm").equals(MainActivity.userProfile.getUsername())){
                cb05pm.setEnabled(false);
            }
        }
        if(!myMap.get("6pm").equals("not scheduled")){
            cb06pm.setChecked(true);
            if( !myMap.get("6pm").equals(MainActivity.userProfile.getUsername())){
                cb06pm.setEnabled(false);
            }
        }
        if(!myMap.get("7pm").equals("not scheduled")){
            cb07pm.setChecked(true);
            if( !myMap.get("7pm").equals(MainActivity.userProfile.getUsername())){
                cb07pm.setEnabled(false);
            }
        }
        if(!myMap.get("8pm").equals("not scheduled")){
            cb08pm.setChecked(true);
            if( !myMap.get("8pm").equals(MainActivity.userProfile.getUsername())){
                cb08pm.setEnabled(false);
            }
        }
        if(!myMap.get("9pm").equals("not scheduled")){
            cb09pm.setChecked(true);
            if( !myMap.get("9pm").equals(MainActivity.userProfile.getUsername())){
                cb09pm.setEnabled(false);
            }
        }
        if(!myMap.get("10pm").equals("not scheduled")){
            cb10pm.setChecked(true);
            if( !myMap.get("10pm").equals(MainActivity.userProfile.getUsername())){
                cb10pm.setEnabled(false);
            }
        }
        if(!myMap.get("11pm").equals("not scheduled")){
            cb11pm.setChecked(true);
            if( !myMap.get("11pm").equals(MainActivity.userProfile.getUsername())){
                cb11pm.setEnabled(false);
            }
        }




    }


    private void checkBoxtoDatabase(View view) {
        //From: https://stackoverflow.com/questions/9411540/android-get-checked-checkbox-values
        boolean[] checkedBoxes = new boolean[24];
        //boolean hi = ((CheckBox)findViewByID(R.id.checkBox01am)).isChecked();
        checkedBoxes[0] = (cb12am.isChecked() && cb12am.isEnabled());
        checkedBoxes[1] = (cb01am.isChecked() && cb01am.isEnabled());
        checkedBoxes[2] = (cb02am.isChecked() && cb02am.isEnabled());
        checkedBoxes[3] = (cb03am.isChecked() && cb03am.isEnabled());
        checkedBoxes[4] = (cb04am.isChecked() && cb04am.isEnabled());
        checkedBoxes[5] = (cb05am.isChecked() && cb05am.isEnabled());
        checkedBoxes[6] = (cb06am.isChecked() && cb06am.isEnabled());
        checkedBoxes[7] = (cb07am.isChecked() && cb07am.isEnabled());
        checkedBoxes[8] = (cb08am.isChecked() && cb08am.isEnabled());
        checkedBoxes[9] = (cb09am.isChecked() && cb09am.isEnabled());
        checkedBoxes[10] = (cb10am.isChecked() && cb10am.isEnabled());
        checkedBoxes[11] = (cb11am.isChecked() && cb11am.isEnabled());
        checkedBoxes[12] = (cb12pm.isChecked() && cb12pm.isEnabled());
        checkedBoxes[13] = (cb01pm.isChecked() && cb01pm.isEnabled());
        checkedBoxes[14] = (cb02pm.isChecked() && cb02pm.isEnabled());
        checkedBoxes[15] = (cb03pm.isChecked() && cb03pm.isEnabled());
        checkedBoxes[16] = (cb04pm.isChecked() && cb04pm.isEnabled());
        checkedBoxes[17] = (cb05pm.isChecked() && cb05pm.isEnabled());
        checkedBoxes[18] = (cb06pm.isChecked() && cb06pm.isEnabled());
        checkedBoxes[19] = (cb07pm.isChecked() && cb07pm.isEnabled());
        checkedBoxes[20] = (cb08pm.isChecked() && cb08pm.isEnabled());
        checkedBoxes[21] = (cb09pm.isChecked() && cb09pm.isEnabled());
        checkedBoxes[22] = (cb10pm.isChecked() && cb10pm.isEnabled());
        checkedBoxes[23] = (cb11pm.isChecked() && cb11pm.isEnabled());
        String keyTemp;

        for(int i=0; i<24; i++) {
            if(checkedBoxes[i]) {
                if(i==0) {
                    keyTemp = "12am";
                }
                else if(i<12) {
                    keyTemp = i+"am";
                }
                else if(i==12) {
                    keyTemp = "12pm";
                }
                else {
                    keyTemp = (i-12)+"pm";
                }
                //https://stackoverflow.com/questions/4157972/how-to-update-a-value-given-a-key-in-a-java-hashmap
                try {
//                    myMap.put(keyTemp, user.getUid());
                    myMap.put(keyTemp, MainActivity.userProfile.getUsername());

                }
                catch (Exception e) {
                    myMap.put(keyTemp, "unknown user scheduled");
                }
            }
        }

        SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0);
        ArrayList<ChipItemSchedule> chipitems = MainActivity.currentGroup.getScheduleChipItems();
        int itemIndex = Integer.parseInt(pref.getString("calID", null));
        schedule.put(dayID, myMap);
        chipitems.get(itemIndex).setSchedule(schedule);
//        adapter.clear();
        MainActivity.currentGroup.setScheduleChipItems(chipitems);
        MainActivity.mDatabase.child("groups").child(currentGroup.getGroupId()).child("scheduleChipItems").setValue(currentGroup.getScheduleChipItems());

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

    private class ButtonClickListener implements View.OnClickListener { ButtonClickListener(){}
        @Override
        public void onClick(View view) {
            if(mListener != null) {
                int buttonID = view.getId();
                if(buttonID == R.id.FABdateAdd) {
                    calendarToDatabase(view);
                    toCheckboxView(view);
                }
                else if(buttonID == R.id.FABcheckbox) {
                    checkBoxtoDatabase(view);
                    toCheckboxView(view);
                    //toDescriptionView(view);
                    /*TODO user id stuff*/
                }
                /*else if(buttonID == R.id.button) {
                    toCheckboxView(view);
                }*/
            }
        }
    }
}
