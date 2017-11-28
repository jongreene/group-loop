package ferocioushammerheads.grouploop;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewCalendarItem.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewCalendarItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewCalendarItem extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;

    private OnFragmentInteractionListener mListener;

    //calendar view
    private DatePicker calendar;
    private FloatingActionButton dateSelection;
    //add description view
    private EditText description;
    private Button addDescription;
    //checkbox view
    private FloatingActionButton checkBoxSelection;
    //... the checkboxes
    private CheckBox cb12am; //C heck B ox 12am
    private CheckBox cb1am;
    private CheckBox cb2am;
    private CheckBox cb3am;
    private CheckBox cb4am;
    private CheckBox cb5am;
    private CheckBox cb6am;
    private CheckBox cb7am;
    private CheckBox cb8am;
    private CheckBox cb9am;
    private CheckBox cb10am;
    private CheckBox cb11am;
    private CheckBox cb12pm;
    private CheckBox cb1pm;
    private CheckBox cb2pm;
    private CheckBox cb3pm;
    private CheckBox cb4pm;
    private CheckBox cb5pm;
    private CheckBox cb6pm;
    private CheckBox cb7pm;
    private CheckBox cb8pm;
    private CheckBox cb9pm;
    private CheckBox cb10pm;
    private CheckBox cb11pm;
    //...  the textboxes
    private TextView tv12am;
    private TextView tv1am;
    private TextView tv2am;
    private TextView tv3am;
    private TextView tv4am;
    private TextView tv5am;
    private TextView tv6am;
    private TextView tv7am;
    private TextView tv8am;
    private TextView tv9am;
    private TextView tv10am;
    private TextView tv11am;
    private TextView tv12pm;
    private TextView tv1pm;
    private TextView tv2pm;
    private TextView tv3pm;
    private TextView tv4pm;
    private TextView tv5pm;
    private TextView tv6pm;
    private TextView tv7pm;
    private TextView tv8pm;
    private TextView tv9pm;
    private TextView tv10pm;
    private TextView tv11pm;

    private ButtonClickListener myBCL;

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
    // TODO: Rename and change types and number of parameters
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
        dateSelection = view.findViewById(R.id.floatingActionButton1);
        //checkbox view
        checkBoxSelection = view.findViewById(R.id.floatingActionButton2);
        cb12am = view.findViewById(R.id.checkBox12am);
        cb1am = view.findViewById(R.id.checkBox01am);
        cb2pm = view.findViewById(R.id.checkBox02am);
        cb3am = view.findViewById(R.id.checkBox03am);
        cb4am = view.findViewById(R.id.checkBox04am);
        cb5am = view.findViewById(R.id.checkBox05am);
        cb6am = view.findViewById(R.id.checkBox06am);
        cb7am = view.findViewById(R.id.checkBox07am);
        cb8am = view.findViewById(R.id.checkBox08am);
        cb9am = view.findViewById(R.id.checkBox09am);
        cb10am = view.findViewById(R.id.checkBox10am);
        cb11am = view.findViewById(R.id.checkBox11am);
        cb12pm = view.findViewById(R.id.checkBox12pm);
        cb1pm = view.findViewById(R.id.checkBox01pm);
        cb2pm = view.findViewById(R.id.checkBox02pm);
        cb3pm = view.findViewById(R.id.checkBox03pm);
        cb4pm = view.findViewById(R.id.checkBox04pm);
        cb5pm = view.findViewById(R.id.checkBox05pm);
        cb6pm = view.findViewById(R.id.checkBox06pm);
        cb7pm = view.findViewById(R.id.checkBox07pm);
        cb8pm = view.findViewById(R.id.checkBox08pm);
        cb9pm = view.findViewById(R.id.checkBox09pm);
        cb10pm = view.findViewById(R.id.checkBox10pm);
        cb11pm = view.findViewById(R.id.checkBox11pm);
        tv12am = view.findViewById(R.id.textView12am);
        tv1am = view.findViewById(R.id.textView01am);
        tv2am = view.findViewById(R.id.textView02am);
        tv3am = view.findViewById(R.id.textView03am);
        tv4am = view.findViewById(R.id.textView04am);
        tv5am = view.findViewById(R.id.textView05am);
        tv6am = view.findViewById(R.id.textView06am);
        tv7am = view.findViewById(R.id.textView07am);
        tv8am = view.findViewById(R.id.textView08am);
        tv9am = view.findViewById(R.id.textView09am);
        tv10am = view.findViewById(R.id.textView10am);
        tv11am = view.findViewById(R.id.textView11am);
        tv12pm = view.findViewById(R.id.textView12pm);
        tv1pm = view.findViewById(R.id.textView01pm);
        tv2pm = view.findViewById(R.id.textView02pm);
        tv3pm = view.findViewById(R.id.textView03pm);
        tv4pm = view.findViewById(R.id.textView04pm);
        tv5pm = view.findViewById(R.id.textView05pm);
        tv6pm = view.findViewById(R.id.textView06pm);
        tv7pm = view.findViewById(R.id.textView07pm);
        tv8pm = view.findViewById(R.id.textView08pm);
        tv9pm = view.findViewById(R.id.textView09pm);
        tv10pm = view.findViewById(R.id.textView10pm);
        tv11pm = view.findViewById(R.id.textView11pm);
        //description view
        description = view.findViewById(R.id.editTextUserDescription);
        addDescription = view.findViewById(R.id.button);
        //buttons
        if(myBCL == null) {
            myBCL = new ButtonClickListener();
        }
        dateSelection.setOnClickListener(myBCL);
        checkBoxSelection.setOnClickListener(myBCL);
        addDescription.setOnClickListener(myBCL);
        /*
         * TODO: Firebase stuff
         */
        return view;
    }

    public void toCalendarView(View v) {
        view.findViewById(R.id.datePicker).setVisibility(View.VISIBLE);
        view.findViewById(R.id.floatingActionButton1).setVisibility(View.VISIBLE);

        view.findViewById(R.id.editTextUserDescription).setVisibility(View.GONE);
        view.findViewById(R.id.button).setVisibility(View.GONE);

        view.findViewById(R.id.floatingActionButton2).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox12am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox01am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox02am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox03am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox04am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox05am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox06am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox07am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox08am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox09am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox10am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox11am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox12pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox01pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox02pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox03pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox04pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox05pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox06pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox07pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox08pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox09pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox10pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox11pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView12am).setVisibility(View.GONE);
        view.findViewById(R.id.textView01am).setVisibility(View.GONE);
        view.findViewById(R.id.textView02am).setVisibility(View.GONE);
        view.findViewById(R.id.textView03am).setVisibility(View.GONE);
        view.findViewById(R.id.textView04am).setVisibility(View.GONE);
        view.findViewById(R.id.textView05am).setVisibility(View.GONE);
        view.findViewById(R.id.textView06am).setVisibility(View.GONE);
        view.findViewById(R.id.textView07am).setVisibility(View.GONE);
        view.findViewById(R.id.textView08am).setVisibility(View.GONE);
        view.findViewById(R.id.textView09am).setVisibility(View.GONE);
        view.findViewById(R.id.textView10am).setVisibility(View.GONE);
        view.findViewById(R.id.textView11am).setVisibility(View.GONE);
        view.findViewById(R.id.textView12pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView01pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView02pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView03pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView04pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView05pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView06pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView07pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView08pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView09pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView10pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView11pm).setVisibility(View.GONE);
    }

    public void toCheckboxView(View v) {
        view.findViewById(R.id.datePicker).setVisibility(View.GONE);
        view.findViewById(R.id.floatingActionButton1).setVisibility(View.GONE);

        view.findViewById(R.id.editTextUserDescription).setVisibility(View.GONE);
        view.findViewById(R.id.button).setVisibility(View.GONE);

        view.findViewById(R.id.floatingActionButton2).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox12am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox01am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox02am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox03am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox04am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox05am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox06am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox07am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox08am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox09am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox10am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox11am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox12pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox01pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox02pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox03pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox04pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox05pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox06pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox07pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox08pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox09pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox10pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.checkBox11pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView12am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView01am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView02am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView03am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView04am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView05am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView06am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView07am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView08am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView09am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView10am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView11am).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView12pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView01pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView02pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView03pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView04pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView05pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView06pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView07pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView08pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView09pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView10pm).setVisibility(View.VISIBLE);
        view.findViewById(R.id.textView11pm).setVisibility(View.VISIBLE);
    }

    public void toDescriptionView(View v) {
        view.findViewById(R.id.datePicker).setVisibility(View.GONE);
        view.findViewById(R.id.floatingActionButton1).setVisibility(View.GONE);

        view.findViewById(R.id.editTextUserDescription).setVisibility(View.VISIBLE);
        view.findViewById(R.id.button).setVisibility(View.VISIBLE);

        view.findViewById(R.id.floatingActionButton2).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox12am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox01am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox02am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox03am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox04am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox05am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox06am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox07am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox08am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox09am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox10am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox11am).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox12pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox01pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox02pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox03pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox04pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox05pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox06pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox07pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox08pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox09pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox10pm).setVisibility(View.GONE);
        view.findViewById(R.id.checkBox11pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView12am).setVisibility(View.GONE);
        view.findViewById(R.id.textView01am).setVisibility(View.GONE);
        view.findViewById(R.id.textView02am).setVisibility(View.GONE);
        view.findViewById(R.id.textView03am).setVisibility(View.GONE);
        view.findViewById(R.id.textView04am).setVisibility(View.GONE);
        view.findViewById(R.id.textView05am).setVisibility(View.GONE);
        view.findViewById(R.id.textView06am).setVisibility(View.GONE);
        view.findViewById(R.id.textView07am).setVisibility(View.GONE);
        view.findViewById(R.id.textView08am).setVisibility(View.GONE);
        view.findViewById(R.id.textView09am).setVisibility(View.GONE);
        view.findViewById(R.id.textView10am).setVisibility(View.GONE);
        view.findViewById(R.id.textView11am).setVisibility(View.GONE);
        view.findViewById(R.id.textView12pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView01pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView02pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView03pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView04pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView05pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView06pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView07pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView08pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView09pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView10pm).setVisibility(View.GONE);
        view.findViewById(R.id.textView11pm).setVisibility(View.GONE);
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
                if(buttonID == R.id.floatingActionButton1) {
                    toCheckboxView(view);
                }
                else if(buttonID == R.id.floatingActionButton2) {
                    toDescriptionView(view);
                }
                else if(buttonID == R.id.button) {
                    toCheckboxView(view);
                }
            }
        }
    }
}
