package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.ShiftDAO;
import lv.javaguru.java2.domain.Shift;
import org.springframework.stereotype.Component;

@Component
public class ShiftController  extends GenericMVCController<ShiftDAO,Shift>
        implements MVCController{

    @Override
    protected String getListPageAddress() {
        return "/shifts.jsp";
    }
}
