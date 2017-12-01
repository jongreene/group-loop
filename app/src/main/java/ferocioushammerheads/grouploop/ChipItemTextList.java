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
//    private String creatorName;
    private String creatorID;
//    private String itemID;
    private List<String> items;


    public ChipItemTextList(){
    }

    public ChipItemTextList(String name, String creatorID){
        this.name = name;
//        this.creatorName = creatorName;
        this.creatorID = creatorID;
//        this.itemID = w;
        items = new ArrayList<String>();
//        items.add("bananas");
//        items.add("apples");
    }



    public String getName(){
        return name;
    }

//    public String getCreatorName(){
//        return creatorName;
//    }

    public String getCreatorID(){
        return creatorID;
    }

//    public String getItemID(){
//        return itemID;
//    }

    public List<String> getItems(){
        return items;
    }

    public void addItem(String item){
        items.add(item);
    }

    public void removeItemAt(int index){
        items.remove(index);
    }

}
