package lv.javaguru.java2.servlet.mvc.controller.roster;

import lv.javaguru.java2.database.shift.ShiftDAO;
import lv.javaguru.java2.database.roster.SingleShiftDAO;
import lv.javaguru.java2.database.user.UserDAO;
import lv.javaguru.java2.domain.shift.Shift;
import lv.javaguru.java2.domain.roster.SingleShift;
import lv.javaguru.java2.servlet.mvc.*;
import lv.javaguru.java2.servlet.mvc.data.MessageContents;
import lv.javaguru.java2.servlet.mvc.data.ShiftOnExactDayControllerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

@Component
public class SingleShiftController extends GenericEditMVCController<SingleShiftDAO, SingleShift> implements MVCController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ShiftDAO shiftDAO;

    @Autowired
    private SingleShiftDAO singleShiftDAO;

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
            result.setSingleShift(singleShiftDAO.getShiftOnExactDay(getUserId(req), getDate(req)));
        } catch (IndexOutOfBoundsException e) {
            SingleShift singleShift = new SingleShift();
            singleShift.setDate(getDate(req));
            singleShift.setUserId(getUserId(req));
            try {
                singleShift.setShift(result.getShiftById(getShift(req)));
            } catch (Exception e1) {
                singleShift.setShift(new Shift());
            }
            result.setSingleShift(singleShift);
        }

        return new MVCModel(result, "/singleShift.jsp");

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
        return "/singleShift.jsp";
    }

    @Override
    protected String getListPageAddress(HttpServletRequest req) {
        return "/roster/roster";
    }

    @Override
    protected SingleShift getNewInstance() {
        return new SingleShift();
    }

    @Override
    protected void fillParameters(HttpServletRequest req, SingleShift singleShift) throws Exception {
        singleShift.setUserId(getUserId(req));
        singleShift.setDate(getDate(req));
        singleShift.setShift(shiftDAO.getById(getNewShift(req)));
    }

    @Override
    protected MVCModel updateObject(HttpServletRequest req) throws Exception {
        SingleShift singleShift = getNewInstance();
        fillParameters(req, singleShift);

        singleShiftDAO.setShiftOnExactDay(singleShift);

        return new MVCModel(
                new MessageContents(
                        getObjectName()+" changed",
                        getObjectName()+" changed",
                        getListPageAddress(req),
                        "Back to Roster"), "/message.jsp");
    }

}
