package lv.javaguru.java2.core.userpattern;

import lv.javaguru.java2.core.ObjectNotExistException;
import lv.javaguru.java2.core.ValueOutOfBoundsException;
import lv.javaguru.java2.domain.pattern.Pattern;

import java.sql.Date;

public interface UserPatternValidator {
    void validateUserId(long userId) throws ObjectNotExistException;
    void validateStartDay(Date date) throws UserPatternOverlapException;
    void validateEndDay(Date date) throws UserPatternOverlapException;
    void validatePatternsStartDay(Pattern pattern, int patternStartDay) throws ValueOutOfBoundsException;
    void validatePatternId(long patternId) throws ObjectNotExistException;
}
