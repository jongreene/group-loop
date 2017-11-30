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
        AdapterChipItem chipitem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        }
        TextView firstText = (TextView) convertView.findViewById(android.R.id.text1);
        TextView secondText = (TextView) convertView.findViewById(android.R.id.text2);
        firstText.setText(chipitem.getName());
        secondText.setText(chipitem.getType());
        // Return the completed view to render on screen
        return convertView;
    }


}
