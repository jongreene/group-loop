package ferocioushammerheads.grouploop;

import java.util.ArrayList;
import java.util.List;

public class UserProfile {

    private String userId;
    private String username;
    private String email;
    private String currentGroup;
    private List<String> groupList;


    public UserProfile() {
        // Default constructor required for calls to DataSnapshot.getValue(UserProfile.class)
    }

    public UserProfile(String userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.currentGroup = "default";

        ArrayList<String> test;
        groupList = new ArrayList<String>();
        groupList.add("Party house");
        groupList.add("Moms");
    }

    public UserProfile(UserProfile c) {
        userId = c.userId;
        username = c.username;
        email = c.email;
        currentGroup = c.currentGroup;

        groupList = c.groupList;
    }

    public String getUserId(){
        return userId;
    }

    public String getUsername(){
        return username;
    }

    public String getEmail(){
        return email;
    }

    public String getCurrentGroup(){
        return currentGroup;
    }

    public List<String> getGroupList(){
        return groupList;
    }

    public Boolean setCurrentGroup(int index) {
        if(groupList.size() >= index && index >= 0){
            currentGroup = groupList.get(index);
            return true;
        }
        return false;
    }

}