package lv.javaguru.java2.database.user;


import lv.javaguru.java2.database.GenericHibernateDAOImpl;
import lv.javaguru.java2.domain.user.UserPattern;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Component
public class UserPatternDAOImpl extends GenericHibernateDAOImpl<UserPattern> implements UserPatternDAO {

    @Transactional
    public List<UserPattern> getByUserId(long id)  {
        return sessionFactory.getCurrentSession().createCriteria(UserPattern.class)
                .addOrder(Order.asc("startDay"))
                .add(Restrictions.eq("userId",id))
                .list();
    }

    @Transactional
    public List<UserPattern> getByDateFrame(Date startDate, Date endDate) {
        return sessionFactory.getCurrentSession().createCriteria(UserPattern.class)
                .addOrder(Order.asc("startDay"))
                .add(Restrictions.or(Restrictions.between("startDay", startDate, endDate),
                        Restrictions.between("endDay", startDate, endDate),
                        Restrictions.and(
                                Restrictions.lt("startDay", startDate), Restrictions.gt("endDay", endDate))))
                        .list();
    }
}
