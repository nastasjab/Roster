package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ShiftDAO;
import lv.javaguru.java2.domain.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class ShiftController implements MVCController{

    @Autowired
    private ShiftDAO shiftDAO;

    public MVCModel processRequest(HttpServletRequest req) {

        List<Shift> shifts = new ArrayList<Shift>();

        try {
            shifts = shiftDAO.getAll();
        } catch (DBException e) {
            e.printStackTrace();
        }

        return new MVCModel(shifts, "/shifts.jsp");
    }
}
