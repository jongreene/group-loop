/**
 * Group loop is a program designed for people living in
 * groups (such as dorms, college houses, apartments)
 * and allows them to communicate and coordinate their
 * daily lives. This is provided through the use of various
 * data types which are available to all users in a
 * particular group. Groups are verified using online
 * authentication(firebase) and can view live updates to
 * the data store in a real time database(firebase database)
 *
 * @author  Ferocious Hammerheads
 * @version Beta 1.0
 * @since   2017-11-5
 */

package ferocioushammerheads.grouploop;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "FirebaseSession";

    private Button ChangeGroupItems;
    private Button ChangeLogin;

    private Button mScheduleDemoButton;
    private Button mChipItemsButton;
    private Button mPreferencesButton;

    private ButtonClickListener mButtonClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

//        Intent intent = new Intent(this, ChipItems.class);
//        Intent intent = new Intent(this, UserAccount.class);
//        startActivity(intent);

        mScheduleDemoButton = this.findViewById(R.id.scheduleDemoButton);
        mChipItemsButton = this.findViewById(R.id.chipItemsButtons);
        mPreferencesButton = this.findViewById(R.id.preferencesButton);

        if (mButtonClickListener == null) {
            mButtonClickListener = new ButtonClickListener();
        }
        mScheduleDemoButton.setOnClickListener(mButtonClickListener);
        mChipItemsButton.setOnClickListener(mButtonClickListener);
        mPreferencesButton.setOnClickListener(mButtonClickListener);

        updateUserEnvironment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        return true;
    }

    private class ButtonClickListener implements View.OnClickListener {
        ButtonClickListener() {}

        @Override
        public void onClick(View view) {
            int clickedId = view.getId();
            if (clickedId == R.id.scheduleDemoButton || clickedId == R.id.chipItemsButtons || clickedId == R.id.preferencesButton) {
                changeActivity(view);
            } else {

            }
        }
    }

    public void updateUserEnvironment(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Button login_button = findViewById(R.id.preferencesButton);

        if (user != null) {
            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

            if(user.isEmailVerified()){
                login_button.setText(R.string.button_login_logout_logged_in);
            }
            else{
                login_button.setText(R.string.button_login_logout_logged_in_no_email);
            }

            findViewById(R.id.chipItemsButtons).setVisibility(View.VISIBLE);
            findViewById(R.id.scheduleDemoButton).setVisibility(View.VISIBLE);

        } else {
            Log.d(TAG, "onAuthStateChanged:signed_out");

            findViewById(R.id.chipItemsButtons).setVisibility(View.GONE);
            findViewById(R.id.scheduleDemoButton).setVisibility(View.GONE);

            login_button.setText(R.string.button_login_logout_logged_out);
        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            updateUserEnvironment();
        }
    }

    public void changeActivity(View view){
        if (view.getId() == R.id.scheduleDemoButton) {
            Intent intent = new Intent(this, ScheduleItem.class);
            startActivity(intent);        }
        else if (view.getId() == R.id.chipItemsButtons) {
            Intent intent = new Intent(this, ChipItems.class);
            startActivity(intent);
        }
        else if (view.getId() == R.id.preferencesButton) {
            Intent intent = new Intent(this, UserAccount.class);
            startActivityForResult(intent, 1);
            updateUserEnvironment();
        }
    }
}
