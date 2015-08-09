package lv.javaguru.java2.domain;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class RosterMap extends Generic {

    Date from;
    Date till;

    Map<User, RosterUserShiftMap> shiftMap = new HashMap<User, RosterUserShiftMap>();

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

    public RosterUserShiftMap getUserMap(User user) {
        return shiftMap.get(user);
    }

    public void setUserMap(User user, RosterUserShiftMap shiftMap) {
        this.shiftMap.put(user, shiftMap);
    }

}
