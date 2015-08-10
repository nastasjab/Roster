package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.ShiftPattern;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ShiftPatternDAOImplTest {

    private final DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    private final ShiftPatternDAOImpl shiftPatternDAO = new ShiftPatternDAOImpl();

    private ShiftPattern shiftPattern;
    private ShiftPattern shiftPattern2;

    @Before
    public void init() throws DBException {
        databaseCleaner.cleanDatabase();
        shiftPattern = createShiftPattern(1,1,1);
        shiftPattern2 = createShiftPattern(1,2,2);
    }

    @Test
    public void testCreate() throws DBException {
        shiftPatternDAO.create(shiftPattern);

        ShiftPattern shiftPatternFromDB = shiftPatternDAO.getById(shiftPattern.getId());
        assertNotNull(shiftPatternFromDB);
        assertEquals(shiftPattern.getId(), shiftPatternFromDB.getId());
        assertEquals(shiftPattern.getPatternId(), shiftPatternFromDB.getPatternId());
        assertEquals(shiftPattern.getShiftId(), shiftPatternFromDB.getShiftId());
        assertEquals(shiftPattern.getSeqNo(), shiftPatternFromDB.getSeqNo());
    }


    @Test
    public void testMultipleShiftPatternCreation() throws DBException {
        shiftPatternDAO.create(shiftPattern);
        shiftPatternDAO.create(shiftPattern2);
        List<ShiftPattern> shiftPatterns = shiftPatternDAO.getAll(1);
        assertEquals(2, shiftPatterns.size());
    }


    @Test
     public void testDelete() throws DBException {
        shiftPatternDAO.create(shiftPattern);
        shiftPatternDAO.create(shiftPattern2);
        List<ShiftPattern> shiftPatterns = shiftPatternDAO.getAll(1);
        assertEquals(2, shiftPatterns.size());

        shiftPatternDAO.delete(shiftPattern.getId());
        shiftPatterns = shiftPatternDAO.getAll(1);
        assertEquals(1, shiftPatterns.size());

        shiftPatternDAO.delete(shiftPattern2.getId());
        shiftPatterns = shiftPatternDAO.getAll(1);
        assertEquals(0, shiftPatterns.size());
    }

    @Test
    public void testDeleteNotExisting() throws DBException {
        shiftPatternDAO.delete(-1);
    }

    @Test
    public void testUpdate() throws DBException {
        shiftPatternDAO.create(shiftPattern);

        shiftPattern = shiftPatternDAO.getById(shiftPattern.getId());
        shiftPattern2.setId(shiftPattern.getId());

        shiftPatternDAO.update(shiftPattern2);

        ShiftPattern shiftPatternFromDB = shiftPatternDAO.getById(shiftPattern2.getId());

        assertNotNull(shiftPatternFromDB);
        assertEquals(shiftPattern2.getPatternId(), shiftPatternFromDB.getPatternId());
        assertEquals(shiftPattern2.getShiftId(), shiftPatternFromDB.getShiftId());
        assertEquals(shiftPattern2.getSeqNo(), shiftPatternFromDB.getSeqNo());
    }

    @Test
    public void testUpdateNotExisting() throws DBException {
        shiftPatternDAO.update(shiftPattern);
    }

    private ShiftPattern createShiftPattern(long patternId, long shiftId, int seqNo) {
        ShiftPattern shiftPattern = new ShiftPattern();
        shiftPattern.setPatternId(patternId);
        shiftPattern.setShiftId(shiftId);
        shiftPattern.setSeqNo(seqNo);
        return shiftPattern;
    }

}