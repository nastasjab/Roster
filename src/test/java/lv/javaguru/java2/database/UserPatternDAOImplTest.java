package lv.javaguru.java2.database;

import lv.javaguru.java2.GenericSpringTest;
import lv.javaguru.java2.database.user.UserPatternDAO;
import lv.javaguru.java2.domain.user.UserPattern;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.List;

import static lv.javaguru.java2.domain.pattern.PatternBuilder.createPattern;
import static lv.javaguru.java2.domain.user.UserPatternBuilder.createUserPattern;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserPatternDAOImplTest extends GenericSpringTest {
    @Autowired
    private UserPatternDAO userPatternDAO;

    private UserPattern userPattern;
    private UserPattern userPattern2;

    @Before
    public void init()  {
        userPattern = createUserPattern()
                .withUserId(1)
                .withPattern(createPattern().withId(1L).build())
                .withStartDay(Date.valueOf("2015-07-01"))
                .withEndDay(Date.valueOf("2015-07-31"))
                .withPatternStartDay(1)
                .build();
        userPattern2 = createUserPattern()
                .withUserId(2)
                .withPattern(createPattern().withId(2L).build())
                .withStartDay(Date.valueOf("2015-08-01"))
                .withEndDay(Date.valueOf("2015-08-31"))
                .withPatternStartDay(2)
                .build();
    }

    @Test
    public void testCreate()  {
        userPatternDAO.create(userPattern);

        UserPattern userPatternFromDB = userPatternDAO.getById(userPattern.getId());
        assertNotNull(userPatternFromDB);
        assertEquals(userPattern.getId(), userPatternFromDB.getId());
        assertEquals(userPattern.getUserId(), userPatternFromDB.getUserId());
        assertEquals(userPattern.getPattern().getId(), userPatternFromDB.getPattern().getId());
        assertEquals(userPattern.getStartDay(), userPatternFromDB.getStartDay());
        assertEquals(userPattern.getEndDay(), userPatternFromDB.getEndDay());
        assertEquals(userPattern.getPatternStartDay(), userPatternFromDB.getPatternStartDay());
    }

    @Test
    public void testMultipleUserCreation()  {
        List<UserPattern> userPatterns = userPatternDAO.getAll();
        int userPatternsCount = userPatterns==null ? 0 : userPatterns.size();

        userPatternDAO.create(userPattern);
        userPatternDAO.create(userPattern2);
        userPatterns = userPatternDAO.getAll();
        assertEquals(2, userPatterns.size()-userPatternsCount);
    }

    @Test
     public void testDelete()  {
        List<UserPattern> userPatterns = userPatternDAO.getAll();
        int userPatternsCount = userPatterns == null ? 0 : userPatterns.size();

        userPatternDAO.create(userPattern);
        userPatternDAO.create(userPattern2);
        assertEquals(2, userPatternDAO.getAll().size() - userPatternsCount);

        userPatternDAO.delete(userPattern.getId());
        assertEquals(1, userPatternDAO.getAll().size() - userPatternsCount);

        userPatternDAO.delete(userPattern2.getId());
        assertEquals(0, userPatternDAO.getAll().size() - userPatternsCount);
    }

    @Test
    public void testUpdate()  {
        userPatternDAO.create(userPattern);

        userPattern = userPatternDAO.getById(userPattern.getId());

        userPattern.setUserId(userPattern2.getUserId());
        userPattern.getPattern().setId(userPattern2.getPattern().getId());
        userPattern.setStartDay(userPattern2.getStartDay());
        userPattern.setEndDay(userPattern2.getEndDay());
        userPattern.setPatternStartDay(userPattern2.getPatternStartDay());

        userPatternDAO.update(userPattern);

        UserPattern userFromDB = userPatternDAO.getById(userPattern.getId());

        assertNotNull(userFromDB);
        assertEquals(userPattern2.getUserId(), userFromDB.getUserId());
        assertEquals(userPattern2.getPattern().getId(), userFromDB.getPattern().getId());
        assertEquals(userPattern2.getStartDay(), userFromDB.getStartDay());
        assertEquals(userPattern2.getEndDay(), userFromDB.getEndDay());
        assertEquals(userPattern2.getPatternStartDay(), userFromDB.getPatternStartDay());
    }

    @Test
    public void testGetByUserId()  {
        userPatternDAO.create(userPattern);

        userPattern = userPatternDAO.getById(userPattern.getId());

        userPattern.setUserId(userPattern2.getUserId());
        userPattern.setStartDay(userPattern2.getStartDay());
        userPattern.setEndDay(userPattern2.getEndDay());
        userPattern.setPatternStartDay(userPattern2.getPatternStartDay());
        userPattern.setPattern(userPattern2.getPattern());

        userPatternDAO.update(userPattern);

        UserPattern shiftFromDB = userPatternDAO.getById(userPattern.getId());

        assertNotNull(shiftFromDB);
        assertEquals(userPattern2.getUserId(), shiftFromDB.getUserId());
        assertEquals(userPattern2.getStartDay(), shiftFromDB.getStartDay());
        assertEquals(userPattern2.getEndDay(), shiftFromDB.getEndDay());
        assertEquals(userPattern2.getPatternStartDay(), shiftFromDB.getPatternStartDay());
        assertEquals(userPattern2.getPattern(), shiftFromDB.getPattern());
    }

    @Test
    public void testGetByDateFrame()  {
        List<UserPattern> userPatterns = userPatternDAO.getByDateFrame(Date.valueOf("2015-07-01"), Date.valueOf("2015-08-31"));
        int userPatternsCount1 = userPatterns==null ? 0 : userPatterns.size();
        userPatterns = userPatternDAO.getByDateFrame(Date.valueOf("2015-07-01"), Date.valueOf("2015-07-25"));
        int userPatternsCount2 = userPatterns==null ? 0 : userPatterns.size();
        userPatterns = userPatternDAO.getByDateFrame(Date.valueOf("2015-06-01"), Date.valueOf("2015-06-30"));
        int userPatternsCount3 = userPatterns==null ? 0 : userPatterns.size();

        userPatternDAO.create(userPattern);
        userPatternDAO.create(userPattern2);

        userPatterns = userPatternDAO.getByDateFrame(Date.valueOf("2015-07-01"), Date.valueOf("2015-08-31"));
        assertEquals(2, userPatterns.size()-userPatternsCount1);

        userPatterns = userPatternDAO.getByDateFrame(Date.valueOf("2015-07-01"), Date.valueOf("2015-07-25"));
        assertEquals(1, userPatterns.size()-userPatternsCount2);

        userPatterns = userPatternDAO.getByDateFrame(Date.valueOf("2015-06-01"), Date.valueOf("2015-06-30"));
        assertEquals(0, userPatterns.size()-userPatternsCount3);
    }

    @Test
    public void testGet()  {
        userPatternDAO.create(userPattern);

        UserPattern found = userPatternDAO.get(Date.valueOf("2015-07-02"), userPattern.getUserId());
        assertEquals(this.userPattern.getId(), found.getId());

        found = userPatternDAO.get(Date.valueOf("2015-07-01"), userPattern.getUserId());
        assertEquals(this.userPattern.getId(), found.getId());

        found = userPatternDAO.get(Date.valueOf("2015-07-31"), userPattern.getUserId());
        assertEquals(this.userPattern.getId(), found.getId());
    }
}