package lv.javaguru.java2.servlet.mvc.controller;


import lv.javaguru.java2.core.roster.RosterService;
import lv.javaguru.java2.domain.Roster;
import lv.javaguru.java2.servlet.mvc.MVCController;
import lv.javaguru.java2.servlet.mvc.MVCModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;

@Component
public class RosterController implements MVCController {

    @Autowired
    private RosterService rosterService;

    public MVCModel processRequest(HttpServletRequest req) {

        return new MVCModel(rosterService
                .getRoster(new Roster(getDateFrom(req), getDateTill(req))), "/roster.jsp");

    }

    private Date getDateFrom(HttpServletRequest req) {
        Date result;
        try {
            result = Date.valueOf(req.getParameter("roster_date_from"));
        } catch (IllegalArgumentException e){
            result = Date.valueOf(LocalDate.now().getYear() + "-"
                    + LocalDate.now().getMonthValue() + "-01");
        }
        return result;
    }

    private Date getDateTill(HttpServletRequest req) {
        Date result;
        try {
            result = Date.valueOf(req.getParameter("roster_date_till"));
        } catch (IllegalArgumentException e){
            result = Date.valueOf(LocalDate.now().getYear() + "-"
                    + LocalDate.now().getMonthValue() + "-"
                    + LocalDate.now().lengthOfMonth());
        }
        return result;
    }

}
