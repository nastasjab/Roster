package lv.javaguru.java2.core;


import lv.javaguru.java2.GenericSpringTest;
import lv.javaguru.java2.core.pattern.PatternValidator;
import lv.javaguru.java2.database.pattern.PatternDAO;
import lv.javaguru.java2.domain.pattern.Pattern;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static lv.javaguru.java2.domain.pattern.PatternBuilder.createPattern;

public class PatternValidatorTest extends GenericSpringTest {

    @Autowired
    PatternValidator patternValidator;

    @Autowired
    PatternDAO patternDAO;

    @Test
    public void testValidateName() throws Exception {
        Pattern pattern = createPattern().withName("pattern1ssss").build();
        patternDAO.create(pattern);
        patternValidator.validateName(pattern.getName(), pattern.getId(), false);
    }

    @Test (expected = ObjectExistException.class)
    public void testValidateNameError1() throws Exception {
        Pattern pattern = createPattern().withName("pattern1ssss").build();
        patternDAO.create(pattern);
        patternValidator.validateName(pattern.getName(), 0, true);
    }

    @Test (expected = ObjectExistException.class)
    public void testValidateNameError2() throws Exception {
        Pattern pattern = createPattern().withName("pattern1ssss").build();
        patternDAO.create(pattern);
        patternValidator.validateName(pattern.getName(), pattern.getId() + 1, false);
    }
}
