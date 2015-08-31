package lv.javaguru.java2.database.user;

import lv.javaguru.java2.database.GenericHibernateDAOImpl;
import lv.javaguru.java2.domain.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserDAOImpl extends GenericHibernateDAOImpl<User> implements UserDAO {

}
