package ferocioushammerheads.grouploop;

import java.util.List;

public class UserGroup {
    private String m_name;
    private String m_ID;
    private List<String> m_users;
    private List<ChipItems> m_chipItems;

    public void setName(String name) {
        m_name = name;
    }
    public void setID(String ID) {
        m_ID = ID;
    }
    public void setUsers(List<String> users) {
        m_users = users;
    }
    public void setChipItems(List<ChipItems> chipItems) {
        m_chipItems = chipItems;
    }
    public String getName(){
       return(m_name);
    }
    public String getID(){
        return(m_ID);
    }
    public List<String> getUsers(){
        return(m_users);
    }
    public List<ChipItems> getChipItems(){
        return(m_chipItems);
    }
}
