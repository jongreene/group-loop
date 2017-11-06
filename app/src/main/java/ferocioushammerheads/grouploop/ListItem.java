package ferocioushammerheads.grouploop;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.*;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ListItem extends AppCompatActivity {
    ArrayList<String> listItems=new ArrayList<String>(); //found at source 1
    ArrayAdapter<String> adapter; //found at source 1
    private EditText temp;
    private static final String TAG = "List items";

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle); //found at source 1
        setContentView(R.layout.activity_list_item);
        ListView listView = (ListView) findViewById(R.id.List);
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems); //found at source 1
        listView.setAdapter(adapter);
        FirebaseDatabase.getInstance().getReference("items")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            listItems.add(snapshot.getValue().toString());
                            adapter.notifyDataSetChanged();

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "ListView item clicked.");
            }
        });
    }

    public void addMenu(View v){
        findViewById(R.id.listLayout).setVisibility(View.GONE);
        findViewById(R.id.addItem).setVisibility(View.GONE);
        findViewById(R.id.add_menu_item).setVisibility(View.VISIBLE);
    }
    public void addItems(View v) {
        temp   = (EditText)findViewById(R.id.itemEdit);
        listItems.add(temp.getText().toString());

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("items");
        myRef = myRef.push();
        myRef.setValue(temp.getText().toString());
        String key = myRef.getKey().toString();


        SharedPreferences mSharedPreferences = getSharedPreferences("objectID", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        Log.d(TAG, "Unique ID " +key);
        mEditor.putString("key", temp.getText().toString());
        mEditor.apply();

        this.temp.setText("");
        adapter.notifyDataSetChanged();
        findViewById(R.id.add_menu_item).setVisibility(View.GONE);
        findViewById(R.id.listLayout).setVisibility(View.VISIBLE);
        findViewById(R.id.addItem).setVisibility(View.VISIBLE);
    }
}
