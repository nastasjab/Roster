package lv.javaguru.java2.database.jdbc;


import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.Shift;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShiftDAOImplTest {


    private final DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    private final ShiftDAOImpl shiftDAO = new ShiftDAOImpl();

    private Shift shift;
    private Shift shift2;

    @Before
    public void init() throws DBException {
        databaseCleaner.cleanDatabase();
        shift = createShift("1", "07:00:00", "15:00:00");
        shift2 = createShift("2", "15:00:00", "23:00:00");
    }

    @Test
    public void testCreate() throws DBException {
        shiftDAO.create(shift);

        Shift shiftFromDB = shiftDAO.getById(shift.getId());
        assertNotNull(shiftFromDB);
        assertEquals(shift.getId(), shiftFromDB.getId());
        assertEquals(shift.getName(), shiftFromDB.getName());
        assertEquals(shift.getShiftStarts(), shiftFromDB.getShiftStarts());
        assertEquals(shift.getShiftEnds(), shiftFromDB.getShiftEnds());
    }

    @Test
    public void testMultipleUserCreation() throws DBException {
        shiftDAO.create(shift);
        shiftDAO.create(shift2);
        List<Shift> shifts = shiftDAO.getAll();
        assertEquals(2, shifts.size());
    }

    @Test
    public void testDelete() throws DBException {
        shiftDAO.create(shift);
        shiftDAO.create(shift2);
        List<Shift> shifts = shiftDAO.getAll();
        assertEquals(2, shifts.size());

        shiftDAO.delete(shift.getId());
        shifts = shiftDAO.getAll();
        assertEquals(1, shifts.size());

        shiftDAO.delete(shift2.getId());
        shifts = shiftDAO.getAll();
        assertEquals(0, shifts.size());
    }

    @Test
    public void testDeleteNotExisting() throws DBException {
        shiftDAO.delete(-1);
    }

    @Test
    public void testUpdate() throws DBException {
        shiftDAO.create(shift);

        shift = shiftDAO.getById(shift.getId());
        shift2.setId(shift.getId());

        shiftDAO.update(shift2);

        Shift shiftFromDB = shiftDAO.getById(shift2.getId());

        assertNotNull(shiftFromDB);
        assertEquals(shift2.getId(), shiftFromDB.getId());
        assertEquals(shift2.getName(), shiftFromDB.getName());
        assertEquals(shift2.getShiftStarts(), shiftFromDB.getShiftStarts());
        assertEquals(shift2.getShiftEnds(), shiftFromDB.getShiftEnds());
    }

    @Test
    public void testUpdateNotExisting() throws DBException {
        shiftDAO.update(shift);
    }

    private Shift createShift(String name, String shiftStarts, String shiftEnds) {
        Shift shift = new Shift();
        shift.setName(name);
        shift.setShiftStarts(shiftStarts);
        shift.setShiftEnds(shiftEnds);
        return shift;
    }

}
