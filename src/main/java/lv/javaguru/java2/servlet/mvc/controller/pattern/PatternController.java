package lv.javaguru.java2.servlet.mvc.controller.pattern;

import lv.javaguru.java2.core.GenericFactory;
import lv.javaguru.java2.core.pattern.PatternFactory;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.servlet.mvc.GenericMVCController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static lv.javaguru.java2.domain.pattern.PatternBuilder.createPattern;

@Controller
@Component
public class PatternController extends GenericMVCController {

    @Autowired
    PatternFactory patternFactory;

    @RequestMapping (value = "/patterns")
    public ModelAndView processRequest(HttpServletRequest req) {
        return super.processRequest(req);
    }

    @Override
    protected GenericFactory getService() {
        return patternFactory;
    }

    @Override
    protected String getObjectName() {
        return "Pattern";
    }

    @Override
    protected String getListPageAddress(HttpServletRequest req) {
        return "/roster/patterns";
    }

    @Override
    protected String getListPageAddressJSP(HttpServletRequest req) {
        return "/patterns.jsp";
    }

    @Override
    protected String getEditPageAddressJSP(HttpServletRequest req) {
        return "/pattern.jsp";
    }

    @Override
    protected Generic fillParameters(HttpServletRequest req) throws Exception {
        return createPattern()
                .withName(req.getParameter("name"))
                .build();
    }
}
