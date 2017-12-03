package ferocioushammerheads.grouploop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bishe on 11/26/2017.
 */

public class ChipItemTextList {
    private String name;
    private String creatorID;
    private ArrayList<String> items;


    public ChipItemTextList(){
    }

    public ChipItemTextList(String itemname, String creatorID){
        this.name = itemname;
        this.creatorID = creatorID;
        items = new ArrayList<String>();
    }



    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }



    public String getCreatorID(){
        return creatorID;
    }

    public void setCreatorID(String ID){
        this.creatorID = ID;
    }

    public void setItems(ArrayList<String> itemlist){
        items = itemlist;
    }
    public List<String> getItems(){
        return nullArrayListCheck(items);
    }

    public void addItem(String item){
        items = nullArrayListCheck(items);
        items.add(item);
    }

    public void removeItemAt(int index){
        items = nullArrayListCheck(items);
        items.remove(index);
    }

    //    used to initialize any ArrayList that isn't already
    private ArrayList nullArrayListCheck(ArrayList arrayIn){
        if(arrayIn == null){
            arrayIn = new ArrayList();
        }
        return arrayIn;
    }

}
