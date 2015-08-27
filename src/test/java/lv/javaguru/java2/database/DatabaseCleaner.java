package lv.javaguru.java2.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import lv.javaguru.java2.database.jdbc.*;

// TODO remove JDBC implementation
class DatabaseCleaner extends DAOImpl {

    DatabaseCleaner() {
        super();
    }

    private List<String> getTableNames() {
        List<String> tableNames = new ArrayList<String>();
        tableNames.add("patterns_shifts");
        tableNames.add("shifts");
        tableNames.add("patterns");
        tableNames.add("user_patterns");
        tableNames.add("users");
        tableNames.add("shifts_on_exact_day");
        return tableNames;
    }

    public void cleanDatabase() throws DBException {
        for(String tableName : getTableNames()) {
            Connection connection = getConnection();
            try {
                connection = getConnection();
                PreparedStatement preparedStatement = connection
                        .prepareStatement("delete from " + tableName);
                preparedStatement.executeUpdate();
            } catch (Throwable e) {
                System.out.println("Exception while execute cleanDatabase() for table " + tableName);
                e.printStackTrace();
                throw new DBException(e);
            } finally {
                closeConnection(connection);
            }
        }
    }

}


/*
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
//@Transactional
class DatabaseCleaner {

    @Autowired
    private HelperDAO helperDAO;

    DatabaseCleaner() {
    }


    private List<String> getTableNames() {
        List<String> tableNames = new ArrayList<String>();
        tableNames.add("patterns_shifts");
        tableNames.add("shifts");
        tableNames.add("patterns");
        tableNames.add("user_patterns");
        tableNames.add("users");
        tableNames.add("shifts_on_exact_day");
        return tableNames;
    }

    public void cleanDatabase() throws DBException {
        for(String tableName : getTableNames()) {
            helperDAO.deleteTable(tableName);
        }
    }
}

 */