package lv.javaguru.java2.servlet.mvc.controller.user;

import lv.javaguru.java2.core.GenericFactory;
import lv.javaguru.java2.core.user.UserFactory;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.servlet.mvc.GenericMVCController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static lv.javaguru.java2.domain.user.UserBuilder.createUser;

@Controller
@Component
public class UserController extends GenericMVCController {

    @Autowired
    private UserFactory userFactory;

    @RequestMapping(value = "/users")
    public ModelAndView processRequest(HttpServletRequest request) {
       return super.processRequest(request);
    }

    @Override
    protected GenericFactory getService() {
        return userFactory;
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
    protected String getListPageAddressJSP(HttpServletRequest req) {
        return "/users.jsp";
    }

    @Override
    protected String getEditPageAddressJSP(HttpServletRequest req) {
        return "/user.jsp";
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

}
