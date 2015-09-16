package lv.javaguru.java2.servlet.mvc.controller.roster;


import lv.javaguru.java2.core.roster.RosterFactory;
import lv.javaguru.java2.core.user.UserFactory;
import lv.javaguru.java2.domain.roster.Roster;
import lv.javaguru.java2.domain.user.User;
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

    @Autowired
    private UserFactory userFactory;

    @RequestMapping(value = "/roster")
    public ModelAndView processRequest(HttpServletRequest req) {

        if (req.getParameter("roster_date_from") != null
                && req.getParameter("roster_date_till") != null) {
            User user = userFactory.getByLogin(req.getUserPrincipal().getName());
            user.setRosterShowStartDate(Date.valueOf(req.getParameter("roster_date_from")));
            user.setRosterShowEndDate(Date.valueOf(req.getParameter("roster_date_till")));
            try {
                userFactory.updateObject(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ModelAndView("/roster.jsp", "model", rosterFactory.getRoster(new Roster(getStartDate(req), getEndDate(req))));

    }

    private Date getStartDate(HttpServletRequest req) {
        Date result;
        try {
            result = Date.valueOf(req.getParameter("roster_date_from"));
        } catch (IllegalArgumentException e){
            try {
                result = userFactory.getByLogin(req.getUserPrincipal().getName()).getRosterShowStartDate();
                if (result == null)
                    throw new Exception();
            } catch (Exception e1) {
                result = Date.valueOf(LocalDate.now().getYear() + "-"
                        + LocalDate.now().getMonthValue() + "-01");
            }
        }
        return result;
    }

    private Date getEndDate(HttpServletRequest req) {
        Date result;
        try {
            result = Date.valueOf(req.getParameter("roster_date_till"));
        } catch (IllegalArgumentException e){
            try {
                result = userFactory.getByLogin(req.getUserPrincipal().getName()).getRosterShowEndDate();
                if (result == null)
                    throw new Exception();
            } catch (Exception e1) {
                result = Date.valueOf(LocalDate.now().getYear() + "-"
                        + LocalDate.now().getMonthValue() + "-"
                        + LocalDate.now().lengthOfMonth());
            }
        }
        return result;
    }

}
