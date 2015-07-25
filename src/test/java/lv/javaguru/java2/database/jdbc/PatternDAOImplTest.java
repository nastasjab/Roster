package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.Pattern;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class PatternDAOImplTest {

    private final DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    private final PatternDAOImpl patternDAO = new PatternDAOImpl();

    private Pattern pattern;
    private Pattern pattern2;

    @Before
    public void init() throws DBException {
        databaseCleaner.cleanDatabase();
        pattern = createPattern("pattern1");
        pattern2 = createPattern("pattern2");
    }

    @Test
    public void testCreate() throws DBException {
        patternDAO.create(pattern);

        Pattern patternFromDB = patternDAO.getById(pattern.getId());
        assertNotNull(patternFromDB);
        assertEquals(pattern.getId(), patternFromDB.getId());
        assertEquals(pattern.getName(), patternFromDB.getName());
    }

    @Test
    public void testMultiplePatternCreation() throws DBException {
        patternDAO.create(pattern);
        patternDAO.create(pattern2);
        List<Pattern> patterns = patternDAO.getAll();
        assertEquals(2, patterns.size());
    }

    @Test
     public void testDelete() throws DBException {
        patternDAO.create(pattern);
        patternDAO.create(pattern2);
        List<Pattern> patterns = patternDAO.getAll();
        assertEquals(2, patterns.size());

        patternDAO.delete(pattern.getId());
        patterns = patternDAO.getAll();
        assertEquals(1, patterns.size());

        patternDAO.delete(pattern2.getId());
        patterns = patternDAO.getAll();
        assertEquals(0, patterns.size());
    }

    @Test
    public void testDeleteNotExisting() throws DBException {
        patternDAO.delete(-1);
    }

    @Test
    public void testUpdate() throws DBException {
        patternDAO.create(pattern);

        pattern = patternDAO.getById(pattern.getId());
        pattern2.setId(pattern.getId());

        patternDAO.update(pattern2);

        Pattern patternFromDB = patternDAO.getById(pattern2.getId());

        assertNotNull(patternFromDB);
        assertEquals(pattern2.getName(), patternFromDB.getName());
    }

    @Test
    public void testUpdateNotExisting() throws DBException {
        patternDAO.update(pattern);
    }

    private Pattern createPattern(String name) {
        Pattern pattern = new Pattern();
        pattern.setName(name);
        return pattern;
    }

}