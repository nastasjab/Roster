package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.Pattern;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class PatternDAOImplTest extends  GenericSpringTest{

    @Autowired
    private PatternDAO patternDAO;

    private Pattern pattern;
    private Pattern pattern2;

    @Before
    public void init() throws DBException {
        super.init();
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

    @Test (expected = IllegalArgumentException.class)
    public void testDeleteNotExisting() throws DBException {
        patternDAO.delete(-1);
    }

    @Test
    public void testUpdate() throws DBException {
        patternDAO.create(pattern);

        pattern = patternDAO.getById(pattern.getId());
        pattern.setName(pattern2.getName());

        patternDAO.update(pattern);

        Pattern patternFromDB = patternDAO.getById(pattern.getId());

        assertNotNull(patternFromDB);
        assertEquals(pattern2.getName(), patternFromDB.getName());
    }

    private Pattern createPattern(String name) {
        Pattern pattern = new Pattern();
        pattern.setName(name);
        return pattern;
    }

}