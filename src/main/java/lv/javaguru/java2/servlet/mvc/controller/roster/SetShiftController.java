package lv.javaguru.java2.servlet.mvc.controller.roster;

import lv.javaguru.java2.core.NoShiftFoundException;
import lv.javaguru.java2.core.roster.InvalidShiftException;
import lv.javaguru.java2.core.roster.RosterService;
import lv.javaguru.java2.core.shift.ShiftFactory;
import lv.javaguru.java2.core.user.UserService;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.shift.Shift;
import lv.javaguru.java2.domain.user.User;
import lv.javaguru.java2.servlet.mvc.*;
import lv.javaguru.java2.servlet.mvc.data.MessageContents;
import lv.javaguru.java2.servlet.mvc.data.SetShiftControllerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static lv.javaguru.java2.domain.shift.ShiftBuilder.createShift;

@Component
public class SetShiftController implements MVCController {

    @Autowired
    private UserService userService;

    @Autowired
    private ShiftFactory shiftFactory;

    @Autowired
    private RosterService rosterService;

    public MVCModel processRequest(HttpServletRequest req) {

        if (req.getParameter("act_update") != null)
            return update(req);

        SetShiftControllerData result = new SetShiftControllerData();

        result.setDate(getDate(req));

        try {
            result.setUser((User) userService.getObject(getUserId(req)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        result.setShifts(rosterService.getAvailableShifts(getDate(req), getUserId(req)));

        try {
            result.setCurrentShiftId(rosterService.getShift(getDate(req), getUserId(req)).getId());
        } catch (NoShiftFoundException e) {
            result.setCurrentShiftId(0);
        }

        return new MVCModel(result, "/setShift.jsp");

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

    protected MVCModel update(HttpServletRequest req) {

        try {
            rosterService.setShift(getDate(req), getUserId(req), getShift(req));
        } catch (InvalidShiftException e) {
            return new MVCModel(
                    new MessageContents(
                            "Invalid Shift",
                            "Invalid Shift",
                            "/roster/roster",
                            "Back to Roster"), "/error.jsp");
        }

        return new MVCModel(
                new MessageContents(
                        "Shift changed",
                        "Shift changed",
                        "/roster/roster",
                        "Back to Roster"), "/message.jsp");
    }

}
