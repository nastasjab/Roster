package lv.javaguru.java2.servlet.mvc.controller;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ShiftDAO;
import lv.javaguru.java2.database.PatternShiftDAO;
import lv.javaguru.java2.domain.PatternShift;
import lv.javaguru.java2.servlet.mvc.GenericEditMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import lv.javaguru.java2.servlet.mvc.MVCModel;
import lv.javaguru.java2.servlet.mvc.MessageContents;
import lv.javaguru.java2.servlet.mvc.data.PatternShiftEditControllerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PatternShiftEditController extends
        GenericEditMVCController<PatternShiftDAO, PatternShift> implements MVCController {

    @Autowired
    private PatternShiftDAO patternShiftDAO;

    @Autowired
    private ShiftDAO shiftDAO;

    public MVCModel listObject(HttpServletRequest req) {
        PatternShiftEditControllerData result = new PatternShiftEditControllerData();
        try {
            long id = getId(req);
            try {
                PatternShift patternShift = patternShiftDAO.getById(id);
                result.setId(patternShift.getId());
                result.setPatternId(patternShift.getPatternId());
                result.setSeqNo(patternShift.getSeqNo());
                result.getShift().setName(patternShift.getShift().getName());
                result.getShift().setId(patternShift.getShift().getId());
            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            result = new PatternShiftEditControllerData();
            result.setPatternId(getPatternId(req));
        }

        try {
            result.setAllShifts(shiftDAO.getAll());
        } catch (DBException e) {
            e.printStackTrace();
        }

        return new MVCModel(result, "/patternshift.jsp");
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

    @Override
    protected PatternShift getNewInstance() {
        return new PatternShift();
    }

    @Override
    protected void fillParameters(HttpServletRequest req, PatternShift object) {
        object.setPatternId(getPatternId(req));
        object.getShift().setId(Long.valueOf(req.getParameter("shift")));
        object.setSeqNo(Integer.valueOf(req.getParameter("seqno")));
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
}
