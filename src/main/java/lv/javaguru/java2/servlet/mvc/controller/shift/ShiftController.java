package lv.javaguru.java2.servlet.mvc.controller.shift;

import lv.javaguru.java2.core.GenericService;
import lv.javaguru.java2.core.pattern.PatternService;
import lv.javaguru.java2.core.shift.ShiftService;
import lv.javaguru.java2.database.ShiftDAO;
import lv.javaguru.java2.domain.Shift;
import lv.javaguru.java2.servlet.mvc.GenericMVCController;
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
