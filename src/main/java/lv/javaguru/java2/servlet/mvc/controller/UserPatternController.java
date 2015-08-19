package lv.javaguru.java2.servlet.mvc.controller;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.database.UserPatternDAO;
import lv.javaguru.java2.domain.UserPattern;
import lv.javaguru.java2.servlet.mvc.GenericMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import lv.javaguru.java2.servlet.mvc.MVCModel;
import lv.javaguru.java2.servlet.mvc.data.UserPatternControllerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserPatternController extends GenericMVCController<UserPatternDAO, UserPattern> implements MVCController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserPatternDAO userPatternDAO;

    @Override
    public MVCModel processRequest(HttpServletRequest req)  {
        UserPatternControllerData result = null;
        try {
            result = new UserPatternControllerData();
            result.setUser(userDAO.getById(getUserId(req)));
            result.setUserPatterns(userPatternDAO.getByUserId(getUserId(req)));

        } catch (NullPointerException e) {
            result = new UserPatternControllerData();
        } catch (DBException e) {
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

}