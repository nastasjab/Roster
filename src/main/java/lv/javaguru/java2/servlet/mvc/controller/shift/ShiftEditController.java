package lv.javaguru.java2.servlet.mvc.controller.shift;

import lv.javaguru.java2.core.GenericFactory;
import lv.javaguru.java2.core.shift.ShiftFactory;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.servlet.mvc.GenericSpringEditMVCController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static lv.javaguru.java2.domain.shift.ShiftBuilder.createShift;

@Controller
public class ShiftEditController
        extends GenericSpringEditMVCController {
    @Autowired
    ShiftFactory shiftFactory;

    @RequestMapping(value = "/shift")
    public ModelAndView processRequestGet(HttpServletRequest req) {
        return  super.processRequest(req);
    }


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
