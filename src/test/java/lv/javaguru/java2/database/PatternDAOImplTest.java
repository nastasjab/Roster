package lv.javaguru.java2.database;

import lv.javaguru.java2.GenericSpringTest;
import lv.javaguru.java2.database.pattern.PatternDAO;
import lv.javaguru.java2.domain.pattern.Pattern;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class PatternDAOImplTest extends GenericSpringTest {

    @Autowired
    private PatternDAO patternDAO;

    private Pattern pattern;
    private Pattern pattern2;

    @Before
    public void init() {
        pattern = createPattern("pattern1ssss");
        pattern2 = createPattern("pattern2ssss");
    }

    @Test
    public void testCreate()  {
        patternDAO.create(pattern);

        Pattern patternFromDB = patternDAO.getById(pattern.getId());
        assertNotNull(patternFromDB);
        assertEquals(pattern.getId(), patternFromDB.getId());
        assertEquals(pattern.getName(), patternFromDB.getName());
    }

    @Test
    public void testMultiplePatternCreation()  {
        List<Pattern> patterns = patternDAO.getAll();
        int patternCount = patterns==null ? 0 : patterns.size();

        patternDAO.create(pattern);
        patternDAO.create(pattern2);
        patterns = patternDAO.getAll();
        assertEquals(2, patterns.size()-patternCount);
    }

    @Test
     public void testDelete() {
        List<Pattern> patterns = patternDAO.getAll();
        int patternCount = patterns==null ? 0 : patterns.size();

        patternDAO.create(pattern);
        patternDAO.create(pattern2);
        patterns = patternDAO.getAll();
        assertEquals(2, patterns.size()-patternCount);

        patternDAO.delete(pattern.getId());
        patterns = patternDAO.getAll();
        assertEquals(1, patterns.size() - patternCount);

        patternDAO.delete(pattern2.getId());
        patterns = patternDAO.getAll();
        assertEquals(0, patterns.size()-patternCount);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDeleteNotExisting()  {
        patternDAO.delete(-1);
    }

    @Test
    public void testUpdate()  {
        patternDAO.create(pattern);

        pattern = patternDAO.getById(pattern.getId());
        pattern.setName(pattern2.getName());

        patternDAO.update(pattern);

        Pattern patternFromDB = patternDAO.getById(pattern.getId());

        assertNotNull(patternFromDB);
        assertEquals(pattern2.getName(), patternFromDB.getName());
    }


    @Test
    public void testGetByObjectName()  {
        patternDAO.create(pattern);

        Pattern patternFromDB = patternDAO.getByObjectName(pattern.getName());
        assertTrue(patternFromDB.getName().equals(pattern.getName()));

        patternDAO.delete(pattern.getId());
        assertNull(patternDAO.getByObjectName(pattern.getName()));
    }

    public static Pattern createPattern(String name) {
        Pattern pattern = new Pattern();
        pattern.setName(name);
        return pattern;
    }

}