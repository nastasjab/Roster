package lv.javaguru.java2.servlet.mvc.controller.shift;

import lv.javaguru.java2.core.GenericService;
import lv.javaguru.java2.core.shift.ShiftFactory;
import lv.javaguru.java2.servlet.mvc.GenericMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShiftController  extends GenericMVCController
        implements MVCController {

    @Autowired
    ShiftFactory shiftFactory;

    @Override
    protected String getListPageAddress() {
        return "/shifts.jsp";
    }

    @Override
    protected GenericService getService() {
        return shiftFactory;
    }
}
