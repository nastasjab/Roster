package lv.javaguru.java2.servlet.mvc.controller;

import lv.javaguru.java2.database.ShiftDAO;
import lv.javaguru.java2.domain.Shift;
import lv.javaguru.java2.servlet.mvc.GenericMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import org.springframework.stereotype.Component;

@Component
public class ShiftController  extends GenericMVCController<ShiftDAO,Shift>
        implements MVCController {

    @Override
    protected String getListPageAddress() {
        return "/shifts.jsp";
    }
}