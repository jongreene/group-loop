package ferocioushammerheads.grouploop;

import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by uberslam on 11/30/17.
 */
public class UserProfileTest {

    UserProfile test = new UserProfile("test123", "Test McTest", "testtest@test.com");
    //Exception inTestEx = new Exception("ERROR -- UserProfileTest -- ");
    private static final String TAG = "UserProfileTest";
    boolean testAddGroup1 = test.addNewGroup("testGroup1");
    boolean testAddGroup2 = test.addNewGroup("testGroup2");

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
        assertEquals("test123", test.getUserId());
    }

    @Test
    public void getUsername() throws Exception {
        assertEquals("Test McTest", test.getUsername());
    }

    @Test
    public void getEmail() throws Exception {
        assertEquals("testtest@test.com", test.getEmail());
    }

    @Test
    public void getCurrentGroup() throws Exception {
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
    }

    @Test
    public void setCurrentGroup() throws Exception {
    }

    @Test
    public void addNewGroup() throws Exception {
    }

    @Test
    public void removeGroup() throws Exception {
    }

    @Test
    public void removeGroup1() throws Exception {
    }

}