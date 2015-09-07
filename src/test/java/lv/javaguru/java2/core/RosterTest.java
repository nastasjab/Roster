package lv.javaguru.java2.core;

import lv.javaguru.java2.GenericSpringTest;
import lv.javaguru.java2.core.roster.InvalidShiftException;
import lv.javaguru.java2.core.roster.RosterService;
import lv.javaguru.java2.database.pattern.PatternDAO;
import lv.javaguru.java2.database.roster.SingleShiftDAO;
import lv.javaguru.java2.database.shift.ShiftDAO;
import lv.javaguru.java2.database.user.UserPatternDAO;
import lv.javaguru.java2.domain.Dates;
import lv.javaguru.java2.domain.pattern.Pattern;
import lv.javaguru.java2.domain.pattern.PatternShift;
import lv.javaguru.java2.domain.roster.SingleShift;
import lv.javaguru.java2.domain.shift.Shift;
import lv.javaguru.java2.domain.user.UserPattern;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static lv.javaguru.java2.domain.shift.ShiftBuilder.createShift;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

    List<Shift> shiftList = new ArrayList<Shift>();
    Pattern pattern;
    List<PatternShift> patternShifts = new ArrayList<PatternShift>();
    UserPattern userPattern;
    long userId;

    @Before
    public void init(){

        userId = 9999;

        pattern = new Pattern();
        pattern.setName("test");

        for (int i = 0; i < 5; i++) {
            Shift shift = createShift().withName("T" + i).build();
            shiftList.add(shift);
            shiftDAO.create(shift);

            PatternShift patternShift = new PatternShift();
            patternShift.setSeqNo(i + 1);
            patternShift.setShift(shift);
            patternShift.setPattern(pattern);
            patternShifts.add(patternShift);
        }

        pattern.setPatternShifts(patternShifts);
        patternDAO.create(pattern);

        userPattern = new UserPattern();
        userPattern.setPatternStartDay(1);
        userPattern.setPattern(pattern);
        userPattern.setStartDay(Date.valueOf("1970-01-03"));
        userPattern.setEndDay(Date.valueOf("1970-01-05"));
        userPattern.setUserId(userId);

        userPatternDAO.create(userPattern);

        SingleShift singleShift1 = new SingleShift();
        singleShift1.setUserId(userId);
        singleShift1.setDate(Date.valueOf("1970-01-02"));
        singleShift1.setShift(shiftList.get(2));
        singleShiftDAO.create(singleShift1);

        SingleShift singleShift2 = new SingleShift();
        singleShift2.setUserId(userId);
        singleShift2.setDate(Date.valueOf("1970-01-05"));
        singleShift2.setShift(shiftList.get(0));
        singleShiftDAO.create(singleShift2);

    }

    @Test
    public void getEmptyShiftTest() {

        boolean exceptionThrown = false;

        try {
            rosterService.getShift(Date.valueOf("1970-01-01"), userId);
        } catch (NoShiftFoundException e) {
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);

    }

    @Test
    public void getShiftFromUserPatternTest() {

        Shift shiftFromUserPattern = null;

        try {
            shiftFromUserPattern = rosterService.getShift(Date.valueOf("1970-01-04"), userId);
        } catch (NoShiftFoundException e) {
            fail();
        }

        assertEquals(shiftList.get(1), shiftFromUserPattern);

    }

    @Test
    public void getShiftFromSingleShifts() {

        Shift shiftFromSingleShifts = null;

        try {
            shiftFromSingleShifts = rosterService.getShift(Date.valueOf("1970-01-02"), userId);
        } catch (NoShiftFoundException e) {
            fail();
        }

        assertEquals(shiftList.get(2), shiftFromSingleShifts);
    }

    @Test
    public void getShiftFromBoth() {

        Shift shiftFromSingleShifts = null;

        try {
            shiftFromSingleShifts = rosterService.getShift(Date.valueOf("1970-01-05"), userId);
        } catch (NoShiftFoundException e) {
            fail();
        }

        assertEquals(shiftList.get(0), shiftFromSingleShifts);
    }

    /*
    @Test
    public void setEmptyShiftToEmptyPatternAndSingleShift() {

        boolean exceptionThrown = false;

        try {
            rosterService.setShift(Date.valueOf("1970-01-01"), userId, 0);
        } catch (InvalidShiftException e) {
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);

    }
    */

    /*
    @Test
    public void setShiftTest() {
        Shift shift1 = new Shift();
        Shift shift2 = new Shift();
        Shift shift3 = new Shift();

        shift1.setName("test1");
        shift2.setName("test2");
        shift3.setName("test3");

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
        userPattern.setPatternStartDay(1);
        userPattern.setPattern(pattern);
        userPattern.setStartDay(Dates.toSqlDate(1));
        userPattern.setEndDay(Dates.toSqlDate(15));
        userPattern.setUserId(99);

        userPatternDAO.create(userPattern);
        assertEquals(userPattern, userPatternDAO.getById(userPattern.getId()));


        List<Shift> shiftList = new ArrayList<Shift>();
        shiftList.add(new Shift());
        shiftList.add(shift1);
        shiftList.add(shift2);
        shiftList.add(shift3);

        for (Shift shiftToSet : shiftList) {
            for (long epochDay = 0; epochDay < 4; epochDay++) {
                for (Shift shiftInSingleShifts : shiftList) {

                    Shift expectedShift = shiftToSet;
                    SingleShift singleShift = new SingleShift();

                    if (shiftInSingleShifts.getId() != 0) {
                        singleShift.setUserId(99);
                        singleShift.setShift(shiftInSingleShifts);
                        singleShift.setDate(Dates.toSqlDate(epochDay));
                        singleShiftDAO.create(singleShift);
                        assertEquals(singleShift, singleShiftDAO.getById(singleShift.getId()));
                    }

                    System.out.println("set = " + shiftToSet.getName()
                            + ", U/P = " + shiftList.get((int)epochDay).getName()
                            + ", S/S = " + singleShift.getShift().getName());

                    boolean exceptionThrown = false;
                    try {
                        rosterService.setShift(Dates.toSqlDate(epochDay), 99, shiftToSet.getId());
                    } catch (InvalidShiftException e) {
                        exceptionThrown = true;
                        expectedShift = rosterService.getShift(Dates.toSqlDate(epochDay), 99);
                        if (shiftToSet.getId() != 0)
                            assertTrue(false);
                    }

                    if (shiftToSet.getId() == 0 && epochDay >= 1 && exceptionThrown == false)
                        assertTrue(false); // Exception not thrown

                    Shift actualShift = rosterService.getShift(Dates.toSqlDate(epochDay), 99);
                    System.out.println("Expect: " + expectedShift.getName()
                            + " Actual: " + actualShift.getName()
                            + " Exception thrown: " + exceptionThrown);
                    assertEquals(expectedShift.getId(), actualShift.getId());

                }
            }
        }

    }
*/
}
