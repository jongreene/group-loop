package ferocioushammerheads.grouploop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import static ferocioushammerheads.grouploop.MainActivity.userProfile;

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
//        fragmentChanger(ViewCalendarItem.class,R.id.ChipItemInterfaceFrame, "ViewCalendarItem");
        fragmentChanger(ViewAllChipItems.class,R.id.ChipItemInterfaceFrame, "ViewAllChipItems");



//        AccountTools tmpTools = AccountTools.getInstance();
//        tmpTools.loadProfileEvent();
////        load current group
//        tmpTools.loadGroup(userProfile.getCurrentGroup());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    //    override back button in toolbar to swap fragment instead of restart activity
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment myFragment = fragmentManager.findFragmentByTag("ViewAllChipItems");
        switch (item.getItemId()) {
            case R.id.action_logout:
                if (MainActivity.user != null) {
                    AccountTools tmpTools = MainActivity.firebaseTools.getInstance();
                    tmpTools.signOut();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Not logged in.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.action_change_group:
                if (MainActivity.user != null) {
                    Intent intent = new Intent(this, UserAccount.class);
                    Bundle b = new Bundle();
                    // will open ChangeGroup fragment
                    b.putInt("key", 3);
                    // Put your key in your next Intent
                    intent.putExtras(b);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Not logged in.", Toast.LENGTH_SHORT).show();
                }
                break;
            case android.R.id.home:
                if (myFragment != null && myFragment.isVisible()) {
                    Toast.makeText(getApplicationContext(), "Default behavior", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Overriding default", Toast.LENGTH_SHORT).show();
                    if (fragmentManager.findFragmentByTag("CreateChipItem") != null && fragmentManager.findFragmentByTag("CreateChipItem").isVisible()) {
                        fragmentChanger(ViewAllChipItems.class, R.id.ChipItemInterfaceFrame, "ViewAllChipItems");
                    } else if (fragmentManager.findFragmentByTag("ViewListItem") != null && fragmentManager.findFragmentByTag("ViewListItem").isVisible()) {
                        fragmentChanger(ViewAllChipItems.class, R.id.ChipItemInterfaceFrame, "ViewAllChipItems");
                    } else {
                        fragmentChanger(ViewAllChipItems.class, R.id.ChipItemInterfaceFrame, "ViewAllChipItems");
                    }
                }
                break;
        }
        return true;
    }

    @Override
    //    override back button to swap fragment instead of restart activity
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment myFragment = fragmentManager.findFragmentByTag("ViewAllChipItems");
        if (myFragment != null && myFragment.isVisible()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            if (fragmentManager.findFragmentByTag("CreateChipItem") != null && fragmentManager.findFragmentByTag("CreateChipItem").isVisible()) {
                fragmentChanger(ViewAllChipItems.class, R.id.ChipItemInterfaceFrame, "ViewAllChipItems");
            } else if (fragmentManager.findFragmentByTag("ViewListItem") != null && fragmentManager.findFragmentByTag("ViewListItem").isVisible()) {
                fragmentChanger(ViewAllChipItems.class, R.id.ChipItemInterfaceFrame, "ViewAllChipItems");
            } else {
                fragmentChanger(ViewAllChipItems.class, R.id.ChipItemInterfaceFrame, "ViewAllChipItems");
            }
        }
    }

    public void fragmentChanger(Class newFragment, int containerName, String fragName){
        Fragment fragment = null;
        Class fragmentClass = newFragment;
        FragmentManager fragmentManager = getSupportFragmentManager();

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (fragName == "ViewAllChipItems") {
            getSupportActionBar().setTitle("View All Chip Items");
        } else if (fragName == "ViewCalendarItem") {
            getSupportActionBar().setTitle("View Calendar Item");
        } else if (fragName == "ViewListItem") {
            getSupportActionBar().setTitle("View List Item");
        } else if (fragName == "CreateChipItem") {
            getSupportActionBar().setTitle("Create Chip Item");
        }

        fragmentManager.beginTransaction().replace(containerName, fragment, fragName).commit();
    }

    public void onFragmentInteraction(){
//        blank fragment listener
    }

    public void onFragmentInteraction(String frag){

        if(frag.equals("List")){
            fragmentChanger(ViewListItem.class, R.id.ChipItemInterfaceFrame, "ViewListItem");

        }
        else{
            fragmentChanger(ViewCalendarItem.class, R.id.ChipItemInterfaceFrame, "ViewCalendarItem");

        }

        Log.d(TAG, "Value passed:" + frag);
    }

    public void onFragmentInteraction(View view){
        if (view.getId() == R.id.addChipItem) {
//            switch to add chip item fragment
            fragmentChanger(CreateChipItem.class,R.id.ChipItemInterfaceFrame,"CreateChipItem");
        }
        else{
            fragmentChanger(ViewListItem.class, R.id.ChipItemInterfaceFrame, "ViewListItem");
        }
//        will be used when new buttons are added from other fragments
//        else if (view.getId() == R.id.ChipItemSearch) {
//
//        }
    }
}
