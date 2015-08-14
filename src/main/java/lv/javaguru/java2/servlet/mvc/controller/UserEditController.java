package lv.javaguru.java2.servlet.mvc.controller;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.servlet.mvc.MVCController;
import lv.javaguru.java2.servlet.mvc.MVCModel;
import lv.javaguru.java2.servlet.mvc.MessageContents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserEditController implements MVCController {

    @Autowired
    private UserDAO userDAO;

    public MVCModel processRequest(HttpServletRequest req) {

        if (req.getParameter("user_add") != null) {
            add(req).getId();
            return new MVCModel(new MessageContents("New user created", "New user created", "/roster/users", "back to Users List"), "/message.jsp");
        }

        if (req.getParameter("user_update") != null) {
            update(req);
            return new MVCModel(new MessageContents("User updated", "User updated", "/roster/users", "back to Users List"), "/message.jsp");
        }

        if (req.getParameter("user_delete") != null) {
            delete(req);
            return new MVCModel(new MessageContents("User deleted", "User deleted", "/roster/users", "back to Users List"), "/message.jsp");
        }

        User result = null;
        try {
            long id = getId(req);
            try {
                result = userDAO.getById(id);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            result = new User();
        }

        return new MVCModel(result, "/user.jsp");
    }

    private long getId(HttpServletRequest req) throws NullPointerException {
        long result = 0;
        try {
            result = Long.decode(req.getParameter("id"));
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return result;
    }

    private User add(HttpServletRequest req) {
        User user = new User();
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        user.setFirstName(req.getParameter("firstname"));
        user.setLastName(req.getParameter("lastname"));
        user.setUserType(req.getParameter("usertype"));
        user.setEmail(req.getParameter("email"));
        user.setPhone(req.getParameter("phone"));
        try {
            userDAO.create(user);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return user;
    }

    private void update(HttpServletRequest req) {
        User user = new User();
        user.setId(getId(req));
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        user.setFirstName(req.getParameter("firstname"));
        user.setLastName(req.getParameter("lastname"));
        user.setUserType(req.getParameter("usertype"));
        user.setEmail(req.getParameter("email"));
        user.setPhone(req.getParameter("phone"));
        try {
            userDAO.update(user);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    private void delete(HttpServletRequest req) {
        try {
            userDAO.delete(getId(req));
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
