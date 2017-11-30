package ferocioushammerheads.grouploop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bishe on 11/28/2017.
 */

public class ListAdapter extends ArrayAdapter<AdapterChipItem> {

    public ListAdapter(Context context, ArrayList<AdapterChipItem> ChipItems){
        super(context, 0, ChipItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        AdapterChipItem chipitem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        }
        // Lookup view for data population
        TextView firstText = (TextView) convertView.findViewById(android.R.id.text1);
        TextView secondText = (TextView) convertView.findViewById(android.R.id.text2);
        // Populate the data into the template view using the data object
//        chipitem.setText(chipitemname);
        firstText.setText(chipitem.getName());
        secondText.setText(chipitem.getID());
        // Return the completed view to render on screen
        return convertView;
    }


}
