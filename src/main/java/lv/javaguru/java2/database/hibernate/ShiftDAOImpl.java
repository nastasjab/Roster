package lv.javaguru.java2.database.hibernate;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ShiftDAO;
import lv.javaguru.java2.domain.Shift;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component//("HIBERNATE")
public class ShiftDAOImpl implements ShiftDAO {

    @Autowired
    SessionFactory sessionFactory;

    public void create(Shift shift) throws DBException {
        sessionFactory.getCurrentSession().save(shift);
    }

    public Shift getById(long id) throws DBException {
        return (Shift)sessionFactory.getCurrentSession().get(Shift.class, id);
    }

    public void delete(long id) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        Shift shift = (Shift) session.get(Shift.class, id);
        session.delete(shift);
    }

    public void update(Shift shift) throws DBException {
        sessionFactory.getCurrentSession().update(shift);
    }

    public List<Shift> getAll() throws DBException {
        return sessionFactory.getCurrentSession().createCriteria(Shift.class).list();
    }
}
