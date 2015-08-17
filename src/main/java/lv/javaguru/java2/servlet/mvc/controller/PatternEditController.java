package lv.javaguru.java2.servlet.mvc.controller;

import lv.javaguru.java2.database.PatternDAO;
import lv.javaguru.java2.database.PatternShiftDAO;
import lv.javaguru.java2.domain.Pattern;
import lv.javaguru.java2.servlet.mvc.GenericEditMVCController;
import lv.javaguru.java2.servlet.mvc.MVCController;
import lv.javaguru.java2.servlet.mvc.MVCModel;
import lv.javaguru.java2.servlet.mvc.data.PatternEditControllerData;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PatternEditController extends GenericEditMVCController<PatternDAO, Pattern> implements MVCController {

    @Autowired
    private PatternDAO patternDAO;

    @Autowired
    private PatternShiftDAO patternShiftDAO;

    protected MVCModel listObject(HttpServletRequest req) throws Exception{
        PatternEditControllerData result = null;
        try {
            long id = getId(req);

            result = new PatternEditControllerData();
            Pattern pattern = patternDAO.getById(id);
            result.setPatternShifts(patternShiftDAO.getAll(id));
            result.setId(pattern.getId());
            result.setName(pattern.getName());

        } catch (NullPointerException e) {
            result = new PatternEditControllerData();
        }

        return new MVCModel(result, getEditPageAddressJSP());
    }

    @Override
    protected void deleteChildObjects(HttpServletRequest req) throws Exception {
        patternShiftDAO.deleteByPatternId(getId(req));
    }

    @Override
    protected String getObjectName() {
        return "Pattern";
    }

    @Override
    protected String getEditPageAddressJSP() {
        return "/pattern.jsp";
    }

    @Override
    protected String getListPageAddress(HttpServletRequest req) {
        return  "/roster/patterns";
    }

    @Override
    protected Pattern getNewInstance() {
        return new Pattern();
    }

    @Override
    protected void fillParameters(HttpServletRequest req, Pattern object) {
        object.setName(req.getParameter("name"));
    }


}
