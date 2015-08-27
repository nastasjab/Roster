package lv.javaguru.java2.database.hibernate;

import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserDAOImplImpl extends GenericHibernateDAOImpl<User> implements UserDAO {

}
