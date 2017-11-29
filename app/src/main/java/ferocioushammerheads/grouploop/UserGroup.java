package ferocioushammerheads.grouploop;

import java.util.ArrayList;
import java.util.List;

public class UserGroup {
    private String creator;
    private String userId;
    private String groupId;
    private ArrayList<String> users;
    private ArrayList<ChipItems> chipItems;

//    TODO: constructor should require at minimum
//    String creator, String groupId
    public UserGroup(){

    }
//    Setters

    public void setUserId(String userId){ this.userId = userId; }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public void setChipItems(ArrayList<ChipItems> chipItems) {
        this.chipItems = chipItems;
    }

//    Getters

    public String getUserId() { return(userId); }

    public String getCreator(){
       return(creator);
    }

    public String getGroupId(){
        return(groupId);
    }

    public List<String> getUsers(){
        return(users);
    }

    public List<ChipItems> getChipItems(){
        return(chipItems);
    }
}
