package lv.javaguru.java2.database;


import lv.javaguru.java2.domain.UserPattern;

import java.sql.Date;
import java.util.List;

public interface UserPatternDAO extends GenericDAO<UserPattern> {
    List<UserPattern> getByUserId(long id) throws DBException;
    UserPattern get(Date date, long userId) throws DBException;
    List<UserPattern> getByDateFrame(Date startDate, Date endDate) throws DBException;
}
