package lv.javaguru.java2.database.user;

import lv.javaguru.java2.database.GenericDAO;
import lv.javaguru.java2.domain.user.User;

public interface UserDAO extends GenericDAO<User> {

    boolean existAtLeastOneAdminUser();
}
