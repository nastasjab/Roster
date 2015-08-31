package lv.javaguru.java2.database;

import lv.javaguru.java2.database.shift.ShiftDAO;
import lv.javaguru.java2.domain.shift.Shift;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShiftDAOImplTest extends GenericSpringTest{
    @Autowired
    private ShiftDAO shiftDAO;

    private Shift shift;
    private Shift shift2;

    @Before
    public void init()  {
        super.init();
        shift = createShift("1", "07:00:00", "15:00:00");
        shift2 = createShift("2", "15:00:00", "23:00:00");
    }

    @Test
    public void testCreate()  {
        shiftDAO.create(shift);

        Shift shiftFromDB = shiftDAO.getById(shift.getId());
        assertNotNull(shiftFromDB);
        assertEquals(shift.getId(), shiftFromDB.getId());
        assertEquals(shift.getName(), shiftFromDB.getName());
        assertEquals(shift.getShiftStarts(), shiftFromDB.getShiftStarts());
        assertEquals(shift.getShiftEnds(), shiftFromDB.getShiftEnds());
    }

    @Test
    public void testMultipleUserCreation()  {
        shiftDAO.create(shift);
        shiftDAO.create(shift2);
        List<Shift> shifts = shiftDAO.getAll();
        assertEquals(2, shifts.size());
    }

    @Test
    public void testDelete()  {
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

    @Test (expected = IllegalArgumentException.class)
    public void testDeleteNotExisting()  {
        shiftDAO.delete(-1);
    }

    @Test
    public void testUpdate()  {
        shiftDAO.create(shift);

        shift = shiftDAO.getById(shift.getId());

        shift.setName(shift2.getName());
        shift.setShiftStarts(shift2.getShiftStarts());
        shift.setShiftEnds(shift2.getShiftEnds());

        shiftDAO.update(shift);

        Shift shiftFromDB = shiftDAO.getById(shift.getId());

        assertNotNull(shiftFromDB);
        assertEquals(shift2.getName(), shiftFromDB.getName());
        assertEquals(shift2.getShiftStarts(), shiftFromDB.getShiftStarts());
        assertEquals(shift2.getShiftEnds(), shiftFromDB.getShiftEnds());
    }

    public static Shift createShift(String name, String shiftStarts, String shiftEnds) {
        Shift shift = new Shift();
        shift.setName(name);
        shift.setShiftStarts(shiftStarts);
        shift.setShiftEnds(shiftEnds);
        return shift;
    }

}
