package lv.javaguru.java2.domain;


import java.util.*;

public class UserTypes {

    private Map<String, String> userTypes = new HashMap<String, String>();

    public UserTypes() {
        this.userTypes.put("A", "Administrator");
        this.userTypes.put("U", "User");
    }

    public String getName(String key) {
        return this.userTypes.get(key);
    }

    public Set<String> getKeys() {
        return this.userTypes.keySet();
    }

}
