package lv.javaguru.java2.servlet.mvc.controller.roster;


import lv.javaguru.java2.core.roster.RosterFactory;
import lv.javaguru.java2.domain.roster.Roster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;

@Controller
@Component
public class RosterController {

    @Autowired
    private RosterFactory rosterFactory;

    @RequestMapping(value = "/roster")
    public ModelAndView processRequest(HttpServletRequest req) {

        return new ModelAndView("/roster.jsp", "model", rosterFactory.getRoster(new Roster(getDateFrom(req), getDateTill(req))));

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
