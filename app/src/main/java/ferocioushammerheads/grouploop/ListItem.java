/**
 * ListItem is the activity responsible for displaying the list of
 * objects that are present in the group.
 * This activity also handles the addition of objects to the group
 * The objects can also be clicked to see more info (feature removed in this version)
 *
 * @author  Ferocious Hammerheads
 * @version Beta 1.0
 * @since   2017-11-5
 */

package ferocioushammerheads.grouploop;

import android.content.Intent;
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
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ListItem extends AppCompatActivity {
    /**
     * Global Variable declarations
     */
    ArrayList<String> listItems=new ArrayList<String>(); //found at source 1
    ArrayAdapter<String> adapter; //found at source 1
    private EditText temp;
    private static final String TAG = "List items";
    private Boolean createdOnce = false;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle); //found at source 1
        setContentView(R.layout.activity_list_item);
        /**
         * Assigning the list view
         * Binding the Adapter
         */
        final ListView listView = findViewById(R.id.List);
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems); //found at source 1
        listView.setAdapter(adapter);

        /**
         * Event listener for children of items (currently only single group functionality works 11/5/2017)
         * Other onChild methods not defined for now, When child is added to the database it gets added to the listview
         */
        FirebaseDatabase.getInstance().getReference("items").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Log.d(TAG, "ListView item clicked." + dataSnapshot.getKey());
                listItems.add(dataSnapshot.getValue().toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        /**
         * sample listener function
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "ListView item clicked.");
                Log.d(TAG, "LIST ID: " + listView.getItemIdAtPosition(position));
            }
        });
    }

    /**
     * display menu when add item button is clicked
     * @param v
     */
    public void addMenu(View v){
        findViewById(R.id.listLayout).setVisibility(View.GONE);
        findViewById(R.id.addItem).setVisibility(View.GONE);
        findViewById(R.id.add_menu_item).setVisibility(View.VISIBLE);
    }

    /**
     * When an item is submitted, it retrieves the text name from the textview
     * Create a reference to the database and push the value
     * The textview was is reset to empty string
     * Return view to original list of of objects
     * @param v
     */
    public void addItems(View v) {
        EditText tempname   = findViewById(R.id.itemEdit);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("items");
        myRef = myRef.push();
        myRef.setValue(tempname.getText().toString());
        String key = myRef.getKey().toString();
        Log.d(TAG, "objectID " +key);
        TextView editText = findViewById(R.id.itemEdit);
        editText.setText("");
        findViewById(R.id.add_menu_item).setVisibility(View.GONE);
        findViewById(R.id.listLayout).setVisibility(View.VISIBLE);
        findViewById(R.id.addItem).setVisibility(View.VISIBLE);
    }
}
