package ferocioushammerheads.grouploop;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by uberslam on 11/30/17.
 */
public class UserProfileTest {

    //UserProfile test = new UserProfile("test123", "Test McTest", "testtest@test.com");
    //Exception inTestEx = new Exception("ERROR -- UserProfileTest -- ");
    //private static final String TAG = "UserProfileTest";
    //boolean testAddGroup1 = test.addNewGroup("testGroup1");
    //boolean testAddGroup2 = test.addNewGroup("testGroup2");

    @Test
    public void getUserId() throws Exception {
        /*try {
            String testUP1 = test.getUserId();
            if(!testUP1.equals("test123")) {
                throw inTestEx;
            }
        }
        catch(Exception e) {
            Log.e(TAG, e.getMessage()+"in getUserId()");
        }*/
        UserProfile test = new UserProfile("test123", "Test McTest", "testtest@test.com");
        assertEquals("test123", test.getUserId());
    }

    @Test
    public void getUsername() throws Exception {
        UserProfile test = new UserProfile("test123", "Test McTest", "testtest@test.com");
        assertEquals("Test McTest", test.getUsername());
    }

    @Test
    public void getEmail() throws Exception {
        UserProfile test = new UserProfile("test123", "Test McTest", "testtest@test.com");
        assertEquals("testtest@test.com", test.getEmail());
    }

    @Test
    public void getCurrentGroup() throws Exception {
        UserProfile test = new UserProfile("test123", "Test McTest", "testtest@test.com");
        boolean testAddGroup1 = test.addNewGroup("testGroup1");
        boolean testAddGroup2 = test.addNewGroup("testGroup2");
        String additionalNote = "";
        if(testAddGroup1 == false) {
            additionalNote+="Adding Group 1 failed. ";
        }
        if(testAddGroup2 == false) {
            additionalNote+="Adding Group 2 failed. ";
        }
        if(!test.getCurrentGroup().equals("testGroup2")) {
            Exception inTestEx = new Exception("ERROR -- UserProfileTest -- in getCurrentGroup(). Current Group is not group with latest action. "+additionalNote);
            throw inTestEx;
        }

    }

    @Test
    public void getGroupList() throws Exception {
        UserProfile test = new UserProfile("test123", "Test McTest", "testtest@test.com");
        boolean testAddGroup1 = test.addNewGroup("testGroup1");
        boolean testAddGroup2 = test.addNewGroup("testGroup2");
        ArrayList<String> expected = new ArrayList<>();
        expected.add("testGroup1");
        expected.add("testGroup2");
        assertEquals(expected, test.getGroupList());
    }

    @Test
    public void setCurrentGroup() throws Exception {
        UserProfile test = new UserProfile("test123", "Test McTest", "testtest@test.com");
        boolean testAddGroup1 = test.addNewGroup("testGroup1");
        boolean testAddGroup2 = test.addNewGroup("testGroup2");
        String msg = "";
        boolean throwError = false;
        if(!testAddGroup1) {
            msg += "Error adding group 1. ";
            throwError = true;
        }
        else {
            msg += "Group 1 added correctly. ";
        }
        if(!testAddGroup2) {
            msg += "Error adding group 2. ";
            throwError = true;
        }
        else {
            msg += "Group 2 added correctly. ";
        }
        for(int i = 0; i<2; i++) {
            if(test.setCurrentGroup(i)) {
                if(!test.getCurrentGroup().equals("testGroup"+(i+1))) {
                    msg += "Returns true but does not return the correct group. ";
                    throwError = true;
                }
            }
            else {
                if(test.getCurrentGroup().equals("testGroup"+(i+1))) {
                    throwError = true;
                    msg += "Returns false but returns the correct group. ";
                }
                else {
                    msg += "Correctly returns false. ";
                }
            }
        }
        if(throwError) {
            Exception inTestEx = new Exception(msg);
            throw inTestEx;
        }
    }

    @Test
    public void addNewGroup() throws Exception {
        UserProfile test = new UserProfile("test123", "Test McTest", "testtest@test.com");
        boolean testAddGroup1 = test.addNewGroup("testGroup1");
        boolean testAddGroup2 = test.addNewGroup("testGroup2");
        if(!(testAddGroup1 && testAddGroup2)) {
            Exception inTestEx = new Exception("Error adding groups");
            throw inTestEx;
        }
    }

    @Test
    public void removeGroup() throws Exception {
        UserProfile test = new UserProfile("test123", "Test McTest", "testtest@test.com");
        boolean testAddGroup1 = test.addNewGroup("testGroup1");
        boolean testAddGroup2 = test.addNewGroup("testGroup2");
    }

    @Test
    public void removeGroup1() throws Exception {
        UserProfile test = new UserProfile("test123", "Test McTest", "testtest@test.com");
        boolean testAddGroup1 = test.addNewGroup("testGroup1");
        boolean testAddGroup2 = test.addNewGroup("testGroup2");
    }


}