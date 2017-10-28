package ferocioushammerheads.grouploop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ListItem extends AppCompatActivity {
    //ArrayList<String> listItems=new ArrayList<String>();
    //ArrayAdapter<String> adapter;
    int clickCounter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
    }
   // public void addItems(View v) {
        //listItems.add("Clicked : " + clickCounter++);
       // adapter.notifyDataSetChanged();
   // }
}
