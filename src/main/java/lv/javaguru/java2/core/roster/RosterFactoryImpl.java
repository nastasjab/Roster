package lv.javaguru.java2.core.roster;

import lv.javaguru.java2.core.NoShiftFoundException;
import lv.javaguru.java2.database.pattern.PatternDAO;
import lv.javaguru.java2.database.pattern.PatternShiftDAO;
import lv.javaguru.java2.database.roster.SingleShiftDAO;
import lv.javaguru.java2.database.shift.ShiftDAO;
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

import static lv.javaguru.java2.domain.roster.SingleShiftBuilder.createSingleShift;
import static lv.javaguru.java2.domain.shift.ShiftBuilder.createShift;

@Component
public class RosterFactoryImpl implements RosterFactory {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SingleShiftDAO singleShiftDAO;

    @Autowired
    private PatternDAO patternDAO;

    @Autowired
    private PatternShiftDAO patternShiftDAO;

    @Autowired
    private UserPatternDAO userPatternDAO;

    @Autowired
    private ShiftDAO shiftDAO;


    public Roster getRoster(Roster roster) {
        try {
            roster = getRoster(roster, userDAO.getAllSorted());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roster;
    }

    public Roster getRoster(Roster roster, List<User> forUsers) {

        fillWithUsers(roster, forUsers);
        fillWithUserPatterns(roster);
        fillWithSingleShifts(roster);

        return roster;

    }

    public Shift getShift(Date date, long userId) throws NoShiftFoundException {
        try {
            return getSingleShift(date, userId).getShift();
        } catch (NoShiftFoundException e) {
            try {
                return getShiftFromUserPattern(date, userId);
            } catch (IndexOutOfBoundsException e1) {
                throw new NoShiftFoundException();
            }
        }
    }

    private SingleShift getSingleShift(Date date, long userId) throws NoShiftFoundException{
        try {
            return singleShiftDAO.getSingleShift(userId, date);
        } catch (IndexOutOfBoundsException e) {
            throw new NoShiftFoundException();
        }
    }

    public void setShift(Date date, long userId, long shiftId) throws InvalidShiftException {

        Shift shiftFromUserPattern;
        try {
            shiftFromUserPattern = getShiftFromUserPattern(date, userId);
        } catch (IndexOutOfBoundsException e) {
            shiftFromUserPattern = createShift().build();
        }

        SingleShift singleShift;
        try {
            singleShift = getSingleShift(date, userId);
        } catch (NoShiftFoundException e) {
            singleShift = createSingleShift().build();
        }

        if (shiftId == 0) {
            if (shiftFromUserPattern.getId() != 0)
                throw new InvalidShiftException();
            else if (singleShift.getId() != 0)
                singleShiftDAO.delete(singleShift.getId());
        } else if (shiftId != shiftFromUserPattern.getId() && shiftId != singleShift.getShift().getId()) {
            SingleShift newSingleShift = createSingleShift()
                    .withId(singleShift.getId())
                    .withDate(date)
                    .withShift(createShift().withId(shiftId).build())
                    .withUserId(userId)
                    .build();
            if (singleShift.getShift().getId() == 0)
                singleShiftDAO.create(newSingleShift);
            else
                singleShiftDAO.update(newSingleShift);
        } else if (shiftFromUserPattern.getId() == shiftId && singleShift.getId() != 0) {
            singleShiftDAO.delete(singleShift.getId());
        }

    }

    public List<Shift> getAvailableShifts(Date date, long userId) {
        List<Shift> shiftList = shiftDAO.getAll();

        if (! (!isShiftInSingleShifts(date, userId) && isShiftInUserPattern(date, userId)))
            shiftList.add(0, createShift().withId(0L).withName("No Shift Set").build());

        return shiftList;
    }

    private Shift getShiftFromUserPattern2(Date date, long userId) throws IndexOutOfBoundsException {
        return patternDAO.getById(getUserPattern(date, userId).getPattern().getId())
                .getPatternShifts()
                .get((int) getPatternOffset(getUserPattern(date, userId), date))
                .getShift();
    }

    private Shift getShiftFromUserPattern(Date date, long userId) throws IndexOutOfBoundsException {
        return getUserPattern(date, userId).getPattern().getPatternShifts()
                .get((int) getPatternOffset(getUserPattern(date, userId), date)).getShift();
    }

    private UserPattern getUserPattern(Date date, long userId) throws IndexOutOfBoundsException {
        return userPatternDAO.get(date, userId);
    }

    private void fillWithUsers(Roster roster, List<User> users) {
        for (User user : users)
            roster.setUserMap(user, new RosterUserShiftMap());
    }

    private boolean isShiftInUserPattern(Date date, long userid) {
        try {
            getUserPattern(date, userid);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        return true;
    }

    private boolean isShiftInSingleShifts(Date date, long userid) {
        try {
            getSingleShift(date, userid);
        } catch (NoShiftFoundException e) {
            return false;
        }

        return true;
    }

    private void fillWithUserPatterns(Roster roster) {
        try {

            for (UserPattern userPattern : getUserPatternsByDatesFromTill(roster)) {

                long epochDayFrom = getEpochDayFrom(roster, userPattern);
                long patternOffset = getPatternOffset(userPattern, epochDayFrom);
                long epochDayTill = getEpochDayTill(roster, userPattern);

                List<Shift> shiftInPattern = new ArrayList<Shift>();
                getShiftsFromPattern(userPattern, shiftInPattern);

                int patternSize = shiftInPattern.size();
                int seqNo = (int) (patternOffset % (long)patternSize);

                for (long day = epochDayFrom; day <= epochDayTill; day++) {

                    setShift(roster, userPattern.getUserId(), shiftInPattern.get(seqNo), day);

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

    private List<UserPattern> getUserPatternsByDatesFromTill(Roster roster)  {
        return userPatternDAO.getByDateFrame(roster.getFrom(), roster.getTill());
    }

    private long getEpochDayFrom(Roster roster, UserPattern userPattern) {
        long epochDayFrom;
        if (Dates.toEpochDay(userPattern.getStartDay()) < Dates.toEpochDay(roster.getFrom()))
            epochDayFrom = Dates.toEpochDay(roster.getFrom());
        else
            epochDayFrom = Dates.toEpochDay(userPattern.getStartDay());
        return epochDayFrom;
    }

    private long getEpochDayTill(Roster roster, UserPattern userPattern) {
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

    private void setShift(Roster roster, long userId, Shift shift, long day) {

        try {
            roster.getUserShifts(userId).setShift(Dates.toSqlDate(day), shift);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void fillWithSingleShifts(Roster roster) {

        List<SingleShift> shiftsOnExactDay = singleShiftDAO.getSingleShift(roster.getFrom(), roster.getTill());

        for (SingleShift singleShift : shiftsOnExactDay) {
            setShift(roster, singleShift.getUserId(), singleShift.getShift(), Dates.toEpochDay(singleShift.getDate()));
        }

    }


}
