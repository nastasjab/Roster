package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.database.UserPatternDAO;
import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.domain.UserPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserPatternController implements MVCController{

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserPatternDAO userPatternDAO;

    public MVCModel processRequest(HttpServletRequest req) {

        User user = new User();

        try {
            user = userDAO.getById(Long.decode(req.getParameter("user")));
        } catch (DBException e) {
            e.printStackTrace();
        }

        List<UserPattern> userPatterns = new ArrayList<UserPattern>();

        try {
            userPatterns = userPatternDAO.getByUserId(Long.decode(req.getParameter("user")));
        } catch (DBException e) {
            e.printStackTrace();
        }

        UserPatternControllerData data = new UserPatternControllerData(user, userPatterns);

        return new MVCModel(data, "/userPatterns.jsp");
    }
}