package lv.javaguru.java2.servlet.mvc.controller.shift;

import lv.javaguru.java2.core.GenericFactory;
import lv.javaguru.java2.core.shift.ShiftFactory;
import lv.javaguru.java2.servlet.mvc.GenericSpringMVCController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ShiftController  extends GenericSpringMVCController
        {

    @RequestMapping(value = "/shifts", method = {RequestMethod.GET})
    public ModelAndView processRequest(HttpServletRequest req) {
       return  super.processRequest(req);
    }

    @Autowired
    ShiftFactory shiftFactory;

    @Override
    protected String getListPageAddress() {
        return "/shifts.jsp";
    }

    @Override
    protected GenericFactory getService() {
        return shiftFactory;
    }
}
