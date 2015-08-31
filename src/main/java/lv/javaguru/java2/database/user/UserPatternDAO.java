package lv.javaguru.java2.database.user;


import lv.javaguru.java2.database.GenericDAO;
import lv.javaguru.java2.domain.user.UserPattern;

import java.sql.Date;
import java.util.List;

public interface UserPatternDAO extends GenericDAO<UserPattern> {
    List<UserPattern> getByUserId(long id);

    List<UserPattern> getByDateFrame(Date startDate, Date endDate);
}
