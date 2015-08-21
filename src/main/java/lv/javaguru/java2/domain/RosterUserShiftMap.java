package lv.javaguru.java2.domain;


import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class RosterUserShiftMap {

    private Map<Date, Shift> userShifts = new HashMap<Date, Shift>();

    public RosterUserShiftMap() {

    }

    public Map<Date, Shift> getUserShifts() {
        return userShifts;
    }

    public void setUserShifts(Map<Date, Shift> userShifts) {
        this.userShifts = userShifts;
    }

    public void setShift(Date date, Shift shift) {
        this.userShifts.put(date, shift);
    }

    public Shift getShift(Date date) {
        return this.userShifts.get(date);
    }
}
