package lv.javaguru.java2.servlet.mvc;


import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.RosterMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;

@Component
public class RosterController implements MVCController{

    @Autowired
    private UserDAO userDAO;

    public MVCModel processRequest(HttpServletRequest req) {

        RosterMap rosterMap = new RosterMap(getDateFrom(req), getDateTill(req));

        return new MVCModel(rosterMap, "/roster.jsp");

    }

    private Date getDateFrom(HttpServletRequest req) {
        Date result;
        try {
            result = Date.valueOf(req.getParameter("roster_date_from"));
        } catch (IllegalArgumentException e){
            result = Date.valueOf(LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-01");
        }
        return result;
    }

    private Date getDateTill(HttpServletRequest req) {
        Date result;
        try {
            result = Date.valueOf(req.getParameter("roster_date_till"));
        } catch (IllegalArgumentException e){
            result = Date.valueOf(LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().lengthOfMonth());
        }
        return result;
    }

}
