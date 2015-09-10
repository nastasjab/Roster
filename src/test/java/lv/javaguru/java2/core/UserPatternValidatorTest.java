package lv.javaguru.java2.core;


import lv.javaguru.java2.GenericSpringTest;
import lv.javaguru.java2.core.userpattern.InvalidDateFormatException;
import lv.javaguru.java2.core.userpattern.StartDateAfterEndDateException;
import lv.javaguru.java2.core.userpattern.UserPatternOverlapException;
import lv.javaguru.java2.core.userpattern.UserPatternValidator;
import lv.javaguru.java2.database.pattern.PatternDAO;
import lv.javaguru.java2.database.user.UserDAO;
import lv.javaguru.java2.database.user.UserPatternDAO;
import lv.javaguru.java2.domain.pattern.Pattern;
import lv.javaguru.java2.domain.pattern.PatternShift;
import lv.javaguru.java2.domain.user.User;
import lv.javaguru.java2.domain.user.UserPattern;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static lv.javaguru.java2.domain.pattern.PatternBuilder.createPattern;
import static lv.javaguru.java2.domain.pattern.PatternShiftBuilder.createPatternShift;
import static lv.javaguru.java2.domain.shift.ShiftBuilder.createShift;
import static lv.javaguru.java2.domain.user.UserBuilder.createUser;
import static lv.javaguru.java2.domain.user.UserPatternBuilder.createUserPattern;

public class UserPatternValidatorTest extends GenericSpringTest{

    @Autowired
    private UserPatternValidator userPatternValidator;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PatternDAO patternDAO;

    @Autowired
    private UserPatternDAO userPatternDAO;



    @Test
    public void testValidateCorrectUserId() {
        User user = createUser().withLogin("test").build();
        userDAO.create(user);
        try {
            userPatternValidator.validateUserId(user.getId());
        } catch (Exception e) {
            fail();
        }
    }

    @Test (expected = ObjectNotExistException.class)
    public void testValidateNonExistingUserId() throws Exception {
        User user = createUser().withLogin("test").build();
        userDAO.create(user);
        userDAO.delete(user.getId());
        userPatternValidator.validateUserId(user.getId());
    }

    @Test (expected = EmptyIdentifierException.class)
    public void testValidateZeroUserId() throws Exception {
        userPatternValidator.validateUserId(0);
    }

    @Test (expected = ValueOutOfBoundsException.class)
    public void testValidateNegativePatternsStartDay() throws Exception {
        List<PatternShift> patternShifts = new ArrayList<PatternShift>();
        for (int i = 1; i <= 7; i++)
            patternShifts.add(createPatternShift()
                    .withShift(
                            createShift()
                            .withName("test" + i)
                            .build()
                    )
                    .withSeqNo(i)
                    .build());
        UserPattern userPattern = createUserPattern()
                .withPatternStartDay(-1)
                .withPattern(createPattern()
                        .withPatternShifts(patternShifts)
                        .withName("testpattern")
                )
                .build();
        userPatternValidator.validatePatternsStartDay(userPattern);
    }

    @Test (expected = ValueOutOfBoundsException.class)
    public void testValidateZeroePatternsStartDay() throws Exception {
        List<PatternShift> patternShifts = new ArrayList<PatternShift>();
        for (int i = 1; i <= 7; i++)
            patternShifts.add(createPatternShift()
                    .withShift(
                            createShift()
                                    .withName("test" + i)
                                    .build()
                    )
                    .withSeqNo(i)
                    .build());
        UserPattern userPattern = createUserPattern()
                .withPatternStartDay(0)
                .withPattern(createPattern()
                                .withPatternShifts(patternShifts)
                                .withName("testpattern")
                )
                .build();
        userPatternValidator.validatePatternsStartDay(userPattern);
    }

    @Test
    public void testValidateDatesOk(){
        userPatternDAO.create(createUserPattern()
                .withStartDay(Date.valueOf("2015-03-14"))
                .withEndDay(Date.valueOf("2015-05-12"))
                .withPattern(createPattern().withId(1L).build())
                .build());
        userPatternDAO.create(createUserPattern()
                .withStartDay(Date.valueOf("2015-06-28"))
                .withEndDay(Date.valueOf("2015-12-20"))
                .withPattern(createPattern().withId(1L).build())
                .build());
        UserPattern userPattern = createUserPattern()
                .withStartDay(Date.valueOf("2015-05-14"))
                .withEndDay(Date.valueOf("2015-06-23"))
                .withPattern(createPattern().withId(1L).build())
                .build();
        userPatternDAO.create(userPattern);

        assertEquals(userPattern.getId(), userPatternDAO.getById(userPattern.getId()).getId());
    }

