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
        calendar = view.findViewById(R.id.datePicker);
        dateSelection = view.findViewById(R.id.floatingActionButton1);


        return view;
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
}
