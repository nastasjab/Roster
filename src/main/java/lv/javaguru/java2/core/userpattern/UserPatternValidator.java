package lv.javaguru.java2.core.userpattern;

import lv.javaguru.java2.core.ObjectNotExistException;
import lv.javaguru.java2.core.ValueOutOfBoundsException;
import lv.javaguru.java2.domain.pattern.Pattern;
import lv.javaguru.java2.domain.user.UserPattern;

import java.sql.Date;

public interface UserPatternValidator {
    void validateUserId(long userId) throws Exception;
    void validateDates(UserPattern userPattern, boolean add) throws Exception;
    void validatePatternsStartDay(UserPattern userPattern) throws Exception;
    void validatePatternId(long patternId) throws Exception;
}
