package lv.javaguru.java2.database;

import lv.javaguru.java2.GenericSpringTest;
import lv.javaguru.java2.database.user.UserDAO;
import lv.javaguru.java2.domain.user.User;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserDAOImplTest extends GenericSpringTest {
    @Autowired
    private UserDAO userDAO;

    private User user;
    private User user2;

    @Before
    public void init()  {
        user = createUser("userrrrr", "encryptedPassword", "U", "dima", "pavlov", "dima@pavlov.lv", "29295656");
        user2 = createUser("adminnnn", "encryptedPassword2", "A", "ivan", "petrov", "ivan@petrov.lv", "29299898");
    }

    @Test
    public void testCreate()  {
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
    public void testMultipleUserCreation()  {
        List<User> users = userDAO.getAll();
        int usersCount = users==null ? 0 : users.size();

        userDAO.create(user);
        userDAO.create(user2);
        users = userDAO.getAll();
        assertEquals(2, users.size()-usersCount);
    }

    @Test
     public void testDelete()  {
        List<User> users = userDAO.getAll();
        int usersCount = users==null ? 0 : users.size();

        userDAO.create(user);
        userDAO.create(user2);
        users = userDAO.getAll();
        assertEquals(2, users.size()-usersCount);

        userDAO.delete(user.getId());
        users = userDAO.getAll();
        assertEquals(1, users.size()-usersCount);

        userDAO.delete(user2.getId());
        users = userDAO.getAll();
        assertEquals(0, users.size()-usersCount);
    }


    @Test
    public void testUpdate()  {
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