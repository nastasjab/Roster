package lv.javaguru.java2.database;


import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.UserPattern;

import java.util.List;

public interface UserPatternDAO {
    void create(UserPattern userPattern) throws DBException;

    UserPattern getById(long id) throws DBException;

    void delete(long id) throws DBException;

    void update(UserPattern userPattern) throws DBException;

    List<UserPattern> getAll() throws DBException;

}
