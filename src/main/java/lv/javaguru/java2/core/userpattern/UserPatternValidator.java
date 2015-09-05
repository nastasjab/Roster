package lv.javaguru.java2.core.userpattern;

import lv.javaguru.java2.core.ObjectNotExistException;
import lv.javaguru.java2.core.ValueOutOfBoundsException;
import lv.javaguru.java2.domain.pattern.Pattern;
import lv.javaguru.java2.domain.user.UserPattern;

import java.sql.Date;

public interface UserPatternValidator {
    void validateUserId(UserPattern userPattern) throws Exception;
    void validateDates(UserPattern userPattern, boolean add) throws Exception;
    void validatePatternsStartDay(UserPattern userPattern) throws Exception;
    void validatePatternId(UserPattern userPattern) throws Exception;
}
