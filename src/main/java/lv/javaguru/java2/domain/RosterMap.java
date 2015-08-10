package lv.javaguru.java2.domain;

import java.sql.Date;
import java.util.*;

public class RosterMap extends Generic {

    private Date from;
    private Date till;

    private final Map<User, RosterUserShiftMap> shiftMap = new HashMap<User, RosterUserShiftMap>();

    public RosterMap(Date from, Date till) {
        this.from = from;
        this.till = till;
    }

    public Date getTill() {
        return till;
    }

    public void setTill(Date till) {
        this.till = till;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public RosterUserShiftMap getUserShifts(User user) {
        return shiftMap.get(user);
    }

    public void setUserMap(User user, RosterUserShiftMap shiftMap) {
        this.shiftMap.put(user, shiftMap);
    }

    public Set<User> getUserList() {
        return shiftMap.keySet();
    }

}
