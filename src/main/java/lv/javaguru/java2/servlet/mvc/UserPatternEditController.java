package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PatternDAO;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.database.UserPatternDAO;
import lv.javaguru.java2.domain.Pattern;
import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.domain.UserPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;

@Component
public class UserPatternEditController implements MVCController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserPatternDAO userPatternDAO;

    @Autowired
    private PatternDAO patternDAO;

    public MVCModel processRequest(HttpServletRequest req) {

        if (req.getParameter("user_pattern_add") != null) {
            add(req);
            return new MVCModel(new MessageContents("New user pattern created", "New user pattern created", "/roster/userpatterns?user=" + req.getParameter("user"), "back to User Patterns List"), "/message.jsp");
        }

        if (req.getParameter("user_pattern_update") != null) {
            update(req);
            return new MVCModel(new MessageContents("User pattern updated", "User pattern updated", "/roster/userpatterns?user=" + req.getParameter("user"), "back to User Patterns List"), "/message.jsp");
        }

        if (req.getParameter("user_pattern_delete") != null) {
            delete(req);
            return new MVCModel(new MessageContents("User pattern deleted", "User pattern deleted", "/roster/userpatterns?user=" + req.getParameter("user"), "back to User Patterns List"), "/message.jsp");
        }

        User user = null;
        try {
            try {
                user = userDAO.getById(Long.decode(req.getParameter("user")));
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        UserPattern userPattern = null;
        try {
            try {
                userPattern = userPatternDAO.getById(getId(req));
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            userPattern = new UserPattern();
        }

        List<Pattern> patterns = null;
        try {
            try {
                patterns = patternDAO.getAll();
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return new MVCModel(new UserPatternEditControllerData(user, userPattern, patterns), "/userPattern.jsp");
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

    private UserPattern add(HttpServletRequest req) {
        UserPattern userPattern = new UserPattern();
        userPattern.setStartDay(Date.valueOf(req.getParameter("startday")));
        userPattern.setEndDay(Date.valueOf(req.getParameter("endday")));
        userPattern.setShiftPatternId(Long.valueOf(req.getParameter("pattern")));
        userPattern.setPatternStartDay(Integer.valueOf(req.getParameter("patternstartday")));
        userPattern.setUserId(Long.valueOf(req.getParameter("user")));
        try {
            userPatternDAO.create(userPattern);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return userPattern;
    }

    private void update(HttpServletRequest req) {
        UserPattern userPattern = new UserPattern();
        userPattern.setId(getId(req));
        userPattern.setStartDay(Date.valueOf(req.getParameter("startday")));
        userPattern.setEndDay(Date.valueOf(req.getParameter("endday")));
        userPattern.setShiftPatternId(Long.valueOf(req.getParameter("pattern")));
        userPattern.setPatternStartDay(Integer.valueOf(req.getParameter("patternstartday")));
        userPattern.setUserId(Long.valueOf(req.getParameter("user")));
        try {
            userPatternDAO.update(userPattern);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    private void delete(HttpServletRequest req) {
        try {
            userPatternDAO.delete(getId(req));
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
