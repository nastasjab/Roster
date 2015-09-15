package lv.javaguru.java2.core.userpattern;

import lv.javaguru.java2.core.EmptyIdentifierException;
import lv.javaguru.java2.core.ObjectNotExistException;
import lv.javaguru.java2.core.ValueOutOfBoundsException;
import lv.javaguru.java2.database.pattern.PatternDAO;
import lv.javaguru.java2.database.user.UserDAO;
import lv.javaguru.java2.database.user.UserPatternDAO;
import lv.javaguru.java2.domain.user.UserPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserPatternValidatorImpl implements UserPatternValidator{

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PatternDAO patternDAO;

    @Autowired
    private UserPatternDAO userPatternDAO;

    public void validateUserId(long userId) throws Exception {
        if (userId == 0)
            throw new EmptyIdentifierException("user");

        if (userDAO.getById(userId) == null)
            throw new ObjectNotExistException("user");
    }

    public void validateDates(UserPattern userPattern, boolean add) throws Exception {

        if (userPattern.getStartDay() == null)
            throw new InvalidDateFormatException("pattern start");

        if (userPattern.getEndDay() == null)
            throw new InvalidDateFormatException("pattern end");

        if (userPattern.getEndDay().before(userPattern.getStartDay()))
            throw new StartDateAfterEndDateException();

        List<UserPattern> overlapsWith = new ArrayList<UserPattern>();
        List<UserPattern> userPatterns = userPatternDAO
                .getByDateFrame(userPattern.getStartDay(), userPattern.getEndDay(), userPattern.getUserId());

        for (UserPattern userPatternFromDB : userPatterns)
            if (add || !add && userPattern.getId() != userPatternFromDB.getId())
                overlapsWith.add(userPatternFromDB);

        if (overlapsWith.size() != 0)
            throw new UserPatternOverlapException(overlapsWith);
    }

    public void validatePatternsStartDay(UserPattern userPattern) throws Exception {
        if (userPattern.getPatternStartDay() < 1 || userPattern.getPatternStartDay() > patternDAO.getById(userPattern.getPattern().getId()).getSize())
            throw new ValueOutOfBoundsException("Pattern start day", "1 - " + patternDAO.getById(userPattern.getPattern().getId()).getSize());
    }

    public void validatePatternId(long patternId) throws Exception {
        if (patternId == 0)
            throw new EmptyIdentifierException("pattern");

        if (patternDAO.getById(patternId) == null)
            throw new ObjectNotExistException("pattern");
    }
}
