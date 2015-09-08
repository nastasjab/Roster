package lv.javaguru.java2.servlet.mvc.controller.pattern;

import lv.javaguru.java2.core.GenericService;
import lv.javaguru.java2.core.pattern.PatternFactory;
import lv.javaguru.java2.servlet.mvc.GenericNewMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatternController extends GenericNewMVCController implements MVCController {

    @Autowired
    PatternFactory patternFactory;

    @Override
    protected String getListPageAddress() {
        return "/patterns.jsp";
    }

    @Override
    protected GenericService getService() {
        return patternFactory;
    }
}
