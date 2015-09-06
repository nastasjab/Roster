package lv.javaguru.java2.database.user;


import lv.javaguru.java2.database.GenericHibernateDAOImpl;
import lv.javaguru.java2.domain.pattern.Pattern;
import lv.javaguru.java2.domain.user.UserPattern;
import org.hibernate.Criteria;
import org.hibernate.JDBCException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Component
public class UserPatternDAOImpl extends GenericHibernateDAOImpl<UserPattern> implements UserPatternDAO {

    @Transactional
    public List<UserPattern> getAll()  throws JDBCException {
        return sessionFactory.getCurrentSession().createCriteria(Pattern.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @Transactional
    public List<UserPattern> getByUserId(long id) {
        return sessionFactory.getCurrentSession().createCriteria(UserPattern.class)
                .addOrder(Order.asc("startDay"))
                .add(Restrictions.eq("userId", id))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @Transactional
    public UserPattern get(Date date, long userId) throws IndexOutOfBoundsException {
        return (UserPattern) sessionFactory.getCurrentSession().createCriteria(UserPattern.class)
                .add(Restrictions.eq("userId", userId))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.and(
                        Restrictions.le("startDay", date),
                        Restrictions.ge("endDay", date)))
                .setMaxResults(1)
                .list().get(0);
    }

    @Transactional
    public List<UserPattern> getByDate(Date date) {
        return sessionFactory.getCurrentSession().createCriteria(UserPattern.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.and(
                        Restrictions.ge("startDay", date),
                        Restrictions.le("endDay", date)))
                .list();
    }

    @Transactional
    public List<UserPattern> getByDateFrame(Date startDate, Date endDate) {
        return sessionFactory.getCurrentSession().createCriteria(UserPattern.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .addOrder(Order.asc("startDay"))
                .add(Restrictions.or(
                        Restrictions.between("startDay", startDate, endDate),
                        Restrictions.between("endDay", startDate, endDate),
                        Restrictions.and(
                                Restrictions.lt("startDay", startDate), Restrictions.gt("endDay", endDate))))
                .list();
    }

    @Transactional
    public List<UserPattern> getByDateFrame(Date startDate, Date endDate, long userId) {
        return sessionFactory.getCurrentSession().createCriteria(UserPattern.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .add(Restrictions.eq("userId", userId))
                .add(Restrictions.or(
                        Restrictions.between("startDay", startDate, endDate),
                        Restrictions.between("endDay", startDate, endDate),
                        Restrictions.and(
                                Restrictions.lt("startDay", startDate), Restrictions.gt("endDay", endDate))))
                .list();
    }

}
