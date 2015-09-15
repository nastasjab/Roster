package lv.javaguru.java2.servlet.mvc.controller.roster;

import lv.javaguru.java2.core.NoShiftFoundException;
import lv.javaguru.java2.core.roster.InvalidShiftException;
import lv.javaguru.java2.core.roster.RosterFactory;
import lv.javaguru.java2.core.user.UserFactory;
import lv.javaguru.java2.domain.user.User;
import lv.javaguru.java2.servlet.mvc.*;
import lv.javaguru.java2.servlet.mvc.data.MessageContents;
import lv.javaguru.java2.servlet.mvc.data.SetShiftControllerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

@Controller
@Component
public class SetShiftController {

    @Autowired
    private UserFactory userFactory;

    @Autowired
    private RosterFactory rosterFactory;

    @RequestMapping(value = "/setshift")
    public ModelAndView processRequest(HttpServletRequest req) {

        if (req.getParameter("act_update") != null)
            return update(req);

        SetShiftControllerData result = new SetShiftControllerData();

        result.setDate(getDate(req));

        try {
            result.setUser((User) userFactory.getObject(getUserId(req)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        result.setShifts(rosterFactory.getAvailableShifts(getDate(req), getUserId(req)));

        try {
            result.setCurrentShiftId(rosterFactory.getShift(getDate(req), getUserId(req)).getId());
        } catch (NoShiftFoundException e) {
            result.setCurrentShiftId(0);
        }

        return new ModelAndView("/setShift.jsp", "model", result);

    }

    private Long getShift(HttpServletRequest req) {
        return Long.valueOf(req.getParameter("shift"));
    }

    private Date getDate(HttpServletRequest req) {
        return Date.valueOf(req.getParameter("date"));
    }

    private Long getUserId(HttpServletRequest req) {
        return Long.decode(req.getParameter("user"));
    }

    protected ModelAndView update(HttpServletRequest req) {

        try {
            rosterFactory.setShift(getDate(req), getUserId(req), getShift(req));
        } catch (InvalidShiftException e) {
            return new ModelAndView("/error.jsp", "model",
                    new MessageContents(
                            "Invalid Shift",
                            "Invalid Shift",
                            "/roster/roster",
                            "Back to Roster"));
        }

        return new ModelAndView("/message.jsp", "model",
                new MessageContents(
                        "Shift changed",
                        "Shift changed",
                        "/roster/roster",
                        "Back to Roster"));
    }

}
