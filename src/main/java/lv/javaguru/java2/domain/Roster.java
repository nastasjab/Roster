package lv.javaguru.java2.domain;

import lv.javaguru.java2.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Component
public class Roster extends Generic {

// TODO Do not autowire?
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
        fillUsers();
        fillShifts();
    }

    private void fillUsers() {
        try {
            // TODO userDAO.getAll() throws NullPointerException before query ?
            for (User user : userDAO.getAll())
                shiftMap.put(user, new RosterUserShiftMap(user));
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

                    shiftMap.get(userPattern.getUserId()).setShift(toSqlDate(day), shiftInPattern.get(seqNo));

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

    private void getShiftsFromPattern(UserPattern userPattern, List<Shift> shiftInPattern) throws DBException {
        for (PatternShift patternShift : patternShiftDAO.getAll(userPattern.getPatternShift().getId())) {
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

    private List<Shift> fillWithShifts(long epochDayFrom, long epochDayTill, long patternOffset) {
        List<Shift> result = new ArrayList<Shift>();
        return result;
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

    public void setUserMap(User user, RosterUserShiftMap shiftMap) {
        this.shiftMap.put(user, shiftMap);
    }

    public Set<User> getUserList() {
        return shiftMap.keySet();
    }

}
