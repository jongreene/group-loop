package ferocioushammerheads.grouploop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Firebase session";

    private Button ChangeGroupItems;
    private Button ChangeLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
        } else {
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    /** Called when the user taps the LoginLogout button */
    public void loginPage(View view) {
        Intent intent = new Intent(this, LoginLogout.class);
        startActivity(intent);
    }

    /** Called when the user taps the GroupItems button */
    public void groupItemsPage(View view) {
        Intent intent = new Intent(this, ScheduleItem.class);
        startActivity(intent);
    }
}
