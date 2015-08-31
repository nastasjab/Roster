package lv.javaguru.java2.core.pattern;

import lv.javaguru.java2.core.GenericServiceImpl;
import lv.javaguru.java2.database.pattern.PatternDAO;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.pattern.Pattern;
import org.springframework.stereotype.Component;

@Component
public class PatternServiceImpl
        extends GenericServiceImpl<PatternDAO, Pattern> implements PatternService {
    @Override
    public Pattern getNewInstance() {
        return new Pattern();
    }

    @Override
    protected void validate(Generic object) throws Exception {

    }
}
