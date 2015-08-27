package lv.javaguru.java2.database;

import static org.junit.Assert.*;

import java.util.List;

import lv.javaguru.java2.servlet.mvc.SpringConfig;
import org.junit.Before;
import org.junit.Test;

import lv.javaguru.java2.domain.User;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@Transactional
public class UserDAOImplTest {

    private final DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    @Autowired
    private  UserDAO userDAO;

    private User user;
    private User user2;

    @Before
    public void init() throws DBException {
        databaseCleaner.cleanDatabase();
        user = createUser("user", "encryptedPassword", "U", "dima", "pavlov", "dima@pavlov.lv", "29295656");
        user2 = createUser("admin", "encryptedPassword2", "A", "ivan", "petrov", "ivan@petrov.lv", "29299898");
    }

    @Test
    public void testCreate() throws DBException {
        userDAO.create(user);

        User userFromDB = userDAO.getById(user.getId());
        assertNotNull(userFromDB);
        assertEquals(user.getId(), userFromDB.getId());
        assertEquals(user.getLogin(), userFromDB.getLogin());
        assertEquals(user.getPassword(), userFromDB.getPassword());
        assertEquals(user.getUserType(), userFromDB.getUserType());
        assertEquals(user.getFirstName(), userFromDB.getFirstName());
        assertEquals(user.getLastName(), userFromDB.getLastName());
        assertEquals(user.getEmail(), userFromDB.getEmail());
        assertEquals(user.getPhone(), userFromDB.getPhone());
    }

    @Test
    public void testMultipleUserCreation() throws DBException {
        userDAO.create(user);
        userDAO.create(user2);
        List<User> users = userDAO.getAll();
        assertEquals(2, users.size());
    }

    @Test
     public void testDelete() throws DBException {
        userDAO.create(user);
        userDAO.create(user2);
        List<User> users = userDAO.getAll();
        assertEquals(2, users.size());

        userDAO.delete(user.getId());
        users = userDAO.getAll();
        assertEquals(1, users.size());

        userDAO.delete(user2.getId());
        users = userDAO.getAll();
        assertEquals(0, users.size());
    }


    @Test
    public void testUpdate() throws DBException {
        userDAO.create(user);

        user = userDAO.getById(user.getId());

        user.setLogin(user2.getLogin());
        user.setPassword(user2.getPassword());
        user.setUserType(user2.getUserType());
        user.setFirstName(user2.getFirstName());
        user.setLastName(user2.getLastName());
        user.setEmail(user2.getEmail());
        user.setPhone(user2.getPhone());

        userDAO.update(user);

        User userFromDB = userDAO.getById(user.getId());

        assertNotNull(userFromDB);
        assertEquals(user2.getLogin(), userFromDB.getLogin());
        assertEquals(user2.getPassword(), userFromDB.getPassword());
        assertEquals(user2.getUserType(), userFromDB.getUserType());
        assertEquals(user2.getFirstName(), userFromDB.getFirstName());
        assertEquals(user2.getLastName(), userFromDB.getLastName());
        assertEquals(user2.getEmail(), userFromDB.getEmail());
        assertEquals(user2.getPhone(), userFromDB.getPhone());
    }

    private User createUser(String login, String password, String userType,
                            String firstName, String lastName, String email, String phone) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setUserType(userType);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhone(phone);
        return user;
    }

}