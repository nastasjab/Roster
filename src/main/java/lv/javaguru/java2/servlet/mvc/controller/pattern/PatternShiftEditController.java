package lv.javaguru.java2.servlet.mvc.controller.pattern;

import lv.javaguru.java2.core.GenericService;
import lv.javaguru.java2.core.pattern.PatternShiftService;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.pattern.PatternShift;
import lv.javaguru.java2.servlet.mvc.GenericNewEditMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import lv.javaguru.java2.servlet.mvc.MVCModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PatternShiftEditController extends
        GenericNewEditMVCController implements MVCController {

    @Autowired
    private PatternShiftService patternShiftServiceService;

    @Override
    public MVCModel listObject(HttpServletRequest req) throws Exception{

        return new MVCModel(patternShiftServiceService.getObject(getId(req), getPatternId(req)),
                getEditPageAddressJSP());
    }

    @Override
    protected Generic fillParameters(HttpServletRequest req) throws Exception {
        PatternShift object = new PatternShift();
        object.getPattern().setId(getPatternId(req));
        object.getShift().setId(Long.valueOf(req.getParameter("shift")));
        object.setSeqNo(Integer.valueOf(req.getParameter("seqno")));
        if (object.getSeqNo() == 0)
            object.setSeqNo(patternShiftServiceService.getNextSequence(object.getPattern().getId()));
        return  object;
    }

    private long getPatternId(HttpServletRequest req) throws NullPointerException {
        long result = 0;
        try {
            result = Long.decode(req.getParameter("pattern_id"));
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected GenericService getService() {
        return patternShiftServiceService;
    }

    @Override
    protected String getObjectName() {
        return "Pattern shift";
    }

    @Override
    protected String getEditPageAddressJSP() {
        return "/patternshift.jsp";
    }

    @Override
    protected String getListPageAddress(HttpServletRequest req) {
        return "/roster/pattern?id="+getPatternId(req);
    }

}
