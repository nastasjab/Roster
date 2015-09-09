package lv.javaguru.java2.servlet.mvc.controller.user;

import lv.javaguru.java2.core.GenericService;
import lv.javaguru.java2.core.user.UserFactory;
import lv.javaguru.java2.servlet.mvc.GenericMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserController extends GenericMVCController implements MVCController {

    @Autowired
    private UserFactory userFactory;

    @Override
    protected String getListPageAddress() {
        return "/users.jsp";
    }

    @Override
    protected GenericService getService() {
        return userFactory;
    }

}
