package lv.javaguru.java2.servlet.mvc.controller;

import lv.javaguru.java2.database.ShiftDAO;
import lv.javaguru.java2.database.PatternShiftDAO;
import lv.javaguru.java2.domain.PatternShift;
import lv.javaguru.java2.servlet.mvc.GenericEditMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import lv.javaguru.java2.servlet.mvc.MVCModel;
import lv.javaguru.java2.servlet.mvc.data.PatternShiftEditControllerData;
import org.hibernate.JDBCException;
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

    public MVCModel listObject(HttpServletRequest req) throws Exception{
        PatternShiftEditControllerData result = new PatternShiftEditControllerData();
        try {
            long id = getId(req);

            PatternShift patternShift = patternShiftDAO.getById(id);
            result.setId(patternShift.getId());
            result.setPatternId(patternShift.getPatternId());
            result.setSeqNo(patternShift.getSeqNo());
            result.getShift().setName(patternShift.getShift().getName());
            result.getShift().setId(patternShift.getShift().getId());

        } catch (NullPointerException e) {
            result = new PatternShiftEditControllerData();
            result.setPatternId(getPatternId(req));
            result.setSeqNo(patternShiftDAO.getNextSequenceNo(getPatternId(req)));
        }


        result.setAllShifts(shiftDAO.getAll());


        return new MVCModel(result, "/patternshift.jsp");
    }

    @Override
    protected void fillParameters(HttpServletRequest req, PatternShift object) throws Exception {
        object.setPatternId(getPatternId(req));
        object.getShift().setId(Long.valueOf(req.getParameter("shift")));
        object.setSeqNo(Integer.valueOf(req.getParameter("seqno")));
        if (object.getSeqNo() == 0)
            object.setSeqNo(patternShiftDAO.getNextSequenceNo(object.getPatternId()));
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
}
