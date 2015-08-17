package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PatternShiftDAO;
import lv.javaguru.java2.database.ShiftDAO;
import lv.javaguru.java2.domain.PatternShift;
import lv.javaguru.java2.domain.Shift;
import lv.javaguru.java2.servlet.mvc.SpringConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
public class PatternShiftDAOImplTest {

    private final DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    @Autowired
    private PatternShiftDAO patternShiftDAO;

    @Autowired
    private ShiftDAO shiftDAO;

    private final int PATTERN_ID=1;

    private PatternShift patternShift;
    private PatternShift patternShift2;
    private PatternShift patternShift3;
    private Shift shift1;
    private Shift shift2;

    @Before
    public void init() throws DBException {
        databaseCleaner.cleanDatabase();
        shift1 = ShiftDAOImplTest.createShift("name", "07:00:00", "15:00:00");
        shift2 = ShiftDAOImplTest.createShift("name2", "07:00:00", "15:00:00");
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
        assertEquals(patternShift.getPatternId(), patternShiftFromDB.getPatternId());
        assertEquals(patternShift.getShift().getId(), patternShiftFromDB.getShift().getId());
        assertEquals(patternShift.getSeqNo(), patternShiftFromDB.getSeqNo());
    }


    @Test
    public void testMultiplePatternShiftCreation() throws DBException {
        patternShiftDAO.create(patternShift);
        patternShiftDAO.create(patternShift2);
        patternShiftDAO.create(patternShift3);
        List<PatternShift> patternShifts = patternShiftDAO.getAll(PATTERN_ID);
        assertEquals(2, patternShifts.size());

        patternShifts = patternShiftDAO.getAll();
        assertEquals(3, patternShifts.size());
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

    @Test
    public void testUpdate() throws DBException {
        patternShiftDAO.create(patternShift);

        patternShift = patternShiftDAO.getById(patternShift.getId());
        patternShift.setPatternId(patternShift2.getPatternId());
        patternShift.getShift().setId(patternShift2.getShift().getId());
        patternShift.setSeqNo(patternShift2.getSeqNo());

        patternShiftDAO.update(patternShift);

        PatternShift patternShiftFromDB = patternShiftDAO.getById(patternShift.getId());

        assertNotNull(patternShiftFromDB);
        assertEquals(patternShift2.getPatternId(), patternShiftFromDB.getPatternId());
        assertEquals(patternShift2.getShift().getId(), patternShiftFromDB.getShift().getId());
        assertEquals(patternShift2.getSeqNo(), patternShiftFromDB.getSeqNo());
        assertEquals(patternShift2.getShift().getName(), patternShiftFromDB.getShift().getName());
    }


    private PatternShift createPatternShift(long patternId, long shiftId, int seqNo) {
        PatternShift patternShift = new PatternShift();
        patternShift.setPatternId(patternId);
        patternShift.getShift().setId(shiftId);
        patternShift.setSeqNo(seqNo);
        return patternShift;
    }

}