package lv.javaguru.java2.servlet.mvc.controller.user;

import lv.javaguru.java2.core.GenericService;
import lv.javaguru.java2.core.userpattern.UserPatternService;
import lv.javaguru.java2.database.pattern.PatternDAO;
import lv.javaguru.java2.database.user.UserDAO;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.user.UserPattern;
import lv.javaguru.java2.servlet.mvc.GenericNewEditMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import lv.javaguru.java2.servlet.mvc.MVCModel;
import lv.javaguru.java2.servlet.mvc.data.UserPatternEditControllerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

@Component
public class UserPatternEditController extends GenericNewEditMVCController implements MVCController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserPatternService userPatternService;

    @Autowired
    private PatternDAO patternDAO;

    @Override
    protected MVCModel listObject(HttpServletRequest req) throws Exception {
        UserPatternEditControllerData result;
        try {
            result = new UserPatternEditControllerData();
            result.setUserPattern((UserPattern) userPatternService.getObject(getId(req)));
        } catch (NullPointerException e) {
            result = new UserPatternEditControllerData();
            result.getUserPattern().setPatternStartDay(1);
        }
        result.setUser(userDAO.getById(getUserId(req)));
        result.setPatterns(patternDAO.getAll());
        return new MVCModel(result, getEditPageAddressJSP());
    }

    private long getUserId(HttpServletRequest req) {

        try {
            return Long.valueOf(req.getParameter("user"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    protected String getEditPageAddressJSP() {
        return "/userPattern.jsp";
    }

    @Override
    protected GenericService getService() {
        return userPatternService;
    }

    @Override
    protected String getObjectName() {
        return "User Pattern";
    }

    @Override
    protected String getListPageAddress(HttpServletRequest req) {
        return  "/roster/userpatterns?user=" + getUserId(req);
    }

    @Override
    protected Generic fillParameters(HttpServletRequest req) throws Exception {
        UserPattern userPattern = new UserPattern();
        userPattern.setUserId(Long.valueOf(req.getParameter("user")));
        userPattern.setPatternStartDay(Integer.valueOf(req.getParameter("patternstartday")));
        userPattern.getPattern().setId(Long.valueOf(req.getParameter("pattern")));
        userPattern.setStartDay(Date.valueOf(req.getParameter("startday")));
        userPattern.setEndDay(Date.valueOf(req.getParameter("endday")));
        return userPattern;
    }

}
