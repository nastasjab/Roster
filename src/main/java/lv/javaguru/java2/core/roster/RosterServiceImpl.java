package lv.javaguru.java2.core.roster;

import lv.javaguru.java2.database.pattern.PatternShiftDAO;
import lv.javaguru.java2.database.roster.SingleShiftDAO;
import lv.javaguru.java2.database.user.UserDAO;
import lv.javaguru.java2.database.user.UserPatternDAO;
import lv.javaguru.java2.domain.*;
import lv.javaguru.java2.domain.pattern.PatternShift;
import lv.javaguru.java2.domain.roster.Roster;
import lv.javaguru.java2.domain.roster.RosterUserShiftMap;
import lv.javaguru.java2.domain.roster.SingleShift;
import lv.javaguru.java2.domain.shift.Shift;
import lv.javaguru.java2.domain.user.User;
import lv.javaguru.java2.domain.user.UserPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class RosterServiceImpl implements RosterService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SingleShiftDAO singleShiftDAO;

    @Autowired
    private PatternShiftDAO patternShiftDAO;

    @Autowired
    private UserPatternDAO userPatternDAO;

    private Roster roster;


    public Roster getRoster(Roster roster) {
        try {
            roster = getRoster(roster, userDAO.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roster;
    }

    public Roster getRoster(Roster roster, List<User> forUsers) {

        this.roster = roster;

        fillWithUsers(forUsers);

        fillWithUserPatterns();

        fillWithSingleShifts();

        return roster;

    }

    public Shift getShift(Date date, long userId) {
        try {
            return getSingleShift(date, userId);
        } catch (IndexOutOfBoundsException e) {
            return getShiftFromUserPattern(date, userId);
        }
    }

    private Shift getShiftFromUserPattern(Date date, long userId) {
        try {
            return getUserPattern(date, userId).getPattern().getPatternShifts()
                    .get((int) getPatternOffset(getUserPattern(date, userId), date)).getShift();
        } catch (IndexOutOfBoundsException e) {
            return new Shift();
        }
    }

    private UserPattern getUserPattern(Date date, long userId) throws IndexOutOfBoundsException {
        return userPatternDAO.get(date, userId);
    }

    private Shift getSingleShift(Date date, long userId) {
        try {
            return singleShiftDAO.getSingleShift(userId, date).getShift();
        } catch (IndexOutOfBoundsException e) {
            return new Shift();
        }
    }

    public void setShift(Date date, long userId, long shiftId) {

        Shift shiftFromUserPattern = getShiftFromUserPattern(date, userId);
        Shift shiftFromSingleShifts = getSingleShift(date, userId);

        if (shiftFromSingleShifts.getId() == 0 && shiftId != shiftFromUserPattern.getId()) {
            SingleShift singleShift = new SingleShift();
            singleShift.setDate(date);
            singleShift.getShift().setId(shiftId);
            singleShift.setUserId(userId);
            singleShiftDAO.create(singleShift);
        } else if (shiftFromSingleShifts.getId() != shiftId && shiftId != shiftFromUserPattern.getId()) {
            SingleShift singleShift = new SingleShift();
            singleShift.setDate(date);
            singleShift.getShift().setId(shiftId);
            singleShift.setUserId(userId);
            singleShiftDAO.update(singleShift);
        } else if (shiftFromUserPattern.getId() == shiftId) {
            singleShiftDAO.delete(shiftFromSingleShifts.getId());
        }

    }

    private void fillWithUsers(List<User> users) {
        for (User user : users)
            roster.setUserMap(user, new RosterUserShiftMap());
    }

    private void fillWithUserPatterns() {
        try {

            for (UserPattern userPattern : getUserPatternsByDatesFromTill()) {

                long epochDayFrom = getEpochDayFrom(userPattern);
                long patternOffset = getPatternOffset(userPattern, epochDayFrom);
                long epochDayTill = getEpochDayTill(userPattern);

                List<Shift> shiftInPattern = new ArrayList<Shift>();
                getShiftsFromPattern(userPattern, shiftInPattern);

                int patternSize = shiftInPattern.size();
                int seqNo = (int) (patternOffset % (long)patternSize);

                for (long day = epochDayFrom; day <= epochDayTill; day++) {

                    setShift(userPattern.getUserId(), shiftInPattern.get(seqNo), day);

                    if (seqNo >= patternSize - 1)
                        seqNo = 0;
                    else
                        seqNo++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long getPatternOffset(UserPattern userPattern, long epochDayFrom) {
        return (epochDayFrom - Dates.toEpochDay(userPattern.getStartDay()) + (long) userPattern.getPatternStartDay() - 1)
                % userPattern.getPattern().getSize();
    }

    private long getPatternOffset(UserPattern userPattern, Date date) {
        return getPatternOffset(userPattern, Dates.toEpochDay(date));
    }

    private List<UserPattern> getUserPatternsByDatesFromTill()  {
        return userPatternDAO.getByDateFrame(roster.getFrom(), roster.getTill());
    }

    private long getEpochDayFrom(UserPattern userPattern) {
        long epochDayFrom;
        if (Dates.toEpochDay(userPattern.getStartDay()) < Dates.toEpochDay(roster.getFrom()))
            epochDayFrom = Dates.toEpochDay(roster.getFrom());
        else
            epochDayFrom = Dates.toEpochDay(userPattern.getStartDay());
        return epochDayFrom;
    }

    private long getEpochDayTill(UserPattern userPattern) {
        long epochDayTill;
        if (Dates.toEpochDay(userPattern.getEndDay()) > Dates.toEpochDay(roster.getTill()))
            epochDayTill = Dates.toEpochDay(roster.getTill());
        else
            epochDayTill = Dates.toEpochDay(userPattern.getEndDay());
        return epochDayTill;
    }

    private void getShiftsFromPattern(UserPattern userPattern, List<Shift> shiftInPattern)  {
        for (PatternShift patternShift : patternShiftDAO.getAll(userPattern.getPattern().getId())) {
            shiftInPattern.add(patternShift.getShift());
        }
    }

    private void setShift(long userId, Shift shift, long day) {

        try {
            roster.getUserShifts(userId).setShift(Dates.toSqlDate(day), shift);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void fillWithSingleShifts() {

        List<SingleShift> shiftsOnExactDay = singleShiftDAO.getSingleShift(roster.getFrom(), roster.getTill());

        for (SingleShift singleShift : shiftsOnExactDay) {

            setShift(singleShift.getUserId(), singleShift.getShift(), Dates.toEpochDay(singleShift.getDate()));

        }

    }


}
