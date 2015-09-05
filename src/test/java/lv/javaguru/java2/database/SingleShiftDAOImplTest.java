package lv.javaguru.java2.database;

import lv.javaguru.java2.GenericSpringTest;
import lv.javaguru.java2.database.roster.SingleShiftDAO;
import lv.javaguru.java2.domain.roster.SingleShift;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;


public class SingleShiftDAOImplTest extends GenericSpringTest {
    @Autowired
    private SingleShiftDAO singleShiftDAO;

    private SingleShift singleShift;
    private SingleShift singleShift2;

    @Before
    public void init()  {
        singleShift = createShiftOnExactDay(1, Date.valueOf("2015-08-15"), 1);
        singleShift2 = createShiftOnExactDay(2, Date.valueOf("2015-08-25"), 2);
    }

    @Test
    public void testCreate()  {
        singleShiftDAO.create(singleShift);

        SingleShift singleShiftFromDB = singleShiftDAO.getById(singleShift.getId());
        assertNotNull(singleShiftFromDB);
        assertEquals(singleShift.getId(), singleShiftFromDB.getId());
        assertEquals(singleShift.getUserId(), singleShiftFromDB.getUserId());
        assertEquals(singleShift.getDate(), singleShiftFromDB.getDate());
        assertEquals(singleShift.getShift().getId(), singleShiftFromDB.getShift().getId());

        singleShiftFromDB = singleShiftDAO.getById(1000000);
        assertNull(singleShiftFromDB);
    }

    @Test
    public void testMultipleUserCreation()  {
        List<SingleShift> singleShifts = singleShiftDAO.getAll();
        int shiftOnExactDaysCount = singleShifts ==null ? 0 : singleShifts.size();

        singleShiftDAO.create(singleShift);
        singleShiftDAO.create(singleShift2);
        singleShifts = singleShiftDAO.getAll();
        assertEquals(2, singleShifts.size()-shiftOnExactDaysCount);
    }

    @Test
    public void testDelete()  {
        List<SingleShift> shiftsOnExactDays = singleShiftDAO.getAll();
        int shiftOnExactDaysCount = shiftsOnExactDays==null ? 0 : shiftsOnExactDays.size();

        singleShiftDAO.create(singleShift);
        singleShiftDAO.create(singleShift2);
        shiftsOnExactDays = singleShiftDAO.getAll();
        assertEquals(2, shiftsOnExactDays.size()-shiftOnExactDaysCount);

        singleShiftDAO.delete(singleShift.getId());
        shiftsOnExactDays = singleShiftDAO.getAll();
        assertEquals(1, shiftsOnExactDays.size()-shiftOnExactDaysCount);

        singleShiftDAO.delete(singleShift2.getId());
        shiftsOnExactDays = singleShiftDAO.getAll();
        assertEquals(0, shiftsOnExactDays.size()-shiftOnExactDaysCount);
    }

    @Test
    public void testUpdate()  {
        singleShiftDAO.create(singleShift);

        singleShift = singleShiftDAO.getById(singleShift.getId());

        singleShift.setUserId(singleShift2.getUserId());
        singleShift.setDate(singleShift2.getDate());
        singleShift.getShift().setId(singleShift2.getShift().getId());

        singleShiftDAO.update(singleShift);

        SingleShift shiftFromDB = singleShiftDAO.getById(singleShift.getId());

        assertNotNull(shiftFromDB);
        assertEquals(singleShift2.getUserId(), shiftFromDB.getUserId());
        assertEquals(singleShift2.getDate(), shiftFromDB.getDate());
        assertEquals(singleShift2.getShift().getId(), shiftFromDB.getShift().getId());
    }

    @Test
    public void testUpdateNotExisting()  {
        singleShiftDAO.update(singleShift);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testGetShiftOnExactDay()  {
        singleShiftDAO.create(singleShift);
        singleShiftDAO.create(singleShift2);

        SingleShift shift = singleShiftDAO.getShiftOnExactDay(1, Date.valueOf("2015-08-15"));
        assertEquals(1, shift.getShift().getId());

        shift = singleShiftDAO.getShiftOnExactDay(2, Date.valueOf("2015-08-15"));
    }

    @Test
    public void testSetShiftOnExactDay()  {
        singleShiftDAO.setShiftOnExactDay(singleShift);

        SingleShift singleShiftFromDB = singleShiftDAO.getById(singleShift.getId());
        assertEquals(singleShift.getShift().getId(), singleShiftFromDB.getShift().getId());

        singleShift.getShift().setId(3);
        singleShiftDAO.setShiftOnExactDay(singleShift);

        singleShiftFromDB = singleShiftDAO.getById(singleShift.getId());
        assertEquals(3, singleShiftFromDB.getShift().getId());
    }

    @Test
    public void testGetShiftsOnExactDay()  {
        singleShiftDAO.create(singleShift);
        singleShiftDAO.create(singleShift2);

        List<SingleShift> shiftsOnExactDays = singleShiftDAO.getShiftsOnExactDay(
                Date.valueOf("2015-08-15"),Date.valueOf("2015-08-25"));
        assertEquals(2, shiftsOnExactDays.size());

        shiftsOnExactDays = singleShiftDAO.getShiftsOnExactDay(
                Date.valueOf("2015-08-15"),Date.valueOf("2015-08-22"));
        assertEquals(1, shiftsOnExactDays.size());

        shiftsOnExactDays = singleShiftDAO.getShiftsOnExactDay(
                Date.valueOf("2015-08-15"),Date.valueOf("2015-08-15"));
        assertEquals(1, shiftsOnExactDays.size());

        shiftsOnExactDays = singleShiftDAO.getShiftsOnExactDay(
                Date.valueOf("2015-08-10"),Date.valueOf("2015-08-14"));
        assertEquals(0, shiftsOnExactDays.size());
    }

    private SingleShift createShiftOnExactDay(long userId, Date date, long shiftId) {
        SingleShift singleShift = new SingleShift();
        singleShift.setUserId(userId);
        singleShift.setDate(date);
        singleShift.getShift().setId(shiftId);
        return singleShift;
    }

}
