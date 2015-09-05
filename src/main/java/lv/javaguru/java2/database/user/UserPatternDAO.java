package lv.javaguru.java2.database.user;


import lv.javaguru.java2.database.GenericDAO;
import lv.javaguru.java2.domain.user.UserPattern;

import java.sql.Date;
import java.util.List;

public interface UserPatternDAO extends GenericDAO<UserPattern> {
    List<UserPattern> getByUserId(long id);
    UserPattern get(Date date, long userId) throws IndexOutOfBoundsException;
    List<UserPattern> getByDateFrame(Date startDate, Date endDate);
    List<UserPattern> getByDateFrame(Date startDate, Date endDate, long uderId);
    List<UserPattern> getByDate(Date date);
}
