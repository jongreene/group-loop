package ferocioushammerheads.grouploop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.*;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ListItem extends AppCompatActivity {
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private EditText temp;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_list_item);
        ListView listView = (ListView) findViewById(R.id.List);
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        listView.setAdapter(adapter);
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
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        temp.setText("");
        adapter.notifyDataSetChanged();
        findViewById(R.id.add_menu_item).setVisibility(View.GONE);
        findViewById(R.id.listLayout).setVisibility(View.VISIBLE);
        findViewById(R.id.addItem).setVisibility(View.VISIBLE);
    }
}
