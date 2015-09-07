package lv.javaguru.java2.database.user;

import lv.javaguru.java2.database.GenericHibernateDAOImpl;
import lv.javaguru.java2.domain.shift.Shift;
import lv.javaguru.java2.domain.user.User;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

@Component
public class UserDAOImpl extends GenericHibernateDAOImpl<User> implements UserDAO {
    @Transactional
    public User getByObjectName(String objectName)  throws JDBCException {
        List<User> list = sessionFactory.getCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("login", objectName))
                .list();
        return list==null || list.isEmpty() ? null : list.get(0);
    }

    @Transactional
    public boolean existAtLeastOneAdminUser() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(
                "select count(*) from users where userType='A'");
        List<BigInteger> list=query.list();

        return list.get(0)==null || list.get(0).intValue()<1 ? false : true;
    }
}
