package lv.javaguru.java2.servlet.mvc.controller.user;

import lv.javaguru.java2.core.GenericFactory;
import lv.javaguru.java2.core.userpattern.UserPatternFactory;
import lv.javaguru.java2.database.pattern.PatternDAO;
import lv.javaguru.java2.database.user.UserDAO;
import lv.javaguru.java2.servlet.mvc.GenericMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import lv.javaguru.java2.servlet.mvc.MVCModel;
import lv.javaguru.java2.servlet.mvc.data.UserPatternControllerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserPatternController extends GenericMVCController implements MVCController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PatternDAO patternDAO;

    @Autowired
    private UserPatternFactory userPatternFactory;

    @Override
    public MVCModel processRequest(HttpServletRequest req)  {
        UserPatternControllerData result;
        try {
            result = new UserPatternControllerData();
            result.setUser(userDAO.getById(getUserId(req)));
            result.setUserPatterns(userPatternFactory.getByUserId(getUserId(req)));
        } catch (NullPointerException e) {
            result = new UserPatternControllerData();
        }

        try {
            // result.setPatterns((List<Pattern>)patternService.getAll()); - incompatible types, cannot cast
            result.setPatterns(patternDAO.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new MVCModel(result, getListPageAddress());
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
    protected String getListPageAddress() {
        return "/userPatterns.jsp";
    }

    @Override
    protected GenericFactory getService() {
        return userPatternFactory;
    }

}