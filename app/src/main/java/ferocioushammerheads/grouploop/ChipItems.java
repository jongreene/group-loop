package ferocioushammerheads.grouploop;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class ChipItems extends AppCompatActivity
        implements ViewAllChipItems.OnFragmentInteractionListener,
            ViewCalendarItem.OnFragmentInteractionListener,
            ViewListItem.OnFragmentInteractionListener
{

    private static final String TAG = "ChipItemsDebug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chip_items);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = ViewAllChipItems.class;

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.ChipItemInterfaceFrame, fragment).commit();
        }
    }

    public void onFragmentInteraction(Uri uri){

    }

    public void onFragmentInteraction(String test){
        Log.d(TAG, "Value passed:" + test);
    }

    public void onFragmentInteraction(View view){
        if (view.getId() == R.id.addChipItem) {

        } else if (view.getId() == R.id.ChipItemSearch) {

        }
    }
}
