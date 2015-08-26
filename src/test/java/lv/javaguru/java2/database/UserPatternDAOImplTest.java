package lv.javaguru.java2.database;

import lv.javaguru.java2.database.hibernate.UserPatternDAOImpl;
import lv.javaguru.java2.domain.UserPattern;
import lv.javaguru.java2.servlet.mvc.SpringConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
public class UserPatternDAOImplTest {

    private final DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    @Autowired
    private UserPatternDAO userPatternDAO;// = new UserPatternDAOImpl();

    private UserPattern userPattern;
    private UserPattern userPattern2;

    @Before
    public void init() throws DBException {
        databaseCleaner.cleanDatabase();
        userPattern = createUserPattern(1, 1, Date.valueOf("2015-07-01"), Date.valueOf("2015-07-31"), 1);
        userPattern2 = createUserPattern(2, 2, Date.valueOf("2015-08-01"), Date.valueOf("2015-08-31"), 2);
    }

    @Test
    public void testCreate() throws DBException {
        userPatternDAO.create(userPattern);

        UserPattern userPatternFromDB = userPatternDAO.getById(userPattern.getId());
        assertNotNull(userPatternFromDB);
        assertEquals(userPattern.getId(), userPatternFromDB.getId());
        assertEquals(userPattern.getUserId(), userPatternFromDB.getUserId());
        assertEquals(userPattern.getPattern(), userPatternFromDB.getPattern());
        assertEquals(userPattern.getStartDay(), userPatternFromDB.getStartDay());
        assertEquals(userPattern.getEndDay(), userPatternFromDB.getEndDay());
        assertEquals(userPattern.getPatternStartDay(), userPatternFromDB.getPatternStartDay());
    }

    @Test
    public void testMultipleUserCreation() throws DBException {
        userPatternDAO.create(userPattern);
        userPatternDAO.create(userPattern2);
        List<UserPattern> userPatterns = userPatternDAO.getAll();
        assertEquals(2, userPatterns.size());
    }

    @Test
     public void testDelete() throws DBException {
        userPatternDAO.create(userPattern);
        userPatternDAO.create(userPattern2);
        List<UserPattern> userPatterns = userPatternDAO.getAll();
        assertEquals(2, userPatterns.size());

        userPatternDAO.delete(userPattern.getId());
        userPatterns = userPatternDAO.getAll();
        assertEquals(1, userPatterns.size());

        userPatternDAO.delete(userPattern2.getId());
        userPatterns = userPatternDAO.getAll();
        assertEquals(0, userPatterns.size());
    }

    @Test
    public void testUpdate() throws DBException {
        userPatternDAO.create(userPattern);

        userPattern = userPatternDAO.getById(userPattern.getId());
        userPattern2.setId(userPattern.getId());

        userPatternDAO.update(userPattern2);

        UserPattern userFromDB = userPatternDAO.getById(userPattern2.getId());

        assertNotNull(userFromDB);
        assertEquals(userPattern2.getUserId(), userFromDB.getUserId());
        assertEquals(userPattern2.getPattern(), userFromDB.getPattern());
        assertEquals(userPattern2.getStartDay(), userFromDB.getStartDay());
        assertEquals(userPattern2.getEndDay(), userFromDB.getEndDay());
        assertEquals(userPattern2.getPatternStartDay(), userFromDB.getPatternStartDay());
    }

    private UserPattern createUserPattern(long userId, long shiftPatternId,
                            Date startDay, Date endDay, int shiftPatternStartDay) {
        UserPattern userPattern = new UserPattern();
        userPattern.setUserId(userId);
        userPattern.getPattern().setId(shiftPatternId);
        userPattern.setStartDay(startDay);
        userPattern.setEndDay(endDay);
        userPattern.setPatternStartDay(shiftPatternStartDay);
        return userPattern;
    }

    //TODO
    // tests for:
    // - getByUserId
    // - getByDateFrame

}