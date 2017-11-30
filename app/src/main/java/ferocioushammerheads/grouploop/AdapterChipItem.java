package ferocioushammerheads.grouploop;

/**
 * Created by bishe on 11/29/2017.
 */

public class AdapterChipItem {
    private String name;
    private String ID;
    private String creatorName;
    private Boolean isTextList;

    public AdapterChipItem(){

    }

    public AdapterChipItem(String name, String ID, String creatorName, Boolean isTextList){
        this.name = name;
        this.ID = ID;
        this.creatorName = creatorName;
        this.isTextList = isTextList;
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

    public Boolean getIsTextList(){
        return isTextList;
    }


}
