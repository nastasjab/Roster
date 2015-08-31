package lv.javaguru.java2.database.pattern;

import lv.javaguru.java2.database.GenericHibernateDAOImpl;
import lv.javaguru.java2.domain.pattern.Pattern;
import org.hibernate.Criteria;
import org.hibernate.JDBCException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class PatternDAOImpl extends GenericHibernateDAOImpl<Pattern> implements PatternDAO {

    @Transactional
    public List<Pattern> getAll()  throws JDBCException {
        return sessionFactory.getCurrentSession().createCriteria(Pattern.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }


    @Transactional
    public Pattern getById(long id)  throws JDBCException {
        return (Pattern)sessionFactory.getCurrentSession()
                .get(Pattern.class, id);
    }
}
