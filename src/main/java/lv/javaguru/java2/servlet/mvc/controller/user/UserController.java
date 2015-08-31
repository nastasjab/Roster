package lv.javaguru.java2.servlet.mvc.controller.user;

import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.servlet.mvc.GenericMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import org.springframework.stereotype.Component;

@Component
public class UserController extends GenericMVCController<UserDAO,User> implements MVCController {

    @Override
    protected String getListPageAddress() {
        return "/users.jsp";
    }

}
