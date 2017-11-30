package ferocioushammerheads.grouploop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserGroup {
    private String creator;
    private String userId;
    private String groupId;
    private ArrayList<String> members;
//    private ArrayList<ChipItems> chipItems;
    private ArrayList<ChipItem_TextList> chipItems;

    public UserGroup(){}

//    String creator, String groupId
    public UserGroup(String creator, String groupId){
        this.creator = creator;
        this.groupId = groupId;

//        add creator to the list of users
        members = new ArrayList<String>();
        members.add(creator);

        ChipItem_TextList tmp = new ChipItem_TextList("testtest");

        chipItems = new ArrayList<ChipItem_TextList>();
        chipItems.add(tmp);

        addChipItem(tmp);

    }

    public void addMember(String member){
        members.add(member);
    }

    public void addChipItem(ChipItem_TextList chipItem_textList){
        ChipItem_TextList tmp = new ChipItem_TextList("poop");
        chipItems.add(tmp);
    }

    public boolean removeMember(int index){
        if (members.size() > index && index >= 0) {
            members.remove(index);
            return true;
        }
        return false;
    }

//    Setters

    public void setUserId(String userId){ this.userId = userId; }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setMembers(ArrayList<String> users) {
        this.members = users;
    }

    public void setChipItems(ArrayList<ChipItem_TextList> chipItems) {
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

    public List<String> getMembers(){
        return(members);
    }

    public List<ChipItem_TextList> getChipItems(){
        return(chipItems);
    }
}
