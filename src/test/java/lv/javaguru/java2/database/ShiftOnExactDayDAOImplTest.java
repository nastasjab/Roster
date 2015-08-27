package lv.javaguru.java2.database;


import lv.javaguru.java2.domain.ShiftOnExactDay;
import lv.javaguru.java2.servlet.mvc.SpringConfig;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
public class ShiftOnExactDayDAOImplTest {


    private final DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    @Autowired
    private  ShiftOnExactDayDAO shiftOnExactDayDAO;

    private ShiftOnExactDay shiftOnExactDay;
    private ShiftOnExactDay shiftOnExactDay2;

    @Before
    public void init() throws DBException {
        databaseCleaner.cleanDatabase();
        shiftOnExactDay = createShiftOnExactDay(1, Date.valueOf("2015-08-15"), 1);
        shiftOnExactDay2 = createShiftOnExactDay(2, Date.valueOf("2015-08-25"), 2);
    }

    @Test
    public void testCreate() throws DBException {
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
    public void testMultipleUserCreation() throws DBException {
        shiftOnExactDayDAO.create(shiftOnExactDay);
        shiftOnExactDayDAO.create(shiftOnExactDay2);
        List<ShiftOnExactDay> userPatterns = shiftOnExactDayDAO.getAll();
        assertEquals(2, userPatterns.size());
    }

    @Test
    public void testDelete() throws DBException {
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
    public void testUpdate() throws DBException {
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
    public void testUpdateNotExisting() throws DBException {
        shiftOnExactDayDAO.update(shiftOnExactDay);
    }

    @Test
    public void testGetShiftOnExactDay() throws DBException {
        shiftOnExactDayDAO.create(shiftOnExactDay);
        shiftOnExactDayDAO.create(shiftOnExactDay2);

        ShiftOnExactDay shift = shiftOnExactDayDAO.getShiftOnExactDay(1, Date.valueOf("2015-08-15"));
        assertEquals(1, shift.getShift().getId());

        shift = shiftOnExactDayDAO.getShiftOnExactDay(2, Date.valueOf("2015-08-15"));
        assertNull(shift);
    }

    @Test
    public void testSetShiftOnExactDay() throws DBException {
        shiftOnExactDayDAO.setShiftOnExactDay(shiftOnExactDay);

        ShiftOnExactDay shiftOnExactDayFromDB = shiftOnExactDayDAO.getById(shiftOnExactDay.getId());
        assertEquals(shiftOnExactDay.getShift().getId(), shiftOnExactDayFromDB.getShift().getId());

        shiftOnExactDay.getShift().setId(3);
        shiftOnExactDayDAO.setShiftOnExactDay(shiftOnExactDay);

        shiftOnExactDayFromDB = shiftOnExactDayDAO.getById(shiftOnExactDay.getId());
        assertEquals(3, shiftOnExactDayFromDB.getShift().getId());
    }

    @Test
    public void testGetShiftsOnExactDay() throws DBException {
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
