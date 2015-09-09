package lv.javaguru.java2.core.userpattern;

import lv.javaguru.java2.core.ObjectNotExistException;
import lv.javaguru.java2.core.ValueOutOfBoundsException;
import lv.javaguru.java2.database.pattern.PatternDAO;
import lv.javaguru.java2.database.user.UserDAO;
import lv.javaguru.java2.database.user.UserPatternDAO;
import lv.javaguru.java2.domain.pattern.Pattern;
import lv.javaguru.java2.domain.user.UserPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
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

    public void validateUserId(UserPattern userPattern) throws Exception {
        if (userPattern.getUserId() == 0 || userDAO.getById(userPattern.getUserId()) == null)
            throw new ObjectNotExistException("user");
    }

    public void validateDates(UserPattern userPattern, boolean add) throws Exception {

        if (userPattern.getStartDay() == null)
            throw new InvalidDateFormatException("pattern start");

        if (userPattern.getEndDay() == null)
            throw new InvalidDateFormatException("pattern end");

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

    public void validatePatternId(UserPattern userPattern) throws Exception {
        if (patternDAO.getById(userPattern.getPattern().getId()) == null)
            throw new ObjectNotExistException("pattern");
    }
}
