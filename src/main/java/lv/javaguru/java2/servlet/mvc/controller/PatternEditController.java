package lv.javaguru.java2.servlet.mvc.controller;

import lv.javaguru.java2.core.GenericService;
import lv.javaguru.java2.core.pattern.PatternService;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.Pattern;
import lv.javaguru.java2.servlet.mvc.GenericNewEditMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PatternEditController
        extends GenericNewEditMVCController implements MVCController {

    @Autowired
    PatternService patternService;

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
        return patternService;
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
        Pattern object = new Pattern();
        object.setName(req.getParameter("name"));
        return object;
    }


}
