package ferocioushammerheads.grouploop;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bishe on 11/26/2017.
 */

public class ChipItem_TextList {
    private String name;
    private String creatorName;
    private String creatorID;
    private String itemID;
    private List<String> items;


    public ChipItem_TextList(){
    }

    public ChipItem_TextList(String w){
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
        return items;
    }

}
