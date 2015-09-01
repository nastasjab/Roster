package lv.javaguru.java2.database.shift;

import lv.javaguru.java2.database.GenericHibernateDAOImpl;
import lv.javaguru.java2.domain.shift.Shift;

import org.hibernate.JDBCException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class ShiftDAOImpl extends GenericHibernateDAOImpl<Shift> implements ShiftDAO {

    @Transactional
    public Shift getByObjectName(String objectName)  throws JDBCException {
        List<Shift> list = sessionFactory.getCurrentSession().createCriteria(Shift.class)
                .add(Restrictions.eq("name", objectName))
                .list();
        return list==null || list.isEmpty() ? null : list.get(0);
    }
}
