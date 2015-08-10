package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ShiftDAO;
import lv.javaguru.java2.database.jdbc.ShiftDAOImpl;
import lv.javaguru.java2.domain.Shift;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class ShiftController implements MVCController{

    private final ShiftDAO shiftDAO = new ShiftDAOImpl();

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
