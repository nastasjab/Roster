package lv.javaguru.java2.servlet.mvc.controller.shift;

import lv.javaguru.java2.core.GenericFactory;
import lv.javaguru.java2.core.shift.ShiftFactory;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.servlet.mvc.GenericEditMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

import static lv.javaguru.java2.domain.shift.ShiftBuilder.createShift;

@Component
public class ShiftEditController
        extends GenericEditMVCController implements MVCController  {
    @Autowired
    ShiftFactory shiftFactory;

    @Override
    protected Generic fillParameters(HttpServletRequest req) throws Exception{
        return createShift()
                .withName(req.getParameter("name"))
                .withShiftStarts(req.getParameter("shiftstarts"))
                .withShiftEnds(req.getParameter("shiftends"))
                .build();
    }

    @Override
    protected GenericFactory getService() {
        return shiftFactory;
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
