package lv.javaguru.java2.database;

import lv.javaguru.java2.GenericSpringTest;
import lv.javaguru.java2.database.pattern.PatternDAO;
import lv.javaguru.java2.database.pattern.PatternShiftDAO;
import lv.javaguru.java2.database.shift.ShiftDAO;
import lv.javaguru.java2.domain.pattern.Pattern;
import lv.javaguru.java2.domain.pattern.PatternShift;
import lv.javaguru.java2.domain.shift.Shift;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import static lv.javaguru.java2.domain.pattern.PatternBuilder.createPattern;
import static lv.javaguru.java2.domain.pattern.PatternShiftBuilder.createPatternShift;
import static lv.javaguru.java2.domain.shift.ShiftBuilder.createShift;
import static org.junit.Assert.*;


public class PatternShiftDAOImplTest extends GenericSpringTest {
    @Autowired
    private PatternShiftDAO patternShiftDAO;

    @Autowired
    private ShiftDAO shiftDAO;

    @Autowired
    private PatternDAO patternDAO;

    private long PATTERN_ID;
    private long PATTERN_ID2;

    private PatternShift patternShift;
    private PatternShift patternShift2;
    private PatternShift patternShift3;

    @Before
    public void init()  {
        Shift shift1 = createShift()
                .withName("shift#name").withShiftStarts("07:00:00").withShiftEnds("15:00:00")
                .build();
        Shift shift2 = createShift()
                .withName("shift#name2").withShiftStarts("07:00:00").withShiftEnds("15:00:00")
                .build();
        shiftDAO.create(shift1);
        shiftDAO.create(shift2);

        Pattern pattern  = createPattern().withName("shift#pattern1").build();
        Pattern pattern2 = createPattern().withName("shift#pattern2").build();
        patternDAO.create(pattern);
        patternDAO.create(pattern2);

        PATTERN_ID = pattern.getId();
        PATTERN_ID2 = pattern2.getId();

        patternShift = createPatternShift()
                .withPatternId(PATTERN_ID).withShiftId(shift1.getId()).withSeqNo(1).build();
        patternShift2 = createPatternShift()
                .withPatternId(PATTERN_ID).withShiftId(shift2.getId()).withSeqNo(2).build();
        patternShift3 = createPatternShift()
                .withPatternId(PATTERN_ID2).withShiftId(shift1.getId()).withSeqNo(2).build();
    }

    @Test
    public void testCreate()  {
        patternShiftDAO.create(patternShift);

        PatternShift patternShiftFromDB = patternShiftDAO.getById(patternShift.getId());

        assertNotNull(patternShiftFromDB);
        assertEquals(patternShift.getId(), patternShiftFromDB.getId());
        assertEquals(patternShift.getPattern(), patternShiftFromDB.getPattern());
        assertEquals(patternShift.getShift().getId(), patternShiftFromDB.getShift().getId());
        assertEquals(patternShift.getSeqNo(), patternShiftFromDB.getSeqNo());
    }

    @Test
    public void testMultiplePatternShiftCreation()  {
        List<PatternShift>  patternShifts = patternShiftDAO.getAll();
        int patternShiftCount = patternShifts==null ? 0 : patternShifts.size();

        patternShiftDAO.create(patternShift);
        patternShiftDAO.create(patternShift3);
        patternShifts = patternShiftDAO.getAll();
        assertEquals(2, patternShifts.size()-patternShiftCount);
    }

    @Test
    public void testGetAllByPatternId()  {
        patternShiftDAO.create(patternShift);
        patternShiftDAO.create(patternShift2);
        patternShiftDAO.create(patternShift3);
        List<PatternShift> patternShifts = patternShiftDAO.getAll(PATTERN_ID);
        assertEquals(2, patternShifts.size());
    }

    @Test
    public void testNextSeqNo()  {
        assertEquals(1, patternShiftDAO.getNextSequenceNo(-1));

        patternShiftDAO.create(patternShift);
        patternShiftDAO.create(patternShift2);

        assertEquals(3, patternShiftDAO.getNextSequenceNo(PATTERN_ID));
    }


    @Test
     public void testDelete()  {
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
    public void testDeleteNotExisting()  {
        patternShiftDAO.delete(-1);
    }

    @Test
    public void testDeleteByPatternId()  {
        List<PatternShift>  patternShifts = patternShiftDAO.getAll();
        int patternShiftCount = patternShifts==null ? 0 : patternShifts.size();

        patternShiftDAO.create(patternShift);
        patternShiftDAO.create(patternShift2);
        patternShiftDAO.create(patternShift3);
        patternShifts = patternShiftDAO.getAll(PATTERN_ID);
        assertEquals(2, patternShifts.size());

        patternShifts = patternShiftDAO.getAll();
        assertEquals(3, patternShifts.size() - patternShiftCount);

        patternShiftDAO.deleteByPatternId(PATTERN_ID);
        patternShifts = patternShiftDAO.getAll(PATTERN_ID);
        assertEquals(0, patternShifts.size());

        patternShifts = patternShiftDAO.getAll();
        assertEquals(1, patternShifts.size()-patternShiftCount);
    }

    @Test
    public void testUpdate()  {
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
}