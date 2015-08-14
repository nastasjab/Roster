package lv.javaguru.java2.database.hibernate;

import lv.javaguru.java2.database.ShiftDAO;
import lv.javaguru.java2.domain.Shift;

import org.springframework.stereotype.Component;


@Component
public class ShiftDAOImpl extends GenericHibernateDAO<Shift> implements ShiftDAO {

}
