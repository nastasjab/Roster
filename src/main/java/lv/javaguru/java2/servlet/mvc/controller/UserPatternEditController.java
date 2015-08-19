package lv.javaguru.java2.servlet.mvc.controller;

import lv.javaguru.java2.database.*;
import lv.javaguru.java2.domain.UserPattern;
import lv.javaguru.java2.servlet.mvc.GenericEditMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import lv.javaguru.java2.servlet.mvc.MVCModel;
import lv.javaguru.java2.servlet.mvc.data.UserPatternEditControllerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

@Component
public class UserPatternEditController extends GenericEditMVCController<UserPatternDAO, UserPattern> implements MVCController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserPatternDAO userPatternDAO;

    @Autowired
    private PatternDAO patternDAO;

    @Override
    protected MVCModel listObject(HttpServletRequest req) throws Exception {
        UserPatternEditControllerData result = null;
        try {
            result.setUserPattern(userPatternDAO.getById(getId(req)));
        } catch (NullPointerException e) {
            result = new UserPatternEditControllerData();
        }
        result.setUser(userDAO.getById(getUserId(req)));
        result.setShiftPatterns(patternDAO.getAll());
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
    protected String getObjectName() {
        return "User Pattern";
    }

    @Override
    protected String getListPageAddress(HttpServletRequest req) {
        return  "/roster/userpatterns?user=" + getUserId(req);
    }

    @Override
    protected UserPattern getNewInstance() {
        return new UserPattern();
    }

    @Override
    protected void fillParameters(HttpServletRequest req, UserPattern object) {
        object.setUserId(Long.valueOf(req.getParameter("user")));
        object.setPatternStartDay(Integer.valueOf(req.getParameter("patternstartday")));
        object.getPatternShift().setId(Long.valueOf(req.getParameter("pattern")));
        object.setStartDay(Date.valueOf(req.getParameter("startday")));
        object.setEndDay(Date.valueOf(req.getParameter("endday")));
    }

}
