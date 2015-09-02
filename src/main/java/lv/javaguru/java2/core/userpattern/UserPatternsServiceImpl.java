package lv.javaguru.java2.core.userpattern;

import lv.javaguru.java2.core.GenericServiceImpl;
import lv.javaguru.java2.database.user.UserPatternDAO;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.user.UserPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Component
public class UserPatternsServiceImpl
        extends GenericServiceImpl<UserPatternDAO, UserPattern>
        implements UserPatternService{

    @Autowired
    private UserPatternDAO userPatternDAO;

    public UserPattern getUserPattern(Date date, long userId) throws IndexOutOfBoundsException {
        try {
            return userPatternDAO.get(date, userId);
        } catch (Exception e) {
            return new UserPattern();
        }
    }

    public List<UserPattern> getByUserId(long id) {
        try {
            return userPatternDAO.getByUserId(id);
        } catch (Exception e) {
            return new ArrayList<UserPattern>();
        }
    }

    public List<UserPattern> getByDateFrame(Date startDate, Date endDate) {
        try {
            return userPatternDAO.getByDateFrame(startDate, endDate);
        } catch (Exception e) {
            return new ArrayList<UserPattern>();

        }
    }

    @Override
    public void validate(Generic object, boolean add) throws Exception {

    }

    @Override
    public UserPattern getNewInstance() {
        return new UserPattern();
    }
}
