package lv.javaguru.java2.core.userpattern;

import lv.javaguru.java2.core.GenericFactoryImpl;
import lv.javaguru.java2.core.ObjectExistException;
import lv.javaguru.java2.database.user.UserPatternDAO;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.user.UserPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static lv.javaguru.java2.domain.user.UserPatternBuilder.createUserPattern;


@Component
public class UserPatternFactoryImpl
        extends GenericFactoryImpl<UserPatternDAO, UserPattern>
        implements UserPatternFactory {

    @Autowired
    private UserPatternDAO userPatternDAO;

    @Autowired
    private UserPatternValidator validator;

    public UserPattern getUserPattern(Date date, long userId) throws IndexOutOfBoundsException {
        try {
            return userPatternDAO.get(date, userId);
        } catch (Exception e) {
            return createUserPattern().build();
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
        UserPattern userPattern = (UserPattern) object;

        if (add && userPatternDAO.getById(userPattern.getId()) != null)
            throw new ObjectExistException("User Pattern");

        validator.validateUserId(userPattern);
        validator.validateDates(userPattern, add);
        validator.validatePatternsStartDay(userPattern);
        validator.validatePatternId(userPattern);
    }

    @Override
    public UserPattern getNewInstance() {
        return createUserPattern().build();
    }
}
