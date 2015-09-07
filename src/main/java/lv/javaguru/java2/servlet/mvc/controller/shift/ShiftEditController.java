package lv.javaguru.java2.servlet.mvc.controller.shift;

import lv.javaguru.java2.core.GenericService;
import lv.javaguru.java2.core.shift.ShiftService;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.servlet.mvc.GenericNewEditMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

import static lv.javaguru.java2.domain.shift.ShiftBuilder.createShift;

@Component
public class ShiftEditController
        extends GenericNewEditMVCController implements MVCController  {
    @Autowired
    ShiftService shiftService;

    @Override
    protected Generic fillParameters(HttpServletRequest req) throws Exception{
        return createShift()
                .withName(req.getParameter("name"))
                .withShiftStarts(req.getParameter("shiftstarts"))
                .withShiftEnds(req.getParameter("shiftends"))
                .build();
    }

    @Override
    protected GenericService getService() {
        return shiftService;
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
    protected String getEditPageAddressJSP() {
        return "/shift.jsp";
    }


}
