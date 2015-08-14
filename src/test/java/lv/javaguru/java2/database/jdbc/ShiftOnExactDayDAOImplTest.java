package lv.javaguru.java2.database.jdbc;


import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.ShiftOnExactDay;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Deprecated
public class ShiftOnExactDayDAOImplTest {


    private final DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    private final ShiftOnExactDayDAOImpl shiftOnExactDayDAO = new ShiftOnExactDayDAOImpl();

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
        assertEquals(shiftOnExactDay.getShiftId(), shiftOnExactDayFromDB.getShiftId());
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
    public void testDeleteNotExisting() throws DBException {
        shiftOnExactDayDAO.delete(-1);
    }

    @Test
    public void testUpdate() throws DBException {
        shiftOnExactDayDAO.create(shiftOnExactDay);

        shiftOnExactDay = shiftOnExactDayDAO.getById(shiftOnExactDay.getId());
        shiftOnExactDay2.setId(shiftOnExactDay.getId());

        shiftOnExactDayDAO.update(shiftOnExactDay2);

        ShiftOnExactDay shiftFromDB = shiftOnExactDayDAO.getById(shiftOnExactDay2.getId());

        assertNotNull(shiftFromDB);
        assertEquals(shiftOnExactDay2.getId(), shiftFromDB.getId());
        assertEquals(shiftOnExactDay2.getUserId(), shiftFromDB.getUserId());
        assertEquals(shiftOnExactDay2.getDate(), shiftFromDB.getDate());
        assertEquals(shiftOnExactDay2.getShiftId(), shiftFromDB.getShiftId());
    }

    @Test
    public void testUpdateNotExisting() throws DBException {
        shiftOnExactDayDAO.update(shiftOnExactDay);
    }

    private ShiftOnExactDay createShiftOnExactDay(long userId, Date date, long shiftId) {
        ShiftOnExactDay shiftOnExactDay = new ShiftOnExactDay();
        shiftOnExactDay.setUserId(userId);
        shiftOnExactDay.setDate(date);
        shiftOnExactDay.setShiftId(shiftId);
        return shiftOnExactDay;
    }

}
