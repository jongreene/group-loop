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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, ChipItems.class);
//        Intent intent = new Intent(this, LoginLogout.class);
        startActivity(intent);

//        updateUserEnvironment();
    }

    public void updateUserEnvironment(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Button login_button = findViewById(R.id.viewChangeLogin);

        if (user != null) {
            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

            if(user.isEmailVerified()){
                login_button.setText(R.string.button_login_logout_logged_in);
            }
            else{
                login_button.setText(R.string.button_login_logout_logged_in_no_email);
            }

            findViewById(R.id.viewChangeGroupItems).setVisibility(View.VISIBLE);
            findViewById(R.id.viewScheduleDemo).setVisibility(View.VISIBLE);

        } else {
            Log.d(TAG, "onAuthStateChanged:signed_out");

            findViewById(R.id.viewChangeGroupItems).setVisibility(View.GONE);
            findViewById(R.id.viewScheduleDemo).setVisibility(View.GONE);

            login_button.setText(R.string.button_login_logout_logged_out);
        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            updateUserEnvironment();
        }
    }


    /** Called when the user taps the LoginLogout button */
    public void loginPage(View view) {
        Intent intent = new Intent(this, LoginLogout.class);
        startActivityForResult(intent, 1);
        updateUserEnvironment();
    }

    /** Called when the user taps the GroupItems button */
    public void groupItemsPage(View view) {
//        Intent intent = new Intent(this, GroupItems.class);
        Intent intent = new Intent(this, ListItem.class);
        startActivity(intent);
    }

    /** Called when the user taps the GroupItems button */
    public void scheduleItemPage(View view) {
        Intent intent = new Intent(this, ScheduleItem.class);
        startActivity(intent);
    }

}
