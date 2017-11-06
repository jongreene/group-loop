package ferocioushammerheads.grouploop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

public class ScheduleItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_item);
    }

    //this is for transitioning from the checkbox times to the discriptions
    public void onClickAdd(View v) {
        /*
        TODO:
        database stuff
        checkbox stuff
         */
        findViewById(R.id.datePicker).setVisibility(View.GONE);
        findViewById(R.id.floatingActionButton2).setVisibility(View.GONE);
        findViewById(R.id.scrollView2).setVisibility(View.GONE);
        findViewById(R.id.editText2).setVisibility(View.VISIBLE);
        findViewById(R.id.button2).setVisibility(View.VISIBLE);
        findViewById(R.id.button3).setVisibility(View.GONE);
    }

    //this is for transitioning from the discriptions to the checkbox times
    public void onClickDone(View v) {
        /*
        TODO:
        database stuff
         */
        findViewById(R.id.datePicker).setVisibility(View.GONE);
        findViewById(R.id.floatingActionButton2).setVisibility(View.VISIBLE);
        findViewById(R.id.scrollView2).setVisibility(View.VISIBLE);
        findViewById(R.id.editText2).setVisibility(View.GONE);
        findViewById(R.id.button2).setVisibility(View.GONE);
        findViewById(R.id.button3).setVisibility(View.GONE);
    }

    //this is for transitioning from the datepicker to the checkbox times
    public void onClickPickDate(View v) {
        int yy = ((DatePicker)findViewById(R.id.datePicker)).getYear();
        int mm = ((DatePicker)findViewById(R.id.datePicker)).getMonth();
        int dd = ((DatePicker)findViewById(R.id.datePicker)).getDayOfMonth();

        /*String test = ""+dd+"/"+mm+"/"+yy+"";
        ((TextView)findViewById(R.id.textView2)).setText(test);
        */
        /*
        TODO:
        database stuff
         */
        findViewById(R.id.datePicker).setVisibility(View.GONE);
        findViewById(R.id.floatingActionButton2).setVisibility(View.VISIBLE);
        findViewById(R.id.scrollView2).setVisibility(View.VISIBLE);
        findViewById(R.id.editText2).setVisibility(View.GONE);
        findViewById(R.id.button2).setVisibility(View.GONE);
        findViewById(R.id.button3).setVisibility(View.GONE);
    }

}
