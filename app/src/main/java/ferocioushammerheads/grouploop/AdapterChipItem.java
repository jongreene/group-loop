package ferocioushammerheads.grouploop;

/**
 * Created by bishe on 11/29/2017.
 */

public class AdapterChipItem {
    private String name;
    private String ID;
    private String creatorName="boo";
    private String type;

    public AdapterChipItem(){

    }

    public AdapterChipItem(String name, String ID){
        this.name = name;
        this.ID = ID;
//        this.creatorName = creatorName;
//        this.type = type;
    }

    public String getName(){
        return name;
    }

    public String getID(){
        return ID;
    }

    public String getCreatorName(){
        return creatorName;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }


}
