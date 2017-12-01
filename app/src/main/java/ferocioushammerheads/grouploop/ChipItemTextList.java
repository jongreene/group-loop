package ferocioushammerheads.grouploop;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bishe on 11/26/2017.
 */

public class ChipItemTextList {
    private String name;
    private String creatorName;
    private String creatorID;
    private String itemID;
    private ArrayList<String> items;


    public ChipItemTextList(){
    }

    public ChipItemTextList(String w){
        this.name = w;
        this.creatorName = "bish";
        this.creatorID = "12345";
        this.itemID = w;
        items = new ArrayList<String>();
        items.add("bananas");
        items.add("apples");
    }



    public String getName(){
        return name;
    }

    public String getCreatorName(){
        return creatorName;
    }

    public String getCreatorID(){
        return creatorID;
    }

    public String getItemID(){
        return itemID;
    }

    public List<String> getItems(){
        return nullArrayListCheck(items);
    }

    //    used to initialize any ArrayList that isn't already
    private ArrayList nullArrayListCheck(ArrayList arrayIn){
        if(arrayIn == null){
            arrayIn = new ArrayList();
        }
        return arrayIn;
    }

    public void addItem(String item){
        items.add(item);
    }

    public void removeItemAt(int index){
        items.remove(index);
    }

}
