package lv.javaguru.java2.domain;


import lv.javaguru.java2.domain.Shift;
import lv.javaguru.java2.domain.User;

import java.util.HashMap;
import java.util.Map;

public class RosterUserShiftMap {

    User user;
    Map<Integer, Shift> shiftMap = new HashMap<Integer, Shift>();

    public RosterUserShiftMap(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<Integer, Shift> getShiftMap() {
        return shiftMap;
    }

    public void setShiftMap(Map<Integer, Shift> shiftMap) {
        this.shiftMap = shiftMap;
    }

    public void setShift(int day, Shift shift) {
        this.shiftMap.put(day, shift);
    }

    public Shift getShift(int day) {
        return this.shiftMap.get(day);
    }
}
