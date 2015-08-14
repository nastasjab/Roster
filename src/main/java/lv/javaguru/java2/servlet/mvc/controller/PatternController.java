package lv.javaguru.java2.servlet.mvc.controller;

import lv.javaguru.java2.domain.Pattern;
import lv.javaguru.java2.database.PatternDAO;
import lv.javaguru.java2.servlet.mvc.GenericMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import org.springframework.stereotype.Component;

@Component
public class PatternController extends GenericMVCController<PatternDAO, Pattern> implements MVCController {

    @Override
    protected String getListPageAddress() {
        return "/patterns.jsp";
    }
}
