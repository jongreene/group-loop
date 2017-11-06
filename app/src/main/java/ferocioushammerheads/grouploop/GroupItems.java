/**
 * GroupItems is currently a placeholder.
 * It will show a list of text lists and
 * schedules for the chosen group. When
 * the user clicks on one it goes to
 * listItem or ScheduleItem
 *
 * @author  Ferocious Hammerheads
 * @version Beta 1.0
 * @since   2017-11-5
 */
package ferocioushammerheads.grouploop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GroupItems extends AppCompatActivity {
    private Button mSendData;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSendData = (Button) findViewById(R.id.sendButton);
        mSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                myRef.child("message").setValue("Hello, World!");
            }
        });




    }
}
