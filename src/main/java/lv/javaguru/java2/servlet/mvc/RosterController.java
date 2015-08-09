package lv.javaguru.java2.servlet.mvc;


import lv.javaguru.java2.database.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class RosterController implements MVCController{

    @Autowired
    private UserDAO userDAO;

    public MVCModel processRequest(HttpServletRequest req) {


        return new MVCModel(null, "/roster.jsp");

    }
}
