package ferocioushammerheads.grouploop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button ChangeGroupItems;
    private Button ChangeLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        Intent intent = new Intent(this, GroupItems.class);
        startActivity(intent);
    }
}
