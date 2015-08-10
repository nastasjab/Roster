package lv.javaguru.java2.servlet.mvc;


import lv.javaguru.java2.database.*;
import lv.javaguru.java2.domain.RosterMap;
import lv.javaguru.java2.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;

@Component
public class RosterController implements MVCController{

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ShiftDAO shiftDAO;

    @Autowired
    private ShiftOnExactDayDAO shiftOnExactDayDAO;

    @Autowired
    private ShiftPatternDAO shiftPatternDAO;

    @Autowired
    private UserPatternDAO userPatternDAO;

    public MVCModel processRequest(HttpServletRequest req) {

        RosterMap rosterMap = new RosterMap(getDateFrom(req), getDateTill(req));

        try {
            for (User user : userDAO.getAll()) {
                rosterMap.setUserMap(user, null);
            }
        } catch (DBException e) {
            e.printStackTrace();
        }

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
