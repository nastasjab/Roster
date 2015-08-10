package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ShiftDAO;
import lv.javaguru.java2.database.jdbc.ShiftDAOImpl;
import lv.javaguru.java2.domain.Shift;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ShiftEditController implements MVCController {

    private final ShiftDAO shiftDAO = new ShiftDAOImpl();

    public MVCModel processRequest(HttpServletRequest req) {

        if (req.getParameter("shift_add") != null) {
            add(req);
            return new MVCModel(new MessageContents("New shift created", "New shift created", "/roster/shifts", "back to Shifts List"), "/message.jsp");
        }

        if (req.getParameter("shift_update") != null) {
            update(req);
            return new MVCModel(new MessageContents("Shift updated", "Shift updated", "/roster/shifts", "back to Shifts List"), "/message.jsp");
        }

        if (req.getParameter("shift_delete") != null) {
            delete(req);
            return new MVCModel(new MessageContents("Shift deleted", "Shift deleted", "/roster/shifts", "back to Shifts List"), "/message.jsp");
        }

        Shift result = null;
        try {
            long id = getId(req);
            try {
                result = shiftDAO.getById(id);
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            result = new Shift();
        }

        return new MVCModel(result, "/shift.jsp");
    }

    private long getId(HttpServletRequest req) throws NullPointerException {
        long result = 0;
        try {
            result = Long.decode(req.getParameter("id"));
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return result;
    }

    private Shift add(HttpServletRequest req) {
        Shift shift = new Shift();
        shift.setName(req.getParameter("name"));
        shift.setShiftStarts(req.getParameter("shiftstarts"));
        shift.setShiftEnds(req.getParameter("shiftends"));
        try {
            shiftDAO.create(shift);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return shift;
    }

    private void update(HttpServletRequest req) {
        Shift shift = new Shift();
        shift.setId(getId(req));
        shift.setName(req.getParameter("name"));
        shift.setShiftStarts(req.getParameter("shiftstarts"));
        shift.setShiftEnds(req.getParameter("shiftends"));
        try {
            shiftDAO.update(shift);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    private void delete(HttpServletRequest req) {
        try {
            shiftDAO.delete(getId(req));
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
