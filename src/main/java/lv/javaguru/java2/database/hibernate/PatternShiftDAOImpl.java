package lv.javaguru.java2.database.hibernate;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PatternShiftDAO;
import lv.javaguru.java2.domain.PatternShift;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class PatternShiftDAOImpl extends GenericHibernateDAO<PatternShift> implements PatternShiftDAO {

    @Transactional
    public List<PatternShift> getAll() {
        return sessionFactory.getCurrentSession().createCriteria(PatternShift.class)
                .addOrder(Order.asc("seqNo")).list();
    }

    @Transactional
    public List<PatternShift> getAll(long patternId) throws DBException {
        return sessionFactory.getCurrentSession().createCriteria(PatternShift.class)
                .add(Restrictions.eq("patternId", patternId))
                .addOrder(Order.asc("seqNo")).list();

    }

    @Transactional
    public void deleteByPatternId(long patternId) throws DBException {
        List<PatternShift> patternShifts = getAll(patternId);
        Session session = sessionFactory.getCurrentSession();
        for (PatternShift patternShift : patternShifts) {
            PatternShift obj = (PatternShift) session.get(PatternShift.class, patternShift.getId());
            session.delete(obj);
        }
    }
}
