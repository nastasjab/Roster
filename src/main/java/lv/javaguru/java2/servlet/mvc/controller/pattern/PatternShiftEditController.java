package lv.javaguru.java2.servlet.mvc.controller.pattern;

import lv.javaguru.java2.core.GenericFactory;
import lv.javaguru.java2.core.pattern.PatternShiftFactory;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.pattern.PatternShift;
import lv.javaguru.java2.servlet.mvc.GenericMVCController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static lv.javaguru.java2.domain.pattern.PatternShiftBuilder.createPatternShift;

@Controller
@Component
public class PatternShiftEditController extends GenericMVCController {

    @Autowired
    private PatternShiftFactory patternShiftFactory;

    @RequestMapping (value = "/patternshift")
    public ModelAndView processRequest(HttpServletRequest req) {
        return super.processRequest(req);
    }

    @Override
    public ModelAndView listObject(HttpServletRequest req) throws Exception{
        return new ModelAndView(getEditPageAddressJSP(req), "model",
                patternShiftFactory.getObject(getId(req), getPatternId(req)));
    }

    @Override
    protected Generic fillParameters(HttpServletRequest req) throws Exception {
        PatternShift object = createPatternShift()
                .withPatternId(getPatternId(req))
                .withShiftId(Long.valueOf(req.getParameter("shift")))
                .withSeqNo(Integer.valueOf(req.getParameter("seqno"))).build();

        if (object.getSeqNo() == 0)
            object.setSeqNo(patternShiftFactory.getNextSequence(object.getPattern().getId()));
        return  object;
    }

    private long getPatternId(HttpServletRequest req) throws Exception {
        try {
            return Long.decode(req.getParameter("pattern"));
        } catch (NumberFormatException e){
            throw new Exception("Missing patternId");
        }
    }

    @Override
    protected GenericFactory getService() {
        return patternShiftFactory;
    }

    @Override
    protected String getObjectName() {
        return "Pattern shift";
    }

    @Override
    protected String getListPageAddress(HttpServletRequest req) throws Exception {
        return "/roster/patterns?id="+getPatternId(req);
    }

    @Override
    protected String getListPageAddressJSP(HttpServletRequest req) {
        return null;
    }

    @Override
    protected String getEditPageAddressJSP(HttpServletRequest req) {
        return "/patternshift.jsp";
    }

}
