package ferocioushammerheads.grouploop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bishe on 11/30/2017.
 */

public class ChipItemSchedule {
    private String name;
    private String creatorID;
//    private Map<String, String> schedule = new HashMap<>();
//    private ArrayList<Map<String, String>> schedule = new ArrayList<>();
    private HashMap<String, HashMap<String, String>> schedule = new HashMap<>();

    public ChipItemSchedule(){
    }

    public ChipItemSchedule(String name, String creatorID){
        this.name = name;
        this.creatorID = creatorID;
        HashMap<String, String> tmp1 = new HashMap<>();
        HashMap<String, String> tmp2 = new HashMap<>();

//        tmp1.put("1AM", "dude");
//        tmp1.put("12AM", "guy");
//        tmp1.put("3PM", "della");
//
//        tmp2.put("1AM", "dude");
//        tmp2.put("12AM", "guy");
//        tmp2.put("3PM", "della");
//
//        schedule.put("11032017", tmp1);
//        schedule.put("12012018", tmp2);


    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getCreatorID(){
        return creatorID;
    }

    public void setCreatorID(String creatorID){
        this.creatorID = creatorID;
    }

    public HashMap<String, HashMap<String, String>> getSchedule() {
        return nullMapCheck(schedule);
    }

    public void setSchedule(HashMap<String, HashMap<String, String>> aSchedule){
        schedule = aSchedule;
    }

    private HashMap<String, HashMap<String, String>> nullMapCheck(HashMap<String, HashMap<String, String>> mapIn){
        if(mapIn == null){
            mapIn = new HashMap<>();
        }
        return mapIn;
    }


}
