package lv.javaguru.java2.servlet.mvc.controller.roster;


import lv.javaguru.java2.core.roster.RosterFactory;
import lv.javaguru.java2.core.shift.ShiftFactory;
import lv.javaguru.java2.core.user.UserFactory;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.roster.Roster;
import lv.javaguru.java2.domain.shift.Shift;
import lv.javaguru.java2.domain.user.User;
import lv.javaguru.java2.servlet.mvc.data.RosterControllerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@Component
public class RosterController {

    @Autowired
    private RosterFactory rosterFactory;

    @Autowired
    private UserFactory userFactory;

    @Autowired
    private ShiftFactory shiftFactory;

    @RequestMapping(value = "/roster")
    public ModelAndView processRequest(HttpServletRequest req) {

        tryToUpdateStartEndDatesInDB(req);

        RosterControllerData data = new RosterControllerData();

        data.setRoster(rosterFactory.getRoster(new Roster(getStartDate(req), getEndDate(req))));

        data.setShifts(shiftFactory.getWorkingShifts());

        return new ModelAndView("/roster.jsp", "model", data);

    }

    private void tryToUpdateStartEndDatesInDB(HttpServletRequest req) {
        Date start;
        Date end;
        try {
            start = getStartDateFromHttpRequest(req);
            end = getEndDateFromHttpRequest(req);
        } catch (Exception e) {
            return;
        }
        User user = userFactory.getByLogin(req.getUserPrincipal().getName());
        user.setRosterShowStartDate(start);
        user.setRosterShowEndDate(end);
        try {
            userFactory.updateObject(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Date getStartDate(HttpServletRequest req) {
        Date result;
        try {
            result = getStartDateFromHttpRequest(req);
        } catch (Exception e){
            try {
                result = getStartDateFromDB(req);
                if (result == null)
                    throw new Exception();
            } catch (Exception e1) {
                result = getStartDateAsFirstDayOfMonth();
            }
        }
        return result;
    }

    private Date getStartDateAsFirstDayOfMonth() {
        return Date.valueOf(LocalDate.now().getYear() + "-"
                + LocalDate.now().getMonthValue() + "-01");
    }

    private Date getStartDateFromDB(HttpServletRequest req) {
        return userFactory.getByLogin(req.getUserPrincipal().getName()).getRosterShowStartDate();
    }

    private Date getStartDateFromHttpRequest(HttpServletRequest req) throws Exception {
        return Date.valueOf(req.getParameter("roster_date_from"));
    }

    private Date getEndDate(HttpServletRequest req) {
        Date result;
        try {
            result = getEndDateFromHttpRequest(req);
        } catch (Exception e){
            try {
                result = getEndDateFromDB(req);
                if (result == null)
                    throw new Exception();
            } catch (Exception e1) {
                result = getEndDateAsLastDayOfMonth();
            }
        }
        return result;
    }

    private Date getEndDateFromHttpRequest(HttpServletRequest req) throws Exception {
        return Date.valueOf(req.getParameter("roster_date_till"));
    }

    private Date getEndDateFromDB(HttpServletRequest req) {
        return userFactory.getByLogin(req.getUserPrincipal().getName()).getRosterShowEndDate();
    }

    private Date getEndDateAsLastDayOfMonth() {
        return Date.valueOf(LocalDate.now().getYear() + "-"
                + LocalDate.now().getMonthValue() + "-"
                + LocalDate.now().lengthOfMonth());
    }

}
