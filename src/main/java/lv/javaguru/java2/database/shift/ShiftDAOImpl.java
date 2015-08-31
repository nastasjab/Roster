package lv.javaguru.java2.database.shift;

import lv.javaguru.java2.database.GenericHibernateDAOImpl;
import lv.javaguru.java2.domain.shift.Shift;

import org.springframework.stereotype.Component;

@Component
public class ShiftDAOImpl extends GenericHibernateDAOImpl<Shift> implements ShiftDAO {

}
