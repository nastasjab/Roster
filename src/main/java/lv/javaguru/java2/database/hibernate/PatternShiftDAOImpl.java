package lv.javaguru.java2.database.hibernate;

import lv.javaguru.java2.database.PatternShiftDAO;
import lv.javaguru.java2.domain.Pattern;
import lv.javaguru.java2.domain.PatternShift;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;


import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

@Component
public class PatternShiftDAOImpl extends GenericHibernateDAOImpl<PatternShift> implements PatternShiftDAO {

    @Transactional
    public List<PatternShift> getAll() throws JDBCException {
        return sessionFactory.getCurrentSession().createCriteria(PatternShift.class)
                .addOrder(Order.asc("seqNo")).list();
    }

    @Transactional
    public List<PatternShift> getAll(long patternId) throws JDBCException {
        Pattern pattern = new Pattern();
        pattern.setId(patternId);
        return sessionFactory.getCurrentSession().createCriteria(PatternShift.class)
                .add(Restrictions.eq("pattern", pattern))
                .addOrder(Order.asc("seqNo")).list();

    }

    @Transactional
    public void deleteByPatternId(long patternId) throws JDBCException {
        List<PatternShift> patternShifts = getAll(patternId);
        Session session = sessionFactory.getCurrentSession();
        for (PatternShift patternShift : patternShifts) {
            PatternShift obj = (PatternShift) session.get(PatternShift.class, patternShift.getId());
            session.delete(obj);
        }
    }

    @Transactional
    public int getNextSequenceNo(long patternId) throws JDBCException {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(
                "select max(seqNo) + 1 from patterns_shifts where patternid = :patternId limit 1")
                .setParameter("patternId", patternId);
        List<BigInteger> list=query.list();

        return list.get(0)==null ? 1 : list.get(0).intValue();
    }
}
