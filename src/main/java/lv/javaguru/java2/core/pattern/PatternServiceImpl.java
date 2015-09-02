package lv.javaguru.java2.core.pattern;

import lv.javaguru.java2.core.*;
import lv.javaguru.java2.database.pattern.PatternDAO;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.pattern.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatternServiceImpl
        extends GenericServiceImpl<PatternDAO, Pattern> implements PatternService {

    @Autowired
    PatternValidator patternValidator;

    @Override
    public Pattern getNewInstance() {
        return new Pattern();
    }

    @Override
    public void validate(Generic object, boolean add) throws Exception {
        Pattern pattern = (Pattern)object;

        patternValidator.validateName(pattern.getName(), pattern.getId(), add);
    }


}
