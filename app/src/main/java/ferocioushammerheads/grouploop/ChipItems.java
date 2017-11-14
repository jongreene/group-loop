package ferocioushammerheads.grouploop;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class ChipItems extends AppCompatActivity
        implements ViewAllChipItems.OnFragmentInteractionListener,
            ViewCalendarItem.OnFragmentInteractionListener,
            ViewListItem.OnFragmentInteractionListener,
            CreateChipItem.OnFragmentInteractionListener
{

    private static final String TAG = "ChipItemsDebug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chip_items);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);


        fragmentChanger(ViewAllChipItems.class,R.id.ChipItemInterfaceFrame);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void fragmentChanger(Class newFragment, int containerName){
        Fragment fragment = null;
        Class fragmentClass = newFragment;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(containerName, fragment).commit();
    }

    public void onFragmentInteraction(){
//        blank fragment listener
    }

    public void onFragmentInteraction(String test){
        Log.d(TAG, "Value passed:" + test);
    }

    public void onFragmentInteraction(View view){
        if (view.getId() == R.id.addChipItem) {
//            switch to add chip item fragment
            fragmentChanger(CreateChipItem.class,R.id.ChipItemInterfaceFrame);
        }
//        will be used when new buttons are added from other fragments
//        else if (view.getId() == R.id.ChipItemSearch) {
//
//        }
    }
}
