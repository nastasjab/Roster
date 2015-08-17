package lv.javaguru.java2.servlet.mvc.controller;


import lv.javaguru.java2.database.*;
import lv.javaguru.java2.domain.*;
import lv.javaguru.java2.servlet.mvc.MVCController;
import lv.javaguru.java2.servlet.mvc.MVCModel;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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

    public MVCModel processRequest(HttpServletRequest req) {
        RosterMap rosterMap = null;
        Map<Long, User> users = null;
        Map<Long, Shift> shifts = null;
        Map<Long, Pattern> patterns = null;

        try {

            rosterMap = new RosterMap(getDateFrom(req), getDateTill(req));

            users = new HashMap<Long, User>();
            try {
                for (User user : userDAO.getAll()) {
                    rosterMap.setUserMap(user, null);
                    users.put(user.getId(), user);
                }
            } catch (DBException e) {
                e.printStackTrace();
            }

            shifts = new HashMap<Long, Shift>();

            for (Shift shift : shiftDAO.getAll()) {
                shifts.put(shift.getId(), shift);
            }

            patterns = new HashMap<Long, Pattern>();

            for (Pattern pattern : patternDAO.getAll()) {
// TODO
// next line should be reworked. pattern doesn't contains all related shifts
                //!!!!!!!!!!!//                 pattern.setPatternShifts(patternShiftDAO.getAll(pattern.getId()));
                patterns.put(pattern.getId(), pattern);
            }
        } catch (JDBCException e) {
            e.printStackTrace();
            // TODO more intellegent exception handling
        } catch (DBException e) {
            e.printStackTrace();
        }

        try {
            for (UserPattern userPattern : userPatternDAO.getAll()) {
                int seqNo = userPattern.getPatternStartDay();
                Map<Integer, Shift> seqNoShiftMap = new HashMap<Integer, Shift>();
                int patternLength = 0;
// next line should be reworked. pattern doesn't contains all related shifts
// TODO
/*                for (PatternShift patternShift : patterns.get(userPattern.getPatternShiftId()).getPatternShifts()) {
                    seqNoShiftMap.put(patternShift.getSeqNo(), shifts.get(patternShift.getShiftId()));
                    if (patternShift.getSeqNo() > patternLength)
                        patternLength = patternShift.getSeqNo();
                }*/
                for (long epochDay = LocalDate.parse(getDateFrom(req).toString()).toEpochDay();
                     epochDay <= LocalDate.parse(getDateTill(req).toString()).toEpochDay(); epochDay++) {
                    if (seqNoShiftMap.get(seqNo) != null)/*
                        rosterMap.getUserShifts(users.get(userPattern.getUserId())).setShift(Date.valueOf(LocalDate.ofEpochDay(epochDay)),
                                seqNoShiftMap.get(seqNo))*/ ;
                    else {
                        System.out.println("---------------------\nseqNo = " + seqNo);
                        System.out.println("seqNoShiftMap.get(seqNo)" + seqNoShiftMap.get(seqNo).getName());
                        System.out.println("users.get(userPattern.getUserId())" + users.get(userPattern.getUserId()));
                        System.out.println("Date.valueOf(LocalDate.ofEpochDay(epochDay" + Date.valueOf(LocalDate.ofEpochDay(epochDay)));
                    }
                    if (seqNo < patternLength)
                        seqNo++;
                    else
                        seqNo = 0;
                }
            }
        } catch (DBException e) {
            e.printStackTrace();
        }



        return new MVCModel(rosterMap, "/roster.jsp");

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
