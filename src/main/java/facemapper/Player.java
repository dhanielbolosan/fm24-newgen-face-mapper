package facemapper;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
/*
 * Player class object with getters and setters
 */
public class Player {
    private final String uid;
    private final String nationality;
    private final String secondNationality;
    private final String name;
    private String folder;
    private Map<String, String> data = new HashMap<>();

    public Player(String uid, String nationality, String secondNationality, String name) {
        this.uid = uid;
        this.nationality = nationality;
        this.secondNationality = secondNationality;
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public String getNationality() {
        return nationality;
    }

    public String getSecondNationality() {
        return secondNationality;
    }

    public String getName() {
        return name;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getFolder() {
        return folder;
    }

    public Map<String, String> getData() {
        return data;
    }
}