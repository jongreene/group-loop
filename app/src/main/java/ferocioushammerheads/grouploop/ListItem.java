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
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private EditText temp;
    private static final String TAG = "List items";
    private Boolean createdOnce = false;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_list_item);
        final ListView listView = (ListView) findViewById(R.id.List);
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        listView.setAdapter(adapter);
//        ValueEventListener addItemListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    listItems.add(snapshot.getValue().toString());
//                    adapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//                // ...
//            }
//        };
//        FirebaseDatabase.getInstance().getReference("items").addValueEventListener(addItemListener);
//        FirebaseDatabase.getInstance().getReference("items")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            listItems.add(snapshot.getValue().toString());
//                            adapter.notifyDataSetChanged();
//
//                        }
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                    }
//                });

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



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "ListView item clicked.");
                Log.d(TAG, "LIST ID: " + listView.getItemIdAtPosition(position));
                int itemID = (int) listView.getItemIdAtPosition(position);
//                SharedPreferences mSharedPreferences = getSharedPreferences("objectID", MODE_PRIVATE);
//                String uniqueID = mSharedPreferences.getString();
//                Intent intent = new Intent(ListItem.this, SampleObjectView.class);
//                intent.putExtra("UniqueID");
            }
        });
    }

    public void addMenu(View v){
        findViewById(R.id.listLayout).setVisibility(View.GONE);
        findViewById(R.id.addItem).setVisibility(View.GONE);
        findViewById(R.id.add_menu_item).setVisibility(View.VISIBLE);
    }
    public void addItems(View v) {
        EditText tempname   = (EditText)findViewById(R.id.itemEdit);
//        listItems.add(temp.getText().toString());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("items");
        myRef = myRef.push();
        myRef.setValue(tempname.getText().toString());
        String key = myRef.getKey().toString();


//        SharedPreferences mSharedPreferences = getSharedPreferences("objectID", MODE_PRIVATE);
//        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        Log.d(TAG, "objectID " +key);
//        int itemID = (int) listView.getItemIdAtPosition(position);
//        mEditor.putString("objectID", temp.getText().toString());
//        mEditor.apply();

        TextView editText = (TextView)findViewById(R.id.itemEdit);
        editText.setText("");
//        adapter.notifyDataSetChanged();
        findViewById(R.id.add_menu_item).setVisibility(View.GONE);
        findViewById(R.id.listLayout).setVisibility(View.VISIBLE);
        findViewById(R.id.addItem).setVisibility(View.VISIBLE);
    }
}
