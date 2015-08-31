package lv.javaguru.java2.servlet.mvc.controller.shift;

import lv.javaguru.java2.core.GenericService;
import lv.javaguru.java2.core.shift.ShiftService;
import lv.javaguru.java2.servlet.mvc.GenericNewMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShiftController  extends GenericNewMVCController
        implements MVCController {

    @Autowired
    ShiftService shiftService;

    @Override
    protected String getListPageAddress() {
        return "/shifts.jsp";
    }

    @Override
    protected GenericService getService() {
        return shiftService;
    }
}
