package lv.javaguru.java2.database;

import lv.javaguru.java2.servlet.mvc.SpringConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
public class GenericSpringTest {

    @Autowired
    public HelperDAO helperDAO;

    @Before
    public void init() throws DBException {
        DatabaseCleaner databaseCleaner = new DatabaseCleaner(helperDAO);
        databaseCleaner.cleanDatabase();
    }

    @Test
    public void fakeTest(){
    }
}
