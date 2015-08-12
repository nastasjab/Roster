package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PatternDAO;
import lv.javaguru.java2.domain.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PatternEditController implements MVCController {

    @Autowired
    private PatternDAO patternDAO;

    public MVCModel processRequest(HttpServletRequest req) {

        if (req.getParameter("pattern_add") != null) {
            add(req);
            return new MVCModel(new MessageContents("New pattern created", "New pattern created", "/roster/patterns", "back to Patterns List"), "/message.jsp");
        }

        if (req.getParameter("pattern_update") != null) {
            update(req);
            return new MVCModel(new MessageContents("Pattern updated", "Pattern updated", "/roster/patterns", "back to Patterns List"), "/message.jsp");
        }

        if (req.getParameter("pattern_delete") != null) {
            delete(req);
            return new MVCModel(new MessageContents("Pattern deleted", "Pattern deleted", "/roster/patterns", "back to Patterns List"), "/message.jsp");
        }

        Pattern result = null;
        try {
            long id = getId(req);
            try {
                result = patternDAO.getById(id);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            result = new Pattern();
        }

        return new MVCModel(result, "/pattern.jsp");
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

    private Pattern add(HttpServletRequest req) {
        Pattern pattern = new Pattern();
        pattern.setName(req.getParameter("name"));
        try {
            patternDAO.create(pattern);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return pattern;
    }

    private void update(HttpServletRequest req) {
        Pattern pattern = new Pattern();
        pattern.setId(getId(req));
        pattern.setName(req.getParameter("name"));
        try {
            patternDAO.update(pattern);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    private void delete(HttpServletRequest req) {
        try {
            patternDAO.delete(getId(req));
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
