package lv.javaguru.java2.database;

import lv.javaguru.java2.database.HelperDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class HelperDAOImpl implements HelperDAO {

    public HelperDAOImpl() {
    }

    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public void deleteTable(String tableName) {
        sessionFactory.getCurrentSession().createSQLQuery(
                "delete from :tableName")
                .setParameter("tableName", tableName);
    }
}
