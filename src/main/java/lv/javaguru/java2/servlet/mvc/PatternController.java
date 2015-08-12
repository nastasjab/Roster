package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PatternDAO;
import lv.javaguru.java2.domain.Pattern;
import lv.javaguru.java2.domain.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class PatternController implements MVCController{

    @Autowired
    private PatternDAO patternDAO;

    public MVCModel processRequest(HttpServletRequest req) {

        List<Pattern> patterns = new ArrayList<Pattern>();

        try {
            patterns = patternDAO.getAll();
        } catch (DBException e) {
            e.printStackTrace();
        }

        return new MVCModel(patterns, "/patterns.jsp");
    }
}
