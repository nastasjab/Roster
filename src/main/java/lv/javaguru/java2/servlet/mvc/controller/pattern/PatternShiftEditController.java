package lv.javaguru.java2.servlet.mvc.controller.pattern;

import lv.javaguru.java2.core.GenericService;
import lv.javaguru.java2.core.pattern.PatternShiftFactory;
import lv.javaguru.java2.domain.Generic;
import lv.javaguru.java2.domain.pattern.PatternShift;
import lv.javaguru.java2.servlet.mvc.GenericNewEditMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import lv.javaguru.java2.servlet.mvc.MVCModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static lv.javaguru.java2.domain.pattern.PatternShiftBuilder.createPatternShift;

@Component
public class PatternShiftEditController extends
        GenericNewEditMVCController implements MVCController {

    @Autowired
    private PatternShiftFactory patternShiftFactory;

    @Override
    public MVCModel listObject(HttpServletRequest req) throws Exception{

        return new MVCModel(patternShiftFactory.getObject(getId(req), getPatternId(req)),
                getEditPageAddressJSP());
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
        return patternShiftFactory;
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
