package lv.javaguru.java2.domain;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.*;

@Component
public class Roster extends Generic {

// TODO Do not autowire?
    @Autowired
    private UserDAO userDAO;

    private Date from;
    private Date till;

    private Map<User, RosterUserShiftMap> shiftMap = new HashMap<User, RosterUserShiftMap>();

    public Roster() {
    }

    public Roster(Date from, Date till) {
        this.from = from;
        this.till = till;
        getUsers();
    }

    private void getUsers() {
        try {
            // TODO userDAO.getAll() returns NULL
            for (User user : userDAO.getAll())
                shiftMap.put(user, new RosterUserShiftMap(user));
        } catch (NullPointerException e) {
            return;
        } catch (DBException e) {
            e.printStackTrace();
        }
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
