package lv.javaguru.java2.servlet.mvc.controller.user;

import lv.javaguru.java2.core.GenericService;
import lv.javaguru.java2.core.user.UserService;
import lv.javaguru.java2.database.user.UserDAO;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.user.User;
import lv.javaguru.java2.servlet.mvc.GenericEditMVCController;
import lv.javaguru.java2.servlet.mvc.GenericNewEditMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static lv.javaguru.java2.domain.user.UserBuilder.createUser;

@Component
public class UserEditController extends GenericNewEditMVCController implements MVCController {

    @Autowired
    private UserService userService;

    @Override
    protected GenericService getService() {
        return userService;
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
    protected Generic fillParameters(HttpServletRequest req) throws Exception {
        return createUser()
                .withLogin(req.getParameter("login"))
                .withPassword(req.getParameter("password"))
                .withUsertype(req.getParameter("usertype"))
                .withFirstname(req.getParameter("firstname"))
                .withLastName(req.getParameter("lastname"))
                .withEmail(req.getParameter("email"))
                .withPhone(req.getParameter("phone"))
                .build();
    }

    @Override
    protected String getEditPageAddressJSP() {
        return "/user.jsp";
    }

}
