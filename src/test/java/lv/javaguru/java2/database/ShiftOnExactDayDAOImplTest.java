package lv.javaguru.java2.database;

import lv.javaguru.java2.database.roster.ShiftOnExactDayDAO;
import lv.javaguru.java2.domain.roster.ShiftOnExactDay;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;


public class ShiftOnExactDayDAOImplTest extends GenericSpringTest {
    @Autowired
    private ShiftOnExactDayDAO shiftOnExactDayDAO;

    private ShiftOnExactDay shiftOnExactDay;
    private ShiftOnExactDay shiftOnExactDay2;

    @Before
    public void init()  {
        super.init();
        shiftOnExactDay = createShiftOnExactDay(1, Date.valueOf("2015-08-15"), 1);
        shiftOnExactDay2 = createShiftOnExactDay(2, Date.valueOf("2015-08-25"), 2);
    }

    @Test
    public void testCreate()  {
        shiftOnExactDayDAO.create(shiftOnExactDay);

        ShiftOnExactDay shiftOnExactDayFromDB = shiftOnExactDayDAO.getById(shiftOnExactDay.getId());
        assertNotNull(shiftOnExactDayFromDB);
        assertEquals(shiftOnExactDay.getId(), shiftOnExactDayFromDB.getId());
        assertEquals(shiftOnExactDay.getUserId(), shiftOnExactDayFromDB.getUserId());
        assertEquals(shiftOnExactDay.getDate(), shiftOnExactDayFromDB.getDate());
        assertEquals(shiftOnExactDay.getShift().getId(), shiftOnExactDayFromDB.getShift().getId());

        shiftOnExactDayFromDB = shiftOnExactDayDAO.getById(1000000);
        assertNull(shiftOnExactDayFromDB);
    }

    @Test
    public void testMultipleUserCreation()  {
        shiftOnExactDayDAO.create(shiftOnExactDay);
        shiftOnExactDayDAO.create(shiftOnExactDay2);
        List<ShiftOnExactDay> userPatterns = shiftOnExactDayDAO.getAll();
        assertEquals(2, userPatterns.size());
    }

    @Test
    public void testDelete()  {
        shiftOnExactDayDAO.create(shiftOnExactDay);
        shiftOnExactDayDAO.create(shiftOnExactDay2);
        List<ShiftOnExactDay> shiftsOnExactDays = shiftOnExactDayDAO.getAll();
        assertEquals(2, shiftsOnExactDays.size());

        shiftOnExactDayDAO.delete(shiftOnExactDay.getId());
        shiftsOnExactDays = shiftOnExactDayDAO.getAll();
        assertEquals(1, shiftsOnExactDays.size());

        shiftOnExactDayDAO.delete(shiftOnExactDay2.getId());
        shiftsOnExactDays = shiftOnExactDayDAO.getAll();
        assertEquals(0, shiftsOnExactDays.size());
    }

    @Test
    public void testUpdate()  {
        shiftOnExactDayDAO.create(shiftOnExactDay);

        shiftOnExactDay = shiftOnExactDayDAO.getById(shiftOnExactDay.getId());

        shiftOnExactDay.setUserId(shiftOnExactDay2.getUserId());
        shiftOnExactDay.setDate(shiftOnExactDay2.getDate());
        shiftOnExactDay.getShift().setId(shiftOnExactDay2.getShift().getId());

        shiftOnExactDayDAO.update(shiftOnExactDay);

        ShiftOnExactDay shiftFromDB = shiftOnExactDayDAO.getById(shiftOnExactDay.getId());

        assertNotNull(shiftFromDB);
        assertEquals(shiftOnExactDay2.getUserId(), shiftFromDB.getUserId());
        assertEquals(shiftOnExactDay2.getDate(), shiftFromDB.getDate());
        assertEquals(shiftOnExactDay2.getShift().getId(), shiftFromDB.getShift().getId());
    }

    @Test
    public void testUpdateNotExisting()  {
        shiftOnExactDayDAO.update(shiftOnExactDay);
    }

    @Test
    public void testGetShiftOnExactDay()  {
        shiftOnExactDayDAO.create(shiftOnExactDay);
        shiftOnExactDayDAO.create(shiftOnExactDay2);

        ShiftOnExactDay shift = shiftOnExactDayDAO.getShiftOnExactDay(1, Date.valueOf("2015-08-15"));
        assertEquals(1, shift.getShift().getId());

        shift = shiftOnExactDayDAO.getShiftOnExactDay(2, Date.valueOf("2015-08-15"));
        // TODO exception should be thrown
        assertNull(shift);
    }

    @Test
    public void testSetShiftOnExactDay()  {
        shiftOnExactDayDAO.setShiftOnExactDay(shiftOnExactDay);

        ShiftOnExactDay shiftOnExactDayFromDB = shiftOnExactDayDAO.getById(shiftOnExactDay.getId());
        assertEquals(shiftOnExactDay.getShift().getId(), shiftOnExactDayFromDB.getShift().getId());

        shiftOnExactDay.getShift().setId(3);
        shiftOnExactDayDAO.setShiftOnExactDay(shiftOnExactDay);

        shiftOnExactDayFromDB = shiftOnExactDayDAO.getById(shiftOnExactDay.getId());
        assertEquals(3, shiftOnExactDayFromDB.getShift().getId());
    }

    @Test
    public void testGetShiftsOnExactDay()  {
        shiftOnExactDayDAO.create(shiftOnExactDay);
        shiftOnExactDayDAO.create(shiftOnExactDay2);

        List<ShiftOnExactDay> shiftsOnExactDays = shiftOnExactDayDAO.getShiftsOnExactDay(
                Date.valueOf("2015-08-15"),Date.valueOf("2015-08-25"));
        assertEquals(2, shiftsOnExactDays.size());

        shiftsOnExactDays = shiftOnExactDayDAO.getShiftsOnExactDay(
                Date.valueOf("2015-08-15"),Date.valueOf("2015-08-22"));
        assertEquals(1, shiftsOnExactDays.size());

        shiftsOnExactDays = shiftOnExactDayDAO.getShiftsOnExactDay(
                Date.valueOf("2015-08-15"),Date.valueOf("2015-08-15"));
        assertEquals(1, shiftsOnExactDays.size());

        shiftsOnExactDays = shiftOnExactDayDAO.getShiftsOnExactDay(
                Date.valueOf("2015-08-10"),Date.valueOf("2015-08-14"));
        assertEquals(0, shiftsOnExactDays.size());
    }

    private ShiftOnExactDay createShiftOnExactDay(long userId, Date date, long shiftId) {
        ShiftOnExactDay shiftOnExactDay = new ShiftOnExactDay();
        shiftOnExactDay.setUserId(userId);
        shiftOnExactDay.setDate(date);
        shiftOnExactDay.getShift().setId(shiftId);
        return shiftOnExactDay;
    }

}
