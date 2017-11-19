package ferocioushammerheads.grouploop;

import java.util.ArrayList;
import java.util.List;

public class UserProfile {

    private String userId;
    private String username;
    private String email;
    private List<String> groupList;


    public UserProfile() {
        // Default constructor required for calls to DataSnapshot.getValue(UserProfile.class)
    }

    public UserProfile(String userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;

        groupList = new ArrayList<String>();
        groupList.add("test1");
        groupList.add("test2");
    }

    public UserProfile(UserProfile c) {
        userId = c.userId;
        username = c.username;
        email = c.email;

        groupList = new ArrayList<String>();
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

    public List<String> getGroupList(){
        return groupList;
    }


    public boolean addGroup(String newGroup){
        groupList.add(newGroup);

//        TODO: check if already in group
        return true;
    }
//
//    public String deleteGroup(){
//
//        return null;
//    }
//
//    public ArrayList<String> getGroups(){
//
//        return null;
//    }

}