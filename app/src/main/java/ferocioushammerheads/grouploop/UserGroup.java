package ferocioushammerheads.grouploop;

import java.util.ArrayList;
import java.util.List;

public class UserGroup {
    private String creator;
    private String userId;
    private String groupId;
    private ArrayList<String> members;
//    private ArrayList<ChipItems> textListChipItems;
    private ArrayList<ChipItemTextList> textListChipItems;

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
        textListChipItems = nullArrayListCheck(textListChipItems);

//        ChipItemTextList tmp = new ChipItemTextList("poop");
        textListChipItems.add(chipItem_textList);
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

    public void setTextListChipItems(ArrayList<ChipItemTextList> textListChipItems) {

        this.textListChipItems = nullArrayListCheck(textListChipItems);
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

    public ArrayList<ChipItemTextList> getTextListChipItems(){
        return(nullArrayListCheck(textListChipItems));
    }

    //    used to initialize any ArrayList that isn't already
    private ArrayList nullArrayListCheck(ArrayList arrayIn){
        if(arrayIn == null){
            arrayIn = new ArrayList();
        }
        return arrayIn;
    }


}
