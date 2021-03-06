package lv.javaguru.java2.core;


import lv.javaguru.java2.GenericSpringTest;
import lv.javaguru.java2.core.shift.InvalidTimeFormatException;
import lv.javaguru.java2.core.shift.ShiftValidator;
import lv.javaguru.java2.database.shift.ShiftDAO;
import lv.javaguru.java2.domain.shift.Shift;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static lv.javaguru.java2.domain.shift.ShiftBuilder.createShift;

public class ShiftValidatorTest extends GenericSpringTest {

    @Autowired
    ShiftValidator shiftValidator;

    @Autowired
    ShiftDAO shiftDAO;

    @Test
    public void testValidateStartTime() throws Exception {
        shiftValidator.validateStartTime("00:00");
        shiftValidator.validateStartTime("0:00");
        shiftValidator.validateStartTime("23:59");
        shiftValidator.validateStartTime("");
    }

    @Test (expected = InvalidTimeFormatException.class)
    public void testValidateStartTimeError1() throws Exception {
        shiftValidator.validateStartTime("08:0a");
    }

    @Test (expected = InvalidTimeFormatException.class)
    public void testValidateStartTimeError2() throws Exception {
        shiftValidator.validateStartTime("24:00");
    }

    @Test
    public void testValidateEndTime() throws Exception {
        shiftValidator.validateEndTime("00:00");
        shiftValidator.validateEndTime("0:00");
        shiftValidator.validateEndTime("23:59");
        shiftValidator.validateEndTime("");
    }

    @Test (expected = InvalidTimeFormatException.class)
    public void testValidateEndTimeError1() throws Exception {
        shiftValidator.validateEndTime("08:0a");
    }

    @Test (expected = InvalidTimeFormatException.class)
    public void testValidateEndTimeError2() throws Exception {
        shiftValidator.validateEndTime("24:00");
    }

    @Test
    public void testValidateName() throws Exception {
        Shift shift =  createShift()
                .withName("ssssss1").withShiftStarts("07:00:00").withShiftEnds("15:00:00")
                .build();
        shiftDAO.create(shift);
        shiftValidator.validateName(shift.getName(), shift.getId(), false);
    }

    @Test (expected = ObjectExistException.class)
    public void testValidateNameError1() throws Exception {
        Shift shift = createShift()
                .withName("ssssss1").withShiftStarts("07:00:00").withShiftEnds("15:00:00")
                .build();
        shiftDAO.create(shift);
        shiftValidator.validateName(shift.getName(),0,true);
    }

    @Test (expected = ObjectExistException.class)
    public void testValidateNameError2() throws Exception {
        Shift shift =  createShift()
                .withName("ssssss1").withShiftStarts("07:00:00").withShiftEnds("15:00:00")
                .build();
        shiftDAO.create(shift);
        shiftValidator.validateName(shift.getName(),shift.getId()+1,false);
    }

}
