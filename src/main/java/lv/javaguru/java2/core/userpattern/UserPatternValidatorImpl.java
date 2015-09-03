package lv.javaguru.java2.core.userpattern;

import lv.javaguru.java2.core.ObjectNotExistException;
import lv.javaguru.java2.core.ValueOutOfBoundsException;
import lv.javaguru.java2.database.pattern.PatternDAO;
import lv.javaguru.java2.database.user.UserDAO;
import lv.javaguru.java2.database.user.UserPatternDAO;
import lv.javaguru.java2.domain.pattern.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class UserPatternValidatorImpl implements UserPatternValidator{

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PatternDAO patternDAO;

    @Autowired
    private UserPatternDAO userPatternDAO;

    public void validateUserId(long userId) throws ObjectNotExistException {
        if (userId == 0 || userDAO.getById(userId) == null)
            throw new ObjectNotExistException("user");
    }

    public void validateStartDay(Date date) throws UserPatternOverlapException {
        if (userPatternDAO.getByDate(date) != null) // TODO unless updated!
            throw new UserPatternOverlapException();
    }

    public void validateEndDay(Date date) throws UserPatternOverlapException {
        if (userPatternDAO.getByDate(date) != null)  // TODO unless updated!
            throw new UserPatternOverlapException();
    }

    public void validatePatternsStartDay(Pattern pattern, int patternStartDay) throws ValueOutOfBoundsException {
        System.out.println(pattern.getPatternShifts().size());
        if (patternStartDay < 1 || patternStartDay > pattern.getPatternShifts().size())
            throw new ValueOutOfBoundsException("Pattern start day");
    }

    public void validatePatternId(long patternId) throws ObjectNotExistException {
        if (patternDAO.getById(patternId) == null)
            throw new ObjectNotExistException("pattern");
    }
}
