package lv.javaguru.java2.core;


import lv.javaguru.java2.GenericSpringTest;
import lv.javaguru.java2.core.shift.InvalidTimeFormatException;
import lv.javaguru.java2.core.shift.ShiftValidator;
import lv.javaguru.java2.database.ShiftDAOImplTest;
import lv.javaguru.java2.database.shift.ShiftDAO;
import lv.javaguru.java2.domain.shift.Shift;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    }

    @Test (expected = InvalidTimeFormatException.class)
    public void testValidateStartTimeError1() throws Exception {
        shiftValidator.validateStartTime("08:0a");
    }

    @Test (expected = InvalidTimeFormatException.class)
    public void testValidateStartTimeError2() throws Exception {
        shiftValidator.validateStartTime("24:00");
    }

    @Test (expected = InvalidTimeFormatException.class)
    public void testValidateStartTimeError3() throws Exception {
        shiftValidator.validateStartTime("");
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
        Shift shift = ShiftDAOImplTest.createShift("ssssss1", "07:00:00", "15:00:00");
        shiftDAO.create(shift);
        shiftValidator.validateName(shift.getName(), shift.getId(), false);
    }

    @Test (expected = ObjectExistException.class)
    public void testValidateNameError1() throws Exception {
        Shift shift = ShiftDAOImplTest.createShift("ssssss1", "07:00:00", "15:00:00");
        shiftDAO.create(shift);
        shiftValidator.validateName(shift.getName(),0,true);
    }

    @Test (expected = ObjectExistException.class)
    public void testValidateNameError2() throws Exception {
        Shift shift = ShiftDAOImplTest.createShift("ssssss1", "07:00:00", "15:00:00");
        shiftDAO.create(shift);
        shiftValidator.validateName(shift.getName(),shift.getId()+1,false);
    }

}
