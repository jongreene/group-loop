/**
 * Uses a widget to allow users to create schedules
 * for objects that are shared in the group
 *
 * @author  Ferocious Hammerheads
 * @version Beta 1.0
 * @since   2017-11-5
 */

package ferocioushammerheads.grouploop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

public class ScheduleItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_item);
    }

    //this is for transitioning from the checkbox times to the discriptions
    public void onClickAdd(View v) {
        //From: https://stackoverflow.com/questions/9411540/android-get-checked-checkbox-values
        boolean time12AM = ((CheckBox)findViewById(R.id.checkBox32)).isChecked();
        boolean time01AM = ((CheckBox)findViewById(R.id.checkBox34)).isChecked();
        boolean time02AM = ((CheckBox)findViewById(R.id.checkBox35)).isChecked();
        boolean time03AM = ((CheckBox)findViewById(R.id.checkBox36)).isChecked();
        boolean time04AM = ((CheckBox)findViewById(R.id.checkBox37)).isChecked();
        boolean time05AM = ((CheckBox)findViewById(R.id.checkBox38)).isChecked();
        boolean time06AM = ((CheckBox)findViewById(R.id.checkBox39)).isChecked();
        boolean time07AM = ((CheckBox)findViewById(R.id.checkBox40)).isChecked();
        boolean time08AM = ((CheckBox)findViewById(R.id.checkBox41)).isChecked();
        boolean time09AM = ((CheckBox)findViewById(R.id.checkBox42)).isChecked();
        boolean time10AM = ((CheckBox)findViewById(R.id.checkBox)).isChecked();
        boolean time11AM = ((CheckBox)findViewById(R.id.checkBox2)).isChecked();
        boolean time12PM = ((CheckBox)findViewById(R.id.checkBox3)).isChecked();
        boolean time01PM = ((CheckBox)findViewById(R.id.checkBox4)).isChecked();
        boolean time02PM = ((CheckBox)findViewById(R.id.checkBox5)).isChecked();
        boolean time03PM = ((CheckBox)findViewById(R.id.checkBox6)).isChecked();
        boolean time04PM = ((CheckBox)findViewById(R.id.checkBox7)).isChecked();
        boolean time05PM = ((CheckBox)findViewById(R.id.checkBox8)).isChecked();
        boolean time06PM = ((CheckBox)findViewById(R.id.checkBox9)).isChecked();
        boolean time07PM = ((CheckBox)findViewById(R.id.checkBox11)).isChecked();
        boolean time08PM = ((CheckBox)findViewById(R.id.checkBox12)).isChecked();
        boolean time09PM = ((CheckBox)findViewById(R.id.checkBox13)).isChecked();
        boolean time10PM = ((CheckBox)findViewById(R.id.checkBox14)).isChecked();
        boolean time11PM = ((CheckBox)findViewById(R.id.checkBox15)).isChecked();
        /*
        TODO:
        database stuff
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
        //From: https://stackoverflow.com/questions/15301157/how-to-set-the-text-entered-in-android-studio-edittext-to-a-variable
        String description = ((EditText)findViewById(R.id.editText2)).getText().toString();
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
