package ferocioushammerheads.grouploop;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Created by uberslam on 11/30/17.
 */
public class UserGroupTest {
    @Test
    public void addMember() throws Exception {
        UserGroup tester = new UserGroup("testName","GroupTest");
        tester.addMember("addedMember");
        if(!tester.getMembers().contains("addedMember")){
            Exception inAddMember = new Exception("ERROR -- UserGroupTest -- in addMember(). Adding person to group FAILED.");
            throw inAddMember;
        }
    }

    @Test
    public void addChipItem() throws Exception {
        UserGroup tester = new UserGroup("testName","GroupTest");
        ChipItemTextList testList = new ChipItemTextList("testList","testName");
        tester.addChipItem(testList);
        if(!tester.getChipItems().contains(testList)) {
            Exception inAddChipItem = new Exception("ERROR -- UserGroupTest -- in addChipItem(). Adding chip to group FAILED.");
            throw inAddChipItem;
        }
    }

    @Test
    public void removeMember() throws Exception {
        UserGroup tester = new UserGroup("testName","GroupTest");
        tester.addMember("addedMember");
        tester.removeMember(1);
        if(tester.getMembers().contains("addedMember")){
            Exception inRemoveMember = new Exception("ERROR -- UserGroupTest -- in removeMember(). Removing person from group FAILED.");
            throw inRemoveMember;
        }
    }

   // @Test
   // public void setUserId() throws Exception {
    //}

    @Test
    public void setCreator() throws Exception {
        UserGroup tester = new UserGroup("testName","GroupTest");
        tester.setCreator("NewCreator");
        assertEquals("NewCreator", tester.getCreator());
    }

    @Test
    public void setGroupId() throws Exception {
        UserGroup tester = new UserGroup("testName","GroupTest");
        tester.setGroupId("NewID");
        assertEquals("NewID", tester.getGroupId());
    }

    @Test
    public void setMembers() throws Exception {
        UserGroup tester = new UserGroup("testName","GroupTest");
        ArrayList<String> members = new ArrayList<String>();
        members.add(0, "Test1");
        members.add(1, "Test2");
        tester.setMembers(members);
        if(!tester.getMembers().contains("Test1")||!tester.getMembers().contains("Test2")){
            Exception inSetMembers = new Exception("ERROR -- UserGroupTest -- in setMembers(). Adding people to group FAILED.");
            throw inSetMembers;
        }
    }

    @Test
    public void setChipItems() throws Exception {
        UserGroup tester = new UserGroup("testName","GroupTest");
        ChipItemTextList testList1 = new ChipItemTextList("testList1","testName");
        ChipItemTextList testList2 = new ChipItemTextList("testList2","testName");
        ChipItemTextList testList3 = new ChipItemTextList("testList3","testName");
        ArrayList<ChipItemTextList> chipItems = new ArrayList<ChipItemTextList>();
        chipItems.add(0,testList1);
        chipItems.add(1,testList2);
        chipItems.add(2,testList3);
        tester.setChipItems(chipItems);
        if(!tester.getChipItems().contains(testList1)||!tester.getChipItems().contains(testList2)||!tester.getChipItems().contains(testList3)) {
            Exception inSetChipItems = new Exception("ERROR -- UserGroupTest -- in setChipItems(). Adding chip items to group FAILED.");
            throw inSetChipItems;
        }
    }

    //@Test
    //public void getUserId() throws Exception {
    //}

    @Test
    public void getCreator() throws Exception {
        UserGroup tester = new UserGroup("testName","GroupTest");
        assertEquals("testName", tester.getCreator());
    }

    @Test
    public void getGroupId() throws Exception {
        UserGroup tester = new UserGroup("testName","GroupTest");
        assertEquals("GroupTest", tester.getGroupId());
    }

    @Test
    public void getMembers() throws Exception {
        UserGroup tester = new UserGroup("testName","GroupTest");
        ArrayList<String> members = new ArrayList<String>();
        members.add(0, "Test1");
        members.add(1, "Test2");
        tester.setMembers(members);
        if(!tester.getMembers().contains("Test1")||!tester.getMembers().contains("Test2")){
            Exception inGetMembers = new Exception("ERROR -- UserGroupTest -- in getMembers(). Getting members from a group FAILED.");
            throw inGetMembers;
        }
    }

    @Test
    public void getChipItems() throws Exception {
        UserGroup tester = new UserGroup("testName","GroupTest");
        ChipItemTextList testList1 = new ChipItemTextList("testList1","testName");
        ChipItemTextList testList2 = new ChipItemTextList("testList2","testName");
        ChipItemTextList testList3 = new ChipItemTextList("testList3","testName");
        ArrayList<ChipItemTextList> chipItems = new ArrayList<ChipItemTextList>();
        chipItems.add(0,testList1);
        chipItems.add(1,testList2);
        chipItems.add(2,testList3);
        tester.setChipItems(chipItems);
        if(!tester.getChipItems().contains(testList1)||!tester.getChipItems().contains(testList2)||!tester.getChipItems().contains(testList3)) {
            Exception inGetChipItems = new Exception("ERROR -- UserGroupTest -- in getChipItems(). Getting chip items from group FAILED.");
            throw inGetChipItems;
        }
    }

}