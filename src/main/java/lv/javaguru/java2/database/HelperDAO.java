package lv.javaguru.java2.database;

import org.hibernate.JDBCException;

public interface HelperDAO {
    void deleteTable (String tableName) throws JDBCException, DBException;
}
