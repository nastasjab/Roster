package lv.javaguru.java2.core.pattern;

import lv.javaguru.java2.core.GenericFactoryImpl;
import lv.javaguru.java2.database.pattern.PatternDAO;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.pattern.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static lv.javaguru.java2.domain.pattern.PatternBuilder.createPattern;

@Component
public class PatternFactoryImpl
        extends GenericFactoryImpl<PatternDAO, Pattern> implements PatternFactory {

    @Autowired
    PatternValidator patternValidator;

    @Override
    public Pattern getNewInstance() {
        return createPattern().build();
    }

    @Override
    public void validate(Generic object, boolean add) throws Exception {
        Pattern pattern = (Pattern)object;

        patternValidator.validateName(pattern.getName(), pattern.getId(), add);
    }


}
