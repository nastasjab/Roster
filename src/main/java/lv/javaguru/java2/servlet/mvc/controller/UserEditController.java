package lv.javaguru.java2.servlet.mvc.controller;

import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.servlet.mvc.GenericEditMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserEditController extends GenericEditMVCController<UserDAO,User> implements MVCController {

    @Override
    protected void fillParameters(HttpServletRequest req, User object) throws Exception{
        object.setLogin(req.getParameter("login"));
        object.setPassword(req.getParameter("password"));
        object.setUserType(req.getParameter("usertype"));
        object.setFirstName(req.getParameter("firstname"));
        object.setLastName(req.getParameter("lastname"));
        object.setEmail(req.getParameter("email"));
        object.setPhone(req.getParameter("phone"));
        validate(object);
    }

    @Override
    protected String getObjectName() {
        return "User";
    }

    @Override
    protected String getListPageAddress(HttpServletRequest req) {
        return "/roster/users";
    }

    @Override
    protected User getNewInstance() {
        return new User();
    }

    @Override
    protected String getEditPageAddressJSP() {
        return "/user.jsp";
    }

    private void validate(User user) throws Exception {

    }

}
