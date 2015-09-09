package lv.javaguru.java2.database.user;

import lv.javaguru.java2.database.GenericDAO;
import lv.javaguru.java2.domain.user.User;
import org.hibernate.JDBCException;

import java.util.List;

public interface UserDAO extends GenericDAO<User> {

    boolean existAtLeastOneAdminUser();
    List<User> getAllSorted() throws JDBCException;

}
