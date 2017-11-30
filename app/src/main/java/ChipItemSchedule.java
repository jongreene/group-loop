import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bishe on 11/30/2017.
 */

public class ChipItemSchedule {
    private String name;
    private String creatorName;
    private String creatorID;
    private String itemID;
    //Map <Time, userid>
    private Map<String, String> schedule;

    public ChipItemSchedule(){
    }

    public ChipItemSchedule(String w){
        this.name = "w";
        this.creatorName = "bish";
        this.creatorID = "12345";
        this.itemID = w;
        schedule = new HashMap<>();
        schedule.put("1AM", "randomAssUser");
        schedule.put("3AM", "anotherRandomUser");
    }
}
