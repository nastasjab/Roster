package lv.javaguru.java2.database;


import lv.javaguru.java2.domain.UserPattern;

import java.sql.Date;
import java.util.List;

public interface UserPatternDAO extends GenericDAO<UserPattern> {

    void create(UserPattern userPattern) throws DBException;

    UserPattern getById(long id) throws DBException;

    void delete(long id) throws DBException;

    void update(UserPattern userPattern) throws DBException;

    List<UserPattern> getAll() throws DBException;

    List<UserPattern> getByUserId(long id) throws DBException;

    List<UserPattern> getByDateFrame(Date startDate, Date endDate) throws DBException;

}
