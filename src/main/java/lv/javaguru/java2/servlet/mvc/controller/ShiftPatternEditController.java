package lv.javaguru.java2.servlet.mvc.controller;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ShiftDAO;
import lv.javaguru.java2.database.ShiftPatternDAO;
import lv.javaguru.java2.domain.ShiftPattern;
import lv.javaguru.java2.servlet.mvc.MVCController;
import lv.javaguru.java2.servlet.mvc.MVCModel;
import lv.javaguru.java2.servlet.mvc.MessageContents;
import lv.javaguru.java2.servlet.mvc.data.ShiftPatternEditControllerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ShiftPatternEditController implements MVCController {

    @Autowired
    private ShiftPatternDAO shiftpatternDAO;

    @Autowired
    private ShiftDAO shiftDAO;

    public MVCModel processRequest(HttpServletRequest req) {

        if (req.getParameter("shiftpattern_add") != null) {
            add(req);
            return new MVCModel(new MessageContents("New shiftpattern created", "New shiftpattern created", "/roster/pattern?id="+getPatternId(req), "back to ShiftPattern List"), "/message.jsp");
        }

        if (req.getParameter("shiftpattern_update") != null) {
            update(req);
            return new MVCModel(new MessageContents("ShiftPattern updated", "ShiftPattern updated", "/roster/pattern?id="+getPatternId(req), "back to ShiftPattern List"), "/message.jsp");
        }

        if (req.getParameter("shiftpattern_delete") != null) {
            delete(req);
            return new MVCModel(new MessageContents("ShiftPattern deleted", "ShiftPattern deleted", "/roster/pattern?id="+getPatternId(req), "back to ShiftPattern List"), "/message.jsp");
        }

        ShiftPatternEditControllerData result = new ShiftPatternEditControllerData();
        try {
            long id = getId(req);
            try {
                ShiftPattern shiftPattern = shiftpatternDAO.getById(id);
                result.setId(shiftPattern.getId());
                result.setPatternId(shiftPattern.getPatternId());
                result.setSeqNo(shiftPattern.getSeqNo());
                result.setShiftName(shiftPattern.getShiftName());
                result.setShiftId(shiftPattern.getShiftId());
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            result = new ShiftPatternEditControllerData();
            result.setPatternId(getPatternId(req));
        }

        try {
            result.setAllShifts(shiftDAO.getAll());
        } catch (DBException e) {
            e.printStackTrace();
        }

        return new MVCModel(result, "/shiftPattern.jsp");
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

    private long getPatternId(HttpServletRequest req) throws NullPointerException {
        long result = 0;
        try {
            result = Long.decode(req.getParameter("pattern_id"));
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return result;
    }

    private ShiftPattern add(HttpServletRequest req) {
        ShiftPattern shiftpattern = new ShiftPattern();
        shiftpattern.setPatternId(getPatternId(req));
        shiftpattern.setShiftId(Long.valueOf(req.getParameter("shift")));
        shiftpattern.setSeqNo(Integer.valueOf(req.getParameter("seqno")));
        try {
            shiftpatternDAO.create(shiftpattern);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return shiftpattern;
    }

    private void update(HttpServletRequest req) {
        ShiftPattern shiftpattern = new ShiftPattern();
        shiftpattern.setId(getId(req));
        shiftpattern.setPatternId(getPatternId(req));
        shiftpattern.setShiftId(Long.valueOf(req.getParameter("shift")));
        shiftpattern.setSeqNo(Integer.valueOf(req.getParameter("seqno")));
        try {
            shiftpatternDAO.update(shiftpattern);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    private void delete(HttpServletRequest req) {
        try {
            shiftpatternDAO.delete(getId(req));
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
