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

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements AccountToolsHelper{
    private static final String TAG = "AccountTools";


    private Button mChipItemsButton, mPreferencesButton;

    private ButtonClickListener mButtonClickListener;

    public static FirebaseUser user;
    public static UserProfile userProfile;

    public static UserGroup currentGroup;

    public static AccountTools firebaseTools;

    // [START declare_auth]
    public static FirebaseAuth mAuth;
    // [END declare_auth]
    public static DatabaseReference mDatabase;

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        set current user
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (mButtonClickListener == null) {
            mButtonClickListener = new ButtonClickListener();
        }

//        set button id's
        mChipItemsButton = this.findViewById(R.id.chipItemsButtons);
        mPreferencesButton = this.findViewById(R.id.preferencesButton);

//        attach listeners to buttons
        mChipItemsButton.setOnClickListener(mButtonClickListener);
        mPreferencesButton.setOnClickListener(mButtonClickListener);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser();
        // [END initialize_auth]

        mDatabase = FirebaseDatabase.getInstance().getReference();
        
        AccountTools tmpTools = AccountTools.getInstance();
        tmpTools.setupTools(this, mAuth, mDatabase);

        if(user != null) {
            tmpTools.loadProfile();
        }

        updateUserEnvironment();

        context = this.getBaseContext();
    }

    private class ButtonClickListener implements View.OnClickListener {
        ButtonClickListener() {}

        @Override
        public void onClick(View view) {
            int clickedId = view.getId();
            if (clickedId == R.id.chipItemsButtons || clickedId == R.id.preferencesButton) {
                changeActivity(view);
            } else {

            }
        }
    }

    public void updateUserEnvironment(){
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
        }
        else {
            Log.d(TAG, "onAuthStateChanged:signed_out");

            findViewById(R.id.chipItemsButtons).setVisibility(View.GONE);

            login_button.setText(R.string.button_login_logout_logged_out);
        }
    }

    public void changeActivity(View view){
        if (view.getId() == R.id.chipItemsButtons) {
            Intent intent = new Intent(this, ChipItems.class);
            //Intent intent = new Intent(this, ViewCalendarItem.class);
            startActivity(intent);
        }
        else if (view.getId() == R.id.preferencesButton) {
            Intent intent = new Intent(this, UserAccount.class);

            Bundle b = new Bundle();
//            1: logged in. 2: otherwise
            if(user!=null) {
                b.putInt("key", 1);
            } else {
                b.putInt("key", -1);
            }
//            Put your key in your next Intent
            intent.putExtras(b);

            startActivity(intent);
            finish();
            updateUserEnvironment();
        }
    }

    // Define the actual handler for the event.
    public void loggedInEvent() {


    }

    // Define the actual handler for the event.
    public static DatabaseReference mDatabaseRef;
    public void loadProfileEvent() {}
    public void toastUp(String toastText){
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }
}