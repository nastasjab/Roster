package lv.javaguru.java2.database;

import lv.javaguru.java2.GenericSpringTest;
import lv.javaguru.java2.database.shift.ShiftDAO;
import lv.javaguru.java2.domain.shift.Shift;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class ShiftDAOImplTest extends GenericSpringTest {
    @Autowired
    private ShiftDAO shiftDAO;

    private Shift shift;
    private Shift shift2;

    @Before
    public void init()  {
        shift = createShift("ssssss1", "07:00:00", "15:00:00");
        shift2 = createShift("sssssss2", "15:00:00", "23:00:00");
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
        List<Shift> shifts = shiftDAO.getAll();
        int shiftCount = shifts==null ? 0 : shifts.size();

        shiftDAO.create(shift);
        shiftDAO.create(shift2);
        shifts = shiftDAO.getAll();
        assertEquals(2, shifts.size()-shiftCount);
    }

    @Test
    public void testDelete()  {
        List<Shift> shifts = shiftDAO.getAll();
        int shiftCount = shifts==null ? 0 : shifts.size();

        shiftDAO.create(shift);
        shiftDAO.create(shift2);
        shifts = shiftDAO.getAll();
        assertEquals(2, shifts.size()-shiftCount);

        shiftDAO.delete(shift.getId());
        shifts = shiftDAO.getAll();
        assertEquals(1, shifts.size() - shiftCount);

        shiftDAO.delete(shift2.getId());
        shifts = shiftDAO.getAll();
        assertEquals(0, shifts.size()-shiftCount);
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

    @Test
    public void testGetByObjectName()  {
        shiftDAO.create(shift);

        Shift shiftFromDB = shiftDAO.getByObjectName(shift.getName());
        assertTrue(shiftFromDB.getName().equals(shift.getName()));

        shiftDAO.delete(shift.getId());
        assertNull(shiftDAO.getByObjectName(shift.getName()));
    }

    public static Shift createShift(String name, String shiftStarts, String shiftEnds) {
        Shift shift = new Shift();
        shift.setName(name);
        shift.setShiftStarts(shiftStarts);
        shift.setShiftEnds(shiftEnds);
        return shift;
    }

}
