package lv.javaguru.java2.database.hibernate;

import lv.javaguru.java2.database.ShiftOnExactDayDAO;
import lv.javaguru.java2.domain.ShiftOnExactDay;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Component
public class ShiftOnExactDayDAOImpl extends GenericHibernateDAOImpl<ShiftOnExactDay> implements ShiftOnExactDayDAO {

    @Transactional
    public ShiftOnExactDay getShiftOnExactDay(long userId, Date date) throws IndexOutOfBoundsException {

        return (ShiftOnExactDay) sessionFactory.getCurrentSession().createCriteria(ShiftOnExactDay.class)
                .add(Restrictions.eq("userId", userId))
                .add(Restrictions.eq("date", date))
                .setMaxResults(1).list().get(0);
    }

    @Transactional
    public void setShiftOnExactDay(ShiftOnExactDay shiftOnExactDay) {
        sessionFactory.getCurrentSession().saveOrUpdate(shiftOnExactDay);
    }

    @Transactional
    public List<ShiftOnExactDay> getShiftsOnExactDay(Date from, Date till) {
        return sessionFactory.getCurrentSession().createCriteria(ShiftOnExactDay.class)
                .add(Restrictions.between("date", from, till))
                .list();
    }

}
