package ferocioushammerheads.grouploop;

import java.util.ArrayList;
import java.util.List;

public class UserGroup {
    private String creator;
    private String userId;
    private String groupId;
    private ArrayList<String> members;
//    private ArrayList<ChipItems> chipItems;
    private ArrayList<ChipItemTextList> chipItems;

    public UserGroup(){}

//    String creator, String groupId
    public UserGroup(String creator, String groupId){
        this.creator = creator;
        this.groupId = groupId;

//        add creator to the list of users
        members = new ArrayList<String>();
        members.add(creator);
    }

    public void addMember(String member){
        members = nullArrayListCheck(members);
        members.add(member);
    }

    public void addChipItem(ChipItemTextList chipItem_textList){
        chipItems = nullArrayListCheck(chipItems);

        ChipItemTextList tmp = new ChipItemTextList("poop");
        chipItems.add(tmp);
    }

    public boolean removeMember(int index){
        members = nullArrayListCheck(members);
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
        members = nullArrayListCheck(members);

        this.members = users;
    }

    public void setChipItems(ArrayList<ChipItemTextList> chipItems) {

        this.chipItems = nullArrayListCheck(chipItems);
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
        return(nullArrayListCheck(members));
    }

    public ArrayList<ChipItemTextList> getChipItems(){
        return(nullArrayListCheck(chipItems));
    }

    //    used to initialize any ArrayList that isn't already
    private ArrayList nullArrayListCheck(ArrayList arrayIn){
        if(arrayIn == null){
            arrayIn = new ArrayList();
        }
        return arrayIn;
    }


}
