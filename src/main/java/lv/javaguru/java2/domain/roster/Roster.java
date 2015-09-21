package lv.javaguru.java2.domain.roster;

import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.shift.Shift;
import lv.javaguru.java2.domain.user.User;

import java.sql.Date;
import java.util.*;

public class Roster extends Generic {

    private Date from;
    private Date till;

    private Map<User, RosterUserShiftMap> shiftMap;

    public Roster() {
    }

    public Roster(Date from, Date till) {
        this.from = from;
        this.till = till;
        shiftMap = new LinkedHashMap<User, RosterUserShiftMap>();
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

    public RosterUserShiftMap getUserShifts(long userId) throws Exception {
        for (User user : getUserList())
            if (userId == user.getId())
                return shiftMap.get(user);
        throw new Exception("User not found");
    }

    public void setUserMap(User user, RosterUserShiftMap shiftMap) {
        this.shiftMap.put(user, shiftMap);
    }

    public Set<User> getUserList() {
        return shiftMap.keySet();
    }

    public int getStaffInShift(Date date, Shift shift) {
        int result = 0;
        for (User user : getUserList())
            if (getUserShifts(user) != null
                    && getUserShifts(user).getShift(date) != null
                    && shift.getId() == getUserShifts(user).getShift(date).getId())
                result++;
        return result;
    }

}
