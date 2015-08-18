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
        if (!user.getEmail().isEmpty() && !isValidEmailAddress(user.getEmail()))
            throw new Exception("Invalid e-mail address!");

        if (!user.getPhone().isEmpty() && !isValidPhone(user.getPhone()))
            throw new Exception("Invalid phone number!");

    }

    private boolean isValidPhone(String phone) {
        String ePattern = "^[0-9 +#-]+$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(phone);
        return m.matches();
    }

    private boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
