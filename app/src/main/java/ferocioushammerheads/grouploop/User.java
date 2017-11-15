package ferocioushammerheads.grouploop;

import java.util.ArrayList;

public class User {

    private String username;
    private String email;
    private ArrayList<String> groups;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }



    public void addGroup(){


    }

    public String deleteGroup(){

        return null;
    }

    public ArrayList<String> getGroups(){

        return null;
    }

}