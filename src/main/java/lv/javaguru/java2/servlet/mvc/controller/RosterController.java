package lv.javaguru.java2.servlet.mvc.controller;


import lv.javaguru.java2.database.*;
import lv.javaguru.java2.domain.*;
import lv.javaguru.java2.servlet.mvc.MVCController;
import lv.javaguru.java2.servlet.mvc.MVCModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class RosterController implements MVCController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ShiftDAO shiftDAO;

    @Autowired
    private ShiftOnExactDayDAO shiftOnExactDayDAO;

    @Autowired
    private PatternDAO patternDAO;

    @Autowired
    private PatternShiftDAO patternShiftDAO;

    @Autowired
    private UserPatternDAO userPatternDAO;

    private Roster roster;

    public MVCModel processRequest(HttpServletRequest req) {

        roster = new Roster(getDateFrom(req), getDateTill(req));

        try {
            for (User user : userDAO.getAll())
                roster.setUserMap(user, new RosterUserShiftMap());
        } catch (DBException e) {
            e.printStackTrace();
        }

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

                    if (seqNo >= patternSize - 1)
                        seqNo = 0;
                    else
                        seqNo++;
                }
            }

        } catch (DBException e) {
            e.printStackTrace();
        }

        return new MVCModel(roster, "/roster.jsp");

    }

    private void setShift(UserPattern userPattern, Shift shift, long day) {
        /*
        if (roster == null || userPattern == null || toSqlDate(day) == null || shift == null || shift.getName() == null) {
            System.out.println("roster" + roster.toString());
            System.out.println("userPattern" + userPattern.toString());
            System.out.println("userPattern.getUserId" + userPattern.getUserId());
            System.out.println("toSqlDate(day)" + toSqlDate(day));
            System.out.println("shiftName" + shift.getName());
        }*/
        try {
            roster.getUserShifts(userPattern.getUserId()).setShift(toSqlDate(day), shift);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // shiftMap.get(userPattern.getUserId()).setShift(toSqlDate(day), shiftInPattern.get(seqNo));
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

    private Date getTill() {
        return roster.getTill();
    }

    private long getEpochDayFrom(UserPattern userPattern) {
        long epochDayFrom;
        if (toEpochDay(userPattern.getStartDay()) < toEpochDay(getFrom()))
            epochDayFrom = toEpochDay(getFrom());
        else
            epochDayFrom = toEpochDay(userPattern.getStartDay());
        return epochDayFrom;
    }

    private Date getFrom() {
        return roster.getFrom();
    }

    private List<UserPattern> getUserPatternsByDatesFromTill() throws DBException {
        return userPatternDAO.getByDateFrame(getFrom(), getTill());
    }

    private long toEpochDay(Date date) {
        return LocalDate.parse(date.toString()).toEpochDay();
    }

    private Date getDateFrom(HttpServletRequest req) {
        Date result;
        try {
            result = Date.valueOf(req.getParameter("roster_date_from"));
        } catch (IllegalArgumentException e){
            result = Date.valueOf(LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-01");
        }
        return result;
    }

    private Date getDateTill(HttpServletRequest req) {
        Date result;
        try {
            result = Date.valueOf(req.getParameter("roster_date_till"));
        } catch (IllegalArgumentException e){
            result = Date.valueOf(LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().lengthOfMonth());
        }
        return result;
    }

}
