package lv.javaguru.java2.domain;

import lv.javaguru.java2.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class Roster extends Generic {

    private Date from;
    private Date till;

    private Map<User, RosterUserShiftMap> shiftMap = new HashMap<User, RosterUserShiftMap>();

    public Roster() {
    }

    public Roster(Date from, Date till) {
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

}
