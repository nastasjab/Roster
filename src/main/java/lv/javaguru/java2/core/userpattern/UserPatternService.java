package lv.javaguru.java2.core.userpattern;

import lv.javaguru.java2.core.GenericService;
import lv.javaguru.java2.domain.user.UserPattern;


import java.sql.Date;
import java.util.List;

public interface UserPatternService extends GenericService{
    UserPattern getUserPattern(Date date, long userId) throws IndexOutOfBoundsException;
    List<UserPattern> getByUserId(long id);
    List<UserPattern> getByDateFrame(Date startDate, Date endDate);
}
