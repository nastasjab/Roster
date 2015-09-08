package lv.javaguru.java2.servlet.mvc.controller.pattern;

import lv.javaguru.java2.core.GenericService;
import lv.javaguru.java2.core.pattern.PatternFactory;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.servlet.mvc.GenericNewEditMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static lv.javaguru.java2.domain.pattern.PatternBuilder.createPattern;

@Component
public class PatternEditController
        extends GenericNewEditMVCController implements MVCController {

    @Autowired
    PatternFactory patternFactory;

    private long getPatternId(HttpServletRequest req) throws Exception {
        try {
            return Long.valueOf(req.getParameter("pattern_id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    @Override
    protected GenericService getService() {
        return patternFactory;
    }

    @Override
    protected String getObjectName() {
        return "Pattern";
    }

    @Override
    protected String getEditPageAddressJSP() {
        return "/pattern.jsp";
    }

    @Override
    protected String getListPageAddress(HttpServletRequest req) {
        return  "/roster/patterns";
    }

    @Override
    protected Generic fillParameters(HttpServletRequest req) throws Exception{
        return createPattern()
                .withName(req.getParameter("name")).build();
    }


}
