package lv.javaguru.java2.servlet.mvc.controller;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ShiftDAO;
import lv.javaguru.java2.domain.Shift;
import lv.javaguru.java2.servlet.mvc.GenericEditMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@Component
public class ShiftEditController
        extends GenericEditMVCController<ShiftDAO, Shift> implements MVCController  {

    @Override
    protected void fillParameters(HttpServletRequest req, Shift object) throws Exception{
        object.setName(req.getParameter("name"));
        object.setShiftStarts(req.getParameter("shiftstarts"));
        object.setShiftEnds(req.getParameter("shiftends"));
        validate(object);
    }

    @Override
    protected String getObjectName() {
        return "Shift";
    }

    @Override
    protected String getListPageAddress(HttpServletRequest req) {
        return "/roster/shifts";
    }

    @Override
    protected Shift getNewInstance() {
        return new Shift();
    }

    @Override
    protected String getEditPageAddressJSP() {
        return "/shift.jsp";
    }

    private void validate(Shift shift) throws Exception {
        String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
        java.util.regex.Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);

        if (!pattern.matcher(shift.getShiftStarts()).matches())
            throw new Exception("Invalid shift start time. Format: HH:MM");

        if (!pattern.matcher(shift.getShiftStarts()).matches())
            throw new Exception("Invalid shift end time. Format: HH:MM");
    }
}
