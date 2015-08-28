package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.PatternShift;
import lv.javaguru.java2.domain.Shift;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import static org.junit.Assert.*;


public class PatternShiftDAOImplTest extends  GenericSpringTest {
    @Autowired
    private PatternShiftDAO patternShiftDAO;

    @Autowired
    private ShiftDAO shiftDAO;

    private final int PATTERN_ID=1;

    private PatternShift patternShift;
    private PatternShift patternShift2;
    private PatternShift patternShift3;

    @Before
    public void init() throws DBException {
        super.init();
        Shift shift1 = ShiftDAOImplTest.createShift("name", "07:00:00", "15:00:00");
        Shift shift2 = ShiftDAOImplTest.createShift("name2", "07:00:00", "15:00:00");
        shiftDAO.create(shift1);
        shiftDAO.create(shift2);

        patternShift = createPatternShift(PATTERN_ID, shift1.getId(), 1);
        patternShift2 = createPatternShift(PATTERN_ID, shift2.getId(), 2);
        patternShift3 = createPatternShift(2, shift1.getId(), 2);
    }

    @Test
    public void testCreate() throws DBException {
        patternShiftDAO.create(patternShift);

        PatternShift patternShiftFromDB = patternShiftDAO.getById(patternShift.getId());

        assertNotNull(patternShiftFromDB);
        assertEquals(patternShift.getId(), patternShiftFromDB.getId());
        assertEquals(patternShift.getPattern(), patternShiftFromDB.getPattern());
        assertEquals(patternShift.getShift().getId(), patternShiftFromDB.getShift().getId());
        assertEquals(patternShift.getSeqNo(), patternShiftFromDB.getSeqNo());
    }

    @Test
    public void testMultiplePatternShiftCreation() throws DBException {
        patternShiftDAO.create(patternShift);
        patternShiftDAO.create(patternShift3);
        List<PatternShift>  patternShifts = patternShiftDAO.getAll();
        assertEquals(2, patternShifts.size());
    }

    @Test
    public void testGetAllByPatternId() throws DBException {
        patternShiftDAO.create(patternShift);
        patternShiftDAO.create(patternShift2);
        patternShiftDAO.create(patternShift3);
        List<PatternShift> patternShifts = patternShiftDAO.getAll(PATTERN_ID);
        assertEquals(2, patternShifts.size());
    }

    @Test
    public void testNextSeqNo() throws DBException {
        assertEquals(1, patternShiftDAO.getNextSequenceNo(-1));

        patternShiftDAO.create(patternShift);
        patternShiftDAO.create(patternShift2);

        assertEquals(3, patternShiftDAO.getNextSequenceNo(PATTERN_ID));
    }


    @Test
     public void testDelete() throws DBException {
        patternShiftDAO.create(patternShift);
        patternShiftDAO.create(patternShift2);
        List<PatternShift> patternShifts = patternShiftDAO.getAll(PATTERN_ID);
        assertEquals(2, patternShifts.size());

        patternShiftDAO.delete(patternShift.getId());
        patternShifts = patternShiftDAO.getAll(PATTERN_ID);
        assertEquals(1, patternShifts.size());

        patternShiftDAO.delete(patternShift2.getId());
        patternShifts = patternShiftDAO.getAll(PATTERN_ID);
        assertEquals(0, patternShifts.size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDeleteNotExisting() throws DBException {
        patternShiftDAO.delete(-1);
    }

    @Test
    public void testDeleteByPatternId() throws DBException {
        patternShiftDAO.create(patternShift);
        patternShiftDAO.create(patternShift2);
        patternShiftDAO.create(patternShift3);
        List<PatternShift> patternShifts = patternShiftDAO.getAll(PATTERN_ID);
        assertEquals(2, patternShifts.size());

        patternShifts = patternShiftDAO.getAll();
        assertEquals(3, patternShifts.size());

        patternShiftDAO.deleteByPatternId(PATTERN_ID);
        patternShifts = patternShiftDAO.getAll(PATTERN_ID);
        assertEquals(0, patternShifts.size());

        patternShifts = patternShiftDAO.getAll();
        assertEquals(1, patternShifts.size());
    }

    @Test
    public void testUpdate() throws DBException {
        patternShiftDAO.create(patternShift);

        patternShift = patternShiftDAO.getById(patternShift.getId());
        patternShift.setPattern(patternShift2.getPattern());
        patternShift.getShift().setId(patternShift2.getShift().getId());
        patternShift.setSeqNo(patternShift2.getSeqNo());

        patternShiftDAO.update(patternShift);

        PatternShift patternShiftFromDB = patternShiftDAO.getById(patternShift.getId());

        assertNotNull(patternShiftFromDB);
        assertEquals(patternShift2.getPattern(), patternShiftFromDB.getPattern());
        assertEquals(patternShift2.getShift().getId(), patternShiftFromDB.getShift().getId());
        assertEquals(patternShift2.getSeqNo(), patternShiftFromDB.getSeqNo());
        assertEquals(patternShift2.getShift().getName(), patternShiftFromDB.getShift().getName());
    }


    private PatternShift createPatternShift(long patternId, long shiftId, int seqNo) {
        PatternShift patternShift = new PatternShift();
        patternShift.getPattern().setId(patternId);
        patternShift.getShift().setId(shiftId);
        patternShift.setSeqNo(seqNo);
        return patternShift;
    }

}