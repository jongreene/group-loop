package ferocioushammerheads.grouploop;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class UserAccount extends AppCompatActivity
        implements Login.OnFragmentInteractionListener,
        UserAccountPreferences.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        DrawerLayout drawer = findViewById(R.id.drawer_layout);


//        fragmentChanger(Login.class,R.id.user_account_frag_frame, "Login");
        fragmentChanger(UserAccountPreferences.class,R.id.user_account_frag_frame, "UserAccountPreferences");
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

        fragmentManager.beginTransaction().replace(containerName, fragment, fragName).commit();
    }

    public void onFragmentInteraction(){

    }

    public void onFragmentInteraction(View view){
        if (view.getId() == R.id.account_pref_login) {
//            switch to add chip item fragment
            fragmentChanger(Login.class,R.id.user_account_frag_frame,"Login");
        }

    }
}
