package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ShiftDAO;
import lv.javaguru.java2.database.jdbc.ShiftDAOImpl;
import lv.javaguru.java2.domain.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Component
public class ShiftEditController extends GenericEditMVCController<ShiftDAO, Shift> implements MVCController {

    @Autowired
    private ShiftDAO shiftDAO;

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
    protected String getListPageAddress() {
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
