package lv.javaguru.java2.servlet.mvc.controller.shift;

import lv.javaguru.java2.core.GenericFactory;
import lv.javaguru.java2.core.shift.ShiftFactory;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.servlet.mvc.GenericMVCController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static lv.javaguru.java2.domain.shift.ShiftBuilder.createShift;

@Controller
@Component
public class ShiftController  extends GenericMVCController {

    @Autowired
    ShiftFactory shiftFactory;

    @RequestMapping(value = "/shifts")
    @Override
    public ModelAndView processRequest(HttpServletRequest req) {
        return super.processRequest(req);
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
    protected String getListPageAddressJSP(HttpServletRequest req) {
        return "/shifts.jsp";
    }

    @Override
    protected String getEditPageAddressJSP(HttpServletRequest req) {
        return "/shift.jsp";
    }

    @Override
    protected Generic fillParameters(HttpServletRequest req) throws Exception {
        return createShift()
                .withName(req.getParameter("name"))
                .withShiftStarts(req.getParameter("shiftstarts"))
                .withShiftEnds(req.getParameter("shiftends"))
                .build();
    }
}
