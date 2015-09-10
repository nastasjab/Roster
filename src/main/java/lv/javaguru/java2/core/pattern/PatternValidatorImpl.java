package lv.javaguru.java2.core.pattern;

import lv.javaguru.java2.core.EmptyObjectNameException;
import lv.javaguru.java2.core.ObjectExistException;
import lv.javaguru.java2.database.pattern.PatternDAO;
import lv.javaguru.java2.domain.pattern.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatternValidatorImpl implements  PatternValidator {

    @Autowired
    PatternDAO patternDAO;

    public void validateName(String name, long id, boolean add) throws Exception {
        if (name==null || name.isEmpty())
            throw new EmptyObjectNameException("pattern");

        Pattern patternFromDb = patternDAO.getByObjectName(name);

        if (add && patternFromDb!=null)
            throw new ObjectExistException(name);

        if (!add && patternFromDb!=null && patternFromDb.getId()!=id)
            throw new ObjectExistException(name);
    }
}
