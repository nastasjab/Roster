package lv.javaguru.java2.servlet.mvc.controller;

import lv.javaguru.java2.servlet.mvc.MVCController;
import lv.javaguru.java2.servlet.mvc.MVCModel;
import lv.javaguru.java2.servlet.mvc.data.LoginControllerData;
import lv.javaguru.java2.servlet.mvc.data.Menu;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class LoginController implements MVCController {

    public MVCModel processRequest(HttpServletRequest req) {

        LoginControllerData data = new LoginControllerData();
        data.setLogin(req.getParameter("login"));
        data.setPassword(req.getParameter("password"));

        if (data.getLogin()!=null && data.getLogin().equals("admin") &&
                data.getPassword()!=null && data.getPassword().equals("admin"))
            return new MVCModel(new Menu(), "redirect:/menu");
        else
            return new MVCModel(null, "/login.jsp");
    }
}
