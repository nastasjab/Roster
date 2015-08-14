package lv.javaguru.java2.servlet.mvc.controller;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.servlet.mvc.MVCController;
import lv.javaguru.java2.servlet.mvc.MVCModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserController implements MVCController {

    @Autowired
    private UserDAO userDAO;

    public MVCModel processRequest(HttpServletRequest req) {

        List<User> users = new ArrayList<User>();

        try {
            users = userDAO.getAll();
        } catch (DBException e) {
            e.printStackTrace();
        }

        return new MVCModel(users, "/users.jsp");
    }
}
