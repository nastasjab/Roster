package lv.javaguru.java2.servlet.mvc.controller.roster;

import lv.javaguru.java2.database.shift.ShiftDAO;
import lv.javaguru.java2.database.roster.ShiftOnExactDayDAO;
import lv.javaguru.java2.database.user.UserDAO;
import lv.javaguru.java2.domain.shift.Shift;
import lv.javaguru.java2.domain.roster.ShiftOnExactDay;
import lv.javaguru.java2.servlet.mvc.*;
import lv.javaguru.java2.servlet.mvc.data.MessageContents;
import lv.javaguru.java2.servlet.mvc.data.ShiftOnExactDayControllerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

@Component
public class ShiftOnExactDayController extends GenericEditMVCController<ShiftOnExactDayDAO, ShiftOnExactDay> implements MVCController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ShiftDAO shiftDAO;

    @Autowired
    private ShiftOnExactDayDAO shiftOnExactDayDAO;

    @Override
    protected MVCModel listObject(HttpServletRequest req) {

        ShiftOnExactDayControllerData result = new ShiftOnExactDayControllerData();

        try {
            result.setUser(userDAO.getById(getUserId(req)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            result.setShifts(shiftDAO.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            result.setShiftOnExactDay(shiftOnExactDayDAO.getShiftOnExactDay(getUserId(req), getDate(req)));
        } catch (IndexOutOfBoundsException e) {
            ShiftOnExactDay shiftOnExactDay = new ShiftOnExactDay();
            shiftOnExactDay.setDate(getDate(req));
            shiftOnExactDay.setUserId(getUserId(req));
            try {
                shiftOnExactDay.setShift(result.getShiftById(getShift(req)));
            } catch (Exception e1) {
                shiftOnExactDay.setShift(new Shift());
            }
            result.setShiftOnExactDay(shiftOnExactDay);
        }

        return new MVCModel(result, "/shiftOnExactDay.jsp");

    }

    private Long getShift(HttpServletRequest req) {
        return Long.valueOf(req.getParameter("shift"));
    }

    private Long getNewShift(HttpServletRequest req) {
        return Long.valueOf(req.getParameter("shift_new"));
    }

    private Date getDate(HttpServletRequest req) {
        return Date.valueOf(req.getParameter("date"));
    }

    private Long getUserId(HttpServletRequest req) {
        return Long.decode(req.getParameter("user"));
    }

    @Override
    protected String getObjectName() {
        return "Shift";
    }

    @Override
    protected String getEditPageAddressJSP() {
        return "/shiftOnExactDay.jsp";
    }

    @Override
    protected String getListPageAddress(HttpServletRequest req) {
        return "/roster/roster";
    }

    @Override
    protected ShiftOnExactDay getNewInstance() {
        return new ShiftOnExactDay();
    }

    @Override
    protected void fillParameters(HttpServletRequest req, ShiftOnExactDay shiftOnExactDay) throws Exception {
        shiftOnExactDay.setUserId(getUserId(req));
        shiftOnExactDay.setDate(getDate(req));
        shiftOnExactDay.setShift(shiftDAO.getById(getNewShift(req)));
    }

    @Override
    protected MVCModel updateObject(HttpServletRequest req) throws Exception {
        ShiftOnExactDay shiftOnExactDay = getNewInstance();
        fillParameters(req, shiftOnExactDay);

        shiftOnExactDayDAO.setShiftOnExactDay(shiftOnExactDay);

        return new MVCModel(
                new MessageContents(
                        getObjectName()+" changed",
                        getObjectName()+" changed",
                        getListPageAddress(req),
                        "Back to Roster"), "/message.jsp");
    }

}
