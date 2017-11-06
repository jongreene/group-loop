package ferocioushammerheads.grouploop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.*;
import android.view.View;

public class ScheduleItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_item);
    }

    public void onClickAdd(View v) {
        /*
        TODO:
        database stuff
        checkbox stuff
         */
        findViewById(R.id.floatingActionButton2).setVisibility(View.GONE);
        findViewById(R.id.scrollView2).setVisibility(View.GONE);
        findViewById(R.id.editText2).setVisibility(View.VISIBLE);
        findViewById(R.id.button2).setVisibility(View.VISIBLE);
    }

    public void onClickDone(View v) {
        /*
        TODO:
        database stuff
         */
        findViewById(R.id.floatingActionButton2).setVisibility(View.VISIBLE);
        findViewById(R.id.scrollView2).setVisibility(View.VISIBLE);
        findViewById(R.id.editText2).setVisibility(View.GONE);
        findViewById(R.id.button2).setVisibility(View.GONE);
    }

}
