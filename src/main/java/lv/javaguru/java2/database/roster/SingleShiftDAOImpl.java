package lv.javaguru.java2.database.roster;

import lv.javaguru.java2.database.GenericHibernateDAOImpl;
import lv.javaguru.java2.domain.roster.SingleShift;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Component
public class SingleShiftDAOImpl extends GenericHibernateDAOImpl<SingleShift> implements SingleShiftDAO {

    @Transactional
    public SingleShift getShiftOnExactDay(long userId, Date date) throws IndexOutOfBoundsException {

        return (SingleShift) sessionFactory.getCurrentSession().createCriteria(SingleShift.class)
                .add(Restrictions.eq("userId", userId))
                .add(Restrictions.eq("date", date))
                .setMaxResults(1).list().get(0);
    }

    @Transactional
    public void setShiftOnExactDay(SingleShift singleShift) {
        sessionFactory.getCurrentSession().saveOrUpdate(singleShift);
    }

    @Transactional
    public List<SingleShift> getShiftsOnExactDay(Date from, Date till) {
        return sessionFactory.getCurrentSession().createCriteria(SingleShift.class)
                .add(Restrictions.between("date", from, till))
                .list();
    }

}
