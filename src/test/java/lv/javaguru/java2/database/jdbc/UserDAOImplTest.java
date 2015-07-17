package lv.javaguru.java2.database.jdbc;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.User;


public class UserDAOImplTest {

    private final DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    private final UserDAOImpl userDAO = new UserDAOImpl();

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
    public void testDeleteNotExisting() throws DBException {
        userDAO.delete(-1);
    }

    @Test
    public void testUpdate() throws DBException {
        userDAO.create(user);

        user = userDAO.getById(user.getId());
        user2.setId(user.getId());

        userDAO.update(user2);

        User userFromDB = userDAO.getById(user2.getId());

        assertNotNull(userFromDB);
        assertEquals(user2.getLogin(), userFromDB.getLogin());
        assertEquals(user2.getPassword(), userFromDB.getPassword());
        assertEquals(user2.getUserType(), userFromDB.getUserType());
        assertEquals(user2.getFirstName(), userFromDB.getFirstName());
        assertEquals(user2.getLastName(), userFromDB.getLastName());
        assertEquals(user2.getEmail(), userFromDB.getEmail());
        assertEquals(user2.getPhone(), userFromDB.getPhone());
    }

    @Test
    public void testUpdateNotExisting() throws DBException {
        userDAO.update(user);
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