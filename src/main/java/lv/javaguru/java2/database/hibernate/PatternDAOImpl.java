package lv.javaguru.java2.database.hibernate;

import lv.javaguru.java2.domain.Pattern;
import lv.javaguru.java2.database.PatternDAO;
import org.springframework.stereotype.Component;

@Component
public class PatternDAOImpl extends GenericHibernateDAO<Pattern> implements PatternDAO {

}
