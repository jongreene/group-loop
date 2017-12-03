package ferocioushammerheads.grouploop;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to create the users profile.
 *
 * The object is serialized and written to firebase and constructed vice versa.
 */
public class UserProfile {

    private String userId;
    private String username;
    private String email;
    private String currentGroup;
    private ArrayList<String> groupList;


    public UserProfile() {
        // Default constructor required for calls to DataSnapshot.getValue(UserProfile.class)
    }

    public UserProfile(String userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    public UserProfile(UserProfile c) {
        userId = c.userId;
        username = c.username;
        email = c.email;
        currentGroup = c.currentGroup;

        groupList = c.groupList;
        groupList = nullArrayListCheck(groupList);
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getCurrentGroup() { return currentGroup; }

    public ArrayList<String> getGroupList() {
        return nullArrayListCheck(groupList);
    }

    public String getGroupListString() {
        String groupListString = new String();
        int count = 1;
        for(String group : groupList){
            groupListString += group;
            if(count < groupList.size()){
                groupListString += ", ";
            }
            count++;
        }
        return groupListString;
    }

    public Boolean setCurrentGroup(int index) {
        groupList = nullArrayListCheck(groupList);
        if (groupList.size() >= index && index >= 0) {
            currentGroup = groupList.get(index);
            return true;
        }
        return false;
    }

    public boolean addNewGroup(String newGroup){
        groupList = nullArrayListCheck(groupList);
        if(newGroup.length()>0) {
            groupList.add(newGroup);
            setCurrentGroup(groupList.indexOf(newGroup));
            return true;
        }
        return false;
    }

    public boolean removeGroup(int index){
        groupList = nullArrayListCheck(groupList);
        String selectedGroup = groupList.get(index);

        if (index >= 0) {
            groupList.remove(index);
            if(currentGroup.equals(selectedGroup)){
                setCurrentGroup(0);
            }
            return true;
        }
        return false;
    }

    public boolean removeGroup(String groupName){
        groupList = nullArrayListCheck(groupList);
        int index = groupList.indexOf(groupName);

        if(groupList.contains(groupName)){
            groupList.remove(index);
            if(currentGroup.equals(groupName)){
                setCurrentGroup(0);
            }
            return true;
        }
        return false;
    }

    //    used to initialize any ArrayList that isn't already
    private ArrayList nullArrayListCheck(ArrayList arrayIn){
        if(arrayIn == null){
            arrayIn = new ArrayList();
        }
        return arrayIn;
    }

}