package lv.javaguru.java2.servlet.mvc.controller.user;

import lv.javaguru.java2.core.GenericFactory;
import lv.javaguru.java2.core.user.UserFactory;
import lv.javaguru.java2.core.userpattern.UserPatternFactory;
import lv.javaguru.java2.database.pattern.PatternDAO;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.user.User;
import lv.javaguru.java2.domain.user.UserPattern;
import lv.javaguru.java2.servlet.mvc.GenericMVCController;
import lv.javaguru.java2.servlet.mvc.data.UserPatternControllerData;
import lv.javaguru.java2.servlet.mvc.data.UserPatternEditControllerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

import static lv.javaguru.java2.domain.pattern.PatternBuilder.createPattern;
import static lv.javaguru.java2.domain.user.UserPatternBuilder.createUserPattern;

@Controller
@Component
public class UserPatternController extends GenericMVCController {

    @Autowired
    private UserFactory userFactory;

    @Autowired
    private PatternDAO patternDAO;

    @Autowired
    private UserPatternFactory userPatternFactory;

    @Override
    @RequestMapping(value = "/userpatterns")
    public ModelAndView processRequest(HttpServletRequest request) {
        return super.processRequest(request);
    }

    @Override
    protected ModelAndView listObject(HttpServletRequest req) throws Exception {
        UserPatternEditControllerData result = new UserPatternEditControllerData();
        if (getId(req) != 0) {
            result.setUserPattern((UserPattern) userPatternFactory.getObject(getId(req)));
        } else {
            result.getUserPattern().setPatternStartDay(1);
        }
        result.setUser((User) userFactory.getObject(getUserId(req)));
        result.setPatterns(patternDAO.getAll());
        return new ModelAndView(getEditPageAddressJSP(req), "model", result);
    }

    public ModelAndView listAllObjects(HttpServletRequest req) throws Exception {
        UserPatternControllerData result;
        try {
            result = new UserPatternControllerData();
            result.setUser((User) userFactory.getObject(getUserId(req)));
            result.setUserPatterns(userPatternFactory.getByUserId(getUserId(req)));
        } catch (Exception e) {
            result = new UserPatternControllerData();
        }

        // result.setPatterns((List<Pattern>)patternService.getAll()); - incompatible types, cannot cast
        result.setPatterns(patternDAO.getAll());

        return new ModelAndView(getListPageAddressJSP(req), "model", result);
    }

    protected long getUserId(HttpServletRequest req) throws NullPointerException {
        long result = 0;
        try {
            result = Long.decode(req.getParameter("user"));
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected GenericFactory getService() {
        return userPatternFactory;
    }

    @Override
    protected String getObjectName() {
        return "User Pattern";
    }

    @Override
    protected String getListPageAddress(HttpServletRequest req) {
        return "/roster/userpatterns?user=" + getUserId(req);
    }

    @Override
    protected String getListPageAddressJSP(HttpServletRequest req) {
        return "/userPatterns.jsp";
    }

    @Override
    protected String getEditPageAddressJSP(HttpServletRequest req) {
        return "/userPattern.jsp";
    }

    @Override
    protected Generic fillParameters(HttpServletRequest req) throws Exception {
        Date startDay;
        try {
            startDay = Date.valueOf(req.getParameter("startday"));
        } catch (Exception e) {
            startDay = null;
        }
        Date endDay;
        try {
            endDay = Date.valueOf(req.getParameter("endday"));
        } catch (Exception e) {
            endDay = null;
        }
        return createUserPattern()
                .withUserId(Long.valueOf(req.getParameter("user")))
                .withPatternStartDay(Integer.valueOf(req.getParameter("patternstartday")))
                .withPattern(createPattern()
                        .withId(Long.valueOf(req.getParameter("pattern")))
                        .build())
                .withStartDay(startDay)
                .withEndDay(endDay)
                .build();
    }

}