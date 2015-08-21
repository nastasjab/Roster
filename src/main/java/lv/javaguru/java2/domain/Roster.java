package lv.javaguru.java2.domain;

import lv.javaguru.java2.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Component
public class Roster extends Generic {

// TODO Do not autowire! Why?
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserPatternDAO userPatternDAO;

    @Autowired
    private ShiftDAO shiftDAO;

    @Autowired
    private PatternShiftDAO patternShiftDAO;

    private Date from;
    private Date till;

    private Map<User, RosterUserShiftMap> shiftMap = new HashMap<User, RosterUserShiftMap>();

    public Roster() {
    }

    public Roster(Date from, Date till) {
        this.from = from;
        this.till = till;
        if (userDAO == null)
            System.out.println("userDAO = null");
        else
            System.out.println("userDAO" + userDAO.toString());
        if (userPatternDAO == null)
            System.out.println("userPatternDAO = null");
        else
            System.out.println("userPatternDAO" + userPatternDAO.toString());
        // fillUsers();
        // fillShifts();
    }

    private void fillUsers() {
        try {
            // TODO userDAO.getAll() throws NullPointerException before query!
            for (User user : userDAO.getAll())
                shiftMap.put(user, new RosterUserShiftMap());
        } catch (NullPointerException e) {
            return;
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    private void fillShifts() {

        try {

            for (UserPattern userPattern : getUserPatternsByDatesFromTill()) {

                long epochDayFrom = getEpochDayFrom(userPattern);

                long patternOffset = epochDayFrom - toEpochDay(userPattern.getStartDay()) + (long) userPattern.getPatternStartDay() - 1;

                long epochDayTill = getEpochDayTill(userPattern);

                List<Shift> shiftInPattern = new ArrayList<Shift>();

                getShiftsFromPattern(userPattern, shiftInPattern);

                int patternSize = shiftInPattern.size();

                int seqNo = (int) (patternOffset % (long)patternSize);

                for (long day = epochDayFrom; day <= epochDayTill; day++) {

                    setShift(userPattern, shiftInPattern.get(seqNo), day);

                    if (seqNo >= patternSize)
                        seqNo = 0;
                    else
                        seqNo++;
                }
            }

        } catch (DBException e) {
            e.printStackTrace();
        }

    }

    private void setShift(UserPattern userPattern, Shift shift, long day) {
        shiftMap.get(userPattern.getUserId()).setShift(toSqlDate(day), shift);
    }

    private void getShiftsFromPattern(UserPattern userPattern, List<Shift> shiftInPattern) throws DBException {
        for (PatternShift patternShift : patternShiftDAO.getAll(userPattern.getPattern().getId())) {
            shiftInPattern.add(patternShift.getShift());
        }
    }

    private Date toSqlDate(long day) {
        return Date.valueOf(LocalDate.ofEpochDay(day));
    }

    private long getEpochDayTill(UserPattern userPattern) {
        long epochDayTill;
        if (toEpochDay(userPattern.getEndDay()) > toEpochDay(getTill()))
            epochDayTill = toEpochDay(getTill());
        else
            epochDayTill = toEpochDay(userPattern.getEndDay());
        return epochDayTill;
    }

    private long getEpochDayFrom(UserPattern userPattern) {
        long epochDayFrom;
        if (toEpochDay(userPattern.getStartDay()) < toEpochDay(getFrom()))
            epochDayFrom = toEpochDay(getFrom());
        else
            epochDayFrom = toEpochDay(userPattern.getStartDay());
        return epochDayFrom;
    }

    private List<UserPattern> getUserPatternsByDatesFromTill() throws DBException {
        // TODO Throws NullpointerException berofe query ?
        return userPatternDAO.getByDateFrame(getFrom(), getTill());
    }

    private long toEpochDay(Date date) {
        return LocalDate.parse(date.toString()).toEpochDay();
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
