package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PatternDAO;
import lv.javaguru.java2.domain.Pattern;
import lv.javaguru.java2.servlet.mvc.domain.PatternEditControllerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PatternEditController extends GenericEditMVCController<PatternDAO, Pattern> implements MVCController {

    @Autowired
    private PatternDAO patternDAO;

    // TODO shiftpatternDAO
  //  @Autowired
  //  private ShiftDAO shiftDAO;

    protected MVCModel listObject(HttpServletRequest req){
        PatternEditControllerData result = null;
        try {
            long id = getId(req);
            try {
                result = new PatternEditControllerData();
                Pattern pattern = patternDAO.getById(id);
                //TODO  !!!!!!!!!!!
                // select from shiftPatternDAO only related to this pattern objects
                //result.setShiftPatterns(shiftDAO.getAll());
                result.setId(pattern.getId());
                result.setName(pattern.getName());

            } catch (DBException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            result = new PatternEditControllerData();
        }

        return new MVCModel(result, getEditPageAddressJSP());
    }

    @Override
    protected void deleteChildObjects(HttpServletRequest req) {
        // TODO cascade delete shifts for this pattern
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
    protected String getListPageAddress() {
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
