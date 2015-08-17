package lv.javaguru.java2.servlet.mvc.controller;

import lv.javaguru.java2.database.ShiftDAO;
import lv.javaguru.java2.domain.Shift;
import lv.javaguru.java2.servlet.mvc.GenericEditMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

@Component
public class ShiftEditController
        extends GenericEditMVCController<ShiftDAO, Shift> implements MVCController  {

    @Override
    protected void fillParameters(HttpServletRequest req, Shift object) {
        object.setName(req.getParameter("name"));
        object.setShiftStarts(req.getParameter("shiftstarts"));
        object.setShiftEnds(req.getParameter("shiftends"));
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


}
