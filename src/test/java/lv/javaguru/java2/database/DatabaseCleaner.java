package lv.javaguru.java2.database;

import java.util.ArrayList;
import java.util.List;

class DatabaseCleaner {
    private HelperDAO helperDAO;

    public DatabaseCleaner(HelperDAO helperDAO) {
        this.helperDAO = helperDAO;
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

