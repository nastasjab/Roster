package lv.javaguru.java2.core;

import lv.javaguru.java2.GenericSpringTest;
import lv.javaguru.java2.core.roster.RosterService;
import lv.javaguru.java2.database.pattern.PatternDAO;
import lv.javaguru.java2.database.roster.SingleShiftDAO;
import lv.javaguru.java2.database.shift.ShiftDAO;
import lv.javaguru.java2.database.user.UserPatternDAO;
import lv.javaguru.java2.domain.pattern.Pattern;
import lv.javaguru.java2.domain.pattern.PatternShift;
import lv.javaguru.java2.domain.roster.SingleShift;
import lv.javaguru.java2.domain.shift.Shift;
import lv.javaguru.java2.domain.user.UserPattern;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static lv.javaguru.java2.domain.shift.ShiftBuilder.createShift;
import static org.junit.Assert.assertEquals;

public class RosterTest extends GenericSpringTest{

    @Autowired
    private RosterService rosterService;

    @Autowired
    private ShiftDAO shiftDAO;

    @Autowired
    private PatternDAO patternDAO;

    @Autowired
    private UserPatternDAO userPatternDAO;

    @Autowired
    private SingleShiftDAO singleShiftDAO;

    @Test
    public void getShiftTest() {
        Shift shift1 = createShift().withName("test1").build();
        Shift shift2 = createShift().withName("test2").build();
        Shift shift3 = createShift().withName("test3").build();

        List<Shift> shifts = shiftDAO.getAll();
        int shiftsCount = shifts == null ? 0 : shifts.size();
        shiftDAO.create(shift1);
        shiftDAO.create(shift2);
        shiftDAO.create(shift3);
        assertEquals(3, shiftDAO.getAll().size() - shiftsCount);


        Pattern pattern = new Pattern();
        pattern.setName("test");

        PatternShift patternShift1 = new PatternShift();
        patternShift1.setShift(shift1);
        patternShift1.setPattern(pattern);
        patternShift1.setSeqNo(1);

        PatternShift patternShift2 = new PatternShift();
        patternShift2.setShift(shift2);
        patternShift2.setPattern(pattern);
        patternShift2.setSeqNo(2);

        PatternShift patternShift3 = new PatternShift();
        patternShift3.setShift(shift3);
        patternShift3.setPattern(pattern);
        patternShift3.setSeqNo(3);

        List<PatternShift> patternShifts = new ArrayList<PatternShift>();
        patternShifts.add(patternShift1);
        patternShifts.add(patternShift2);
        patternShifts.add(patternShift3);
        pattern.setPatternShifts(patternShifts);

        List<Pattern> patterns = patternDAO.getAll();
        int patternCount = patterns == null ? 0 : patterns.size();
        patternDAO.create(pattern);
        assertEquals(1, patternDAO.getAll().size() - patternCount);
        assertEquals(3, patternDAO.getById(pattern.getId()).getSize());
        assertEquals(pattern, patternDAO.getById(pattern.getId()));


        UserPattern userPattern = new UserPattern();
        userPattern.setPatternStartDay(2);
        userPattern.setPattern(pattern);
        userPattern.setStartDay(Date.valueOf("2014-01-01"));
        userPattern.setEndDay(Date.valueOf("2014-02-28"));
        userPattern.setUserId(97);

        userPatternDAO.create(userPattern);
        assertEquals(userPattern, userPatternDAO.getById(userPattern.getId()));


        SingleShift singleShift1 = new SingleShift();
        singleShift1.setUserId(97);
        singleShift1.setShift(shift2);
        singleShift1.setDate(Date.valueOf("2014-01-11"));
        singleShiftDAO.create(singleShift1);
        assertEquals(singleShift1, singleShiftDAO.getById(singleShift1.getId()));

        SingleShift singleShift2 = new SingleShift();
        singleShift2.setUserId(96);
        singleShift2.setShift(shift2);
        singleShift2.setDate(Date.valueOf("2014-01-08"));
        singleShiftDAO.create(singleShift2);
        assertEquals(singleShift2, singleShiftDAO.getById(singleShift2.getId()));


        assertEquals(rosterService.getShift(Date.valueOf("2013-12-31"), 97).getId(), 0);
        assertEquals(rosterService.getShift(Date.valueOf("2014-01-08"), 97), shift3);
        assertEquals(rosterService.getShift(Date.valueOf("2014-01-09"), 97), shift1);
        assertEquals(rosterService.getShift(Date.valueOf("2014-01-10"), 97), shift2);
        assertEquals(rosterService.getShift(Date.valueOf("2014-01-11"), 97), shift2);
    }

    @Test
    public void setShiftTest() {
        Shift shift1 = createShift().withName("test1").build();
        Shift shift2 = createShift().withName("test2").build();
        Shift shift3 = createShift().withName("test3").build();

        List<Shift> shifts = shiftDAO.getAll();
        int shiftsCount = shifts == null ? 0 : shifts.size();
        shiftDAO.create(shift1);
        shiftDAO.create(shift2);
        shiftDAO.create(shift3);
        assertEquals(3, shiftDAO.getAll().size() - shiftsCount);


        Pattern pattern = new Pattern();
        pattern.setName("test");

        PatternShift patternShift1 = new PatternShift();
        patternShift1.setShift(shift1);
        patternShift1.setPattern(pattern);
        patternShift1.setSeqNo(1);

        PatternShift patternShift2 = new PatternShift();
        patternShift2.setShift(shift2);
        patternShift2.setPattern(pattern);
        patternShift2.setSeqNo(2);

        PatternShift patternShift3 = new PatternShift();
        patternShift3.setShift(shift3);
        patternShift3.setPattern(pattern);
        patternShift3.setSeqNo(3);

        List<PatternShift> patternShifts = new ArrayList<PatternShift>();
        patternShifts.add(patternShift1);
        patternShifts.add(patternShift2);
        patternShifts.add(patternShift3);
        pattern.setPatternShifts(patternShifts);

        List<Pattern> patterns = patternDAO.getAll();
        int patternCount = patterns == null ? 0 : patterns.size();
        patternDAO.create(pattern);
        assertEquals(1, patternDAO.getAll().size() - patternCount);
        assertEquals(3, patternDAO.getById(pattern.getId()).getSize());
        assertEquals(pattern, patternDAO.getById(pattern.getId()));


        UserPattern userPattern = new UserPattern();
        userPattern.setPatternStartDay(2);
        userPattern.setPattern(pattern);
        userPattern.setStartDay(Date.valueOf("2014-01-01"));
        userPattern.setEndDay(Date.valueOf("2014-02-28"));
        userPattern.setUserId(97);

        userPatternDAO.create(userPattern);
        assertEquals(userPattern, userPatternDAO.getById(userPattern.getId()));


        SingleShift singleShift1 = new SingleShift();
        singleShift1.setUserId(97);
        singleShift1.setShift(shift2);
        singleShift1.setDate(Date.valueOf("2014-01-11"));
        singleShiftDAO.create(singleShift1);
        assertEquals(singleShift1, singleShiftDAO.getById(singleShift1.getId()));

        SingleShift singleShift2 = new SingleShift();
        singleShift2.setUserId(97);
        singleShift2.setShift(shift2);
        singleShift2.setDate(Date.valueOf("2014-01-08"));
        singleShiftDAO.create(singleShift2);
        assertEquals(singleShift2, singleShiftDAO.getById(singleShift2.getId()));


        List<Shift> shiftList = new ArrayList<Shift>();
        shiftList.add(new Shift());
        shiftList.add(shift1);
        shiftList.add(shift2);
        shiftList.add(shift3);

        for (Shift shiftToSet : shiftList) {
            for (Shift shiftInUserPattern : shiftList) {
                for (Shift shiftInSingleShifts : shiftList) {
                    //userPatternDAO.create(???); manip w/dates?
                    // singleShiftDAO.create(???); uhh, Object Factory needed...
                    rosterService.setShift(Date.valueOf("1970-01-01"), 99, shiftToSet.getId());
                }
            }
        }

        assertEquals(rosterService.getShift(Date.valueOf("2013-12-31"), 97).getId(), 0);
        assertEquals(rosterService.getShift(Date.valueOf("2014-01-08"), 97), shift2);
        assertEquals(rosterService.getShift(Date.valueOf("2014-01-09"), 97), shift1);
        assertEquals(rosterService.getShift(Date.valueOf("2014-01-10"), 97), shift2);
        assertEquals(rosterService.getShift(Date.valueOf("2014-01-11"), 97), shift2);
    }



}