    @Test (expected = UserPatternOverlapException.class)
    public void testValidateDatesOverlap() throws Exception {
        userPatternDAO.create(createUserPattern()
                .withStartDay(Date.valueOf("2015-03-14"))
                .withEndDay(Date.valueOf("2015-05-12"))
                .withPattern(createPattern().withId(1L).build())
                .build());
        userPatternDAO.create(createUserPattern()
                .withStartDay(Date.valueOf("2015-05-14"))
                .withEndDay(Date.valueOf("2015-06-23"))
                .withPattern(createPattern().withId(1L).build())
                .build());
        userPatternDAO.create(createUserPattern()
                .withStartDay(Date.valueOf("2015-06-28"))
                .withEndDay(Date.valueOf("2015-12-20"))
                .withPattern(createPattern().withId(1L).build())
                .build());
        userPatternValidator.validateDates(createUserPattern()
                .withStartDay(Date.valueOf("2015-05-24"))
                .withEndDay(Date.valueOf("2015-07-23"))
                .withPattern(createPattern().withId(1L).build())
                .build(), true);
    }

    @Test (expected = InvalidDateFormatException.class)
    public void testValidateNullStartDate() throws Exception {
        userPatternValidator.validateDates(createUserPattern()
                .withStartDay(null)
                .withEndDay(Date.valueOf("2015-08-31"))
                .build()
                , true);
    }

    @Test (expected = InvalidDateFormatException.class)
    public void testValidateNullEndDate() throws Exception {
        userPatternValidator.validateDates(createUserPattern()
                .withStartDay(Date.valueOf("2015-09-15"))
                .withEndDay(null)
                .build()
                , false);
    }

    @Test (expected = StartDateAfterEndDateException.class)
    public void testValidateDatesEndBeforeStart() throws Exception {
        userPatternValidator.validateDates(createUserPattern()
                .withStartDay(Date.valueOf("2015-09-15"))
                .withEndDay(Date.valueOf("2015-08-15"))
                .build()
                , true);
    }


    @Test
    public void testValidatePatternsStartDay5() throws Exception {
        List<PatternShift> patternShifts = new ArrayList<PatternShift>();
        for (int i = 1; i <= 7; i++)
            patternShifts.add(createPatternShift()
                    .withShift(
                            createShift()
                                    .withName("test" + i)
                                    .build()
                    )
                    .withSeqNo(i)
                    .build());
        UserPattern userPattern = createUserPattern()
                .withPatternStartDay(5)
                .withPattern(createPattern()
                                .withPatternShifts(patternShifts)
                                .withName("testpattern")
                )
                .build();
        userPatternValidator.validatePatternsStartDay(userPattern);
        assertTrue(true);
    }

    @Test
    public void testValidatePatternsStartDay7() throws Exception {
        List<PatternShift> patternShifts = new ArrayList<PatternShift>();
        for (int i = 1; i <= 7; i++)
            patternShifts.add(createPatternShift()
                    .withShift(
                            createShift()
                                    .withName("test" + i)
                                    .build()
                    )
                    .withSeqNo(i)
                    .build());
        UserPattern userPattern = createUserPattern()
                .withPatternStartDay(7)
                .withPattern(createPattern()
                                .withPatternShifts(patternShifts)
                                .withName("testpattern")
                )
                .build();
        userPatternValidator.validatePatternsStartDay(userPattern);
        assertTrue(true);
    }

    @Test (expected = ValueOutOfBoundsException.class)
    public void testValidatePatternsStartDay8() throws Exception {
        List<PatternShift> patternShifts = new ArrayList<PatternShift>();
        for (int i = 1; i <= 7; i++)
            patternShifts.add(createPatternShift()
                    .withShift(
                            createShift()
                                    .withName("test" + i)
                                    .build()
                    )
                    .withSeqNo(i)
                    .build());
        UserPattern userPattern = createUserPattern()
                .withPatternStartDay(8)
                .withPattern(createPattern()
                                .withPatternShifts(patternShifts)
                                .withName("testpattern")
                )
                .build();
        userPatternValidator.validatePatternsStartDay(userPattern);
    }

    @Test
    public void testValidateCorrectPatternId() {
        Pattern pattern = createPattern().withName("test").build();
        patternDAO.create(pattern);
        try {
            userPatternValidator.validatePatternId(pattern.getId());
        } catch (Exception e) {
            fail();
        }
    }

    @Test (expected = ObjectNotExistException.class)
    public void testValidateNonExistingPatternId() throws Exception {
        Pattern pattern = createPattern().withName("test").build();
        patternDAO.create(pattern);
        patternDAO.delete(pattern.getId());
        userPatternValidator.validatePatternId(pattern.getId());
    }

    @Test (expected = EmptyIdentifierException.class)
    public void testValidateZeroPatternId() throws Exception {
        userPatternValidator.validatePatternId(0);
    }
    
}
