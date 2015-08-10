package lv.javaguru.java2.servlet;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PatternDAO;
import lv.javaguru.java2.database.ShiftDAO;
import lv.javaguru.java2.database.ShiftPatternDAO;
import lv.javaguru.java2.database.jdbc.PatternDAOImpl;
import lv.javaguru.java2.database.jdbc.ShiftDAOImpl;
import lv.javaguru.java2.database.jdbc.ShiftPatternDAOImpl;
import lv.javaguru.java2.domain.Pattern;
import lv.javaguru.java2.domain.Shift;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class PatternsServlet extends HttpServlet {
    final PatternDAO patterns = new PatternDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        add(req);

        delete(req);

        update(req);

        List<Pattern> patternList = new ArrayList<Pattern>();
        try {
            patternList = patterns.getAll();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        out.println("<html><title>Patterns</title><body><center><h2>Patterns</h2>" +
                "<a href=\"/roster/\">Main Menu</a>" +
                "<form method = \"get\" name = \"patternDAO\">" +
                "<table border = 1 cellpadding = 5><tr>" +
                "<th>No</th>" +
                "<th>Name</th>");
        if (req.getParameter("edit") == null)
            out.println(
                    "<th><input type = \"submit\" name = \"edit\" value = \"Edit\"></th>" +
                            "<th><input type = \"submit\" name = \"delete\" value = \"Delete\"></th>");
        else
            out.println("<th><input type = \"submit\" name = \"update\" value = \"Update\"></th>");

        for (Pattern s : patternList) {
            out.println("<tr><td>" + s.getId() +
                    "</td><td>" + surroundByInputIfNeeded(s.getName(), "name", s.getId(), req.getParameterValues("edit")) +
                    "<br>" + getPattern(s.getId()) +
                    "<br><right><a href=\"/roster/shiftpattern?pattern=" +
                    s.getId() +
                    "\">Edit Pattern</a></right></td>");
            if (req.getParameter("edit") == null)
                out.println("<td><input type = \"checkbox\" name = \"edit\" value = \"" + s.getId() + "\"></td>" +
                        "</td><td><input type = \"checkbox\" name = \"delete\" value = \"" + s.getId() + "\"></td>");
            else
            if (idInResponse(s.getId(), req.getParameterValues("edit")))
                out.println("<td><input type = \"hidden\" name = \"update\" value = \"" + s.getId() + "\"></td>");
        }

        if (req.getParameter("edit") == null)
            out.println("<tr>" +
                    "<td>new</td><td><input type = \"text\" name = \"new_name\"></td>" +
                    "<td><input type = \"submit\" name = \"add\" value = \"Add\"></td>");

        out.println("</table></form></center></body></html>");
    }

    private String getPattern(long id) {
        StringBuilder result = new StringBuilder();
        List<Shift> shiftsInPattern = new ArrayList<Shift>();
        ShiftPatternDAO shiftPatternDAO = new ShiftPatternDAOImpl();

        try {
            shiftsInPattern = shiftPatternDAO.getShiftsByPatternId(id);
        } catch (DBException e) {
            e.printStackTrace();
        }

        if (shiftsInPattern == null || shiftsInPattern.size() == 0)
            return "<i>Pattern has no shifts</i>";

        for (Shift s : shiftsInPattern){
            result.append(s.getName());
            result.append(" ");
            /*<input type = \"submit\" name = \"pattern_");
            result.append(s.getId());
            result.append("_shift_remove\" value = \"Remove\"><br>"); */
        }
        /*
        result.append(getShiftsDropdownList(id));
        result.append("<input type = \"hidden\" name = \"pattern_id_shift_add\" value = \"");
        result.append(id);
        result.append("\"> <input type = \"submit\" name = \"pattern_shift_add\" value = \"Add Shift\"><br>"); */

        return result.toString();
    }

    private String getShiftsDropdownList(long patternId) {
        List<Shift> shifts = new ArrayList<Shift>();
        StringBuilder result = new StringBuilder();
        ShiftDAO shiftDAO = new ShiftDAOImpl();
        try {
            shifts = shiftDAO.getAll();
        } catch (DBException e) {
            e.printStackTrace();
        }
        result.append("<select name = \"pattern_");
        result.append(patternId);
        result.append("_shift_add");
        result.append("\">");
        for (Shift s : shifts) {
            result.append("<option value = \"");
            result.append(s.getId());
            result.append("\">");
            result.append(s.getName());
            result.append("</option>");
        }
        result.append("</select>");

        return result.toString();
    }

    private void add(HttpServletRequest req) {
        if (req.getParameter("add") != null) {
            Pattern newPattern = new Pattern();
            newPattern.setName(req.getParameter("new_name"));
            try {
                patterns.create(newPattern);
            } catch (DBException e) {
                e.printStackTrace();
            }
        }

        /*
        if (req.getParameter("pattern_shift_add") != null) {
            ShiftPatternDAO shiftPatternDAO = new ShiftPatternDAOImpl();
            ShiftPattern shiftPattern = new ShiftPattern();
            Long patternId = null;
            try {
                patternId = Long.valueOf(req.getParameter("pattern_id_shift_add"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            int nextSeqNo = 0;
            try {
                nextSeqNo = shiftPatternDAO.getNextSequenceNo(patternId);
            } catch (DBException e) {
                e.printStackTrace();
            }
            Long shiftId = null;
            shiftId = Long.valueOf(req.getParameter("pattern_" + patternId + "_shift_add"));
            shiftPattern.setPatternId(patternId);
            shiftPattern.setSeqNo(nextSeqNo);
            shiftPattern.setShiftId(shiftId);
            try {
                shiftPatternDAO.create(shiftPattern);
            } catch (DBException e) {
                e.printStackTrace();
            }
        }*/
    }

    private void delete(HttpServletRequest req) {
        if (req.getParameter("delete") != null) {
            for (String v : req.getParameterValues("delete")) {
                long id = 0;
                try {
                    id = Long.decode(v);
                } catch (NumberFormatException e) { }
                if (id != 0)
                    try {
                        patterns.delete(id);
                    } catch (DBException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    private void update(HttpServletRequest req) {
        if (req.getParameter("update") != null) {
            for (String v : req.getParameterValues("update")) {
                long id = 0;
                try {
                    id = Long.decode(v);
                } catch (NumberFormatException e) { }
                if (id != 0) {
                    Pattern updatePattern = new Pattern();
                    updatePattern.setId(id);
                    updatePattern.setName(req.getParameter("update_name_" + id));
                    try {
                        patterns.update(updatePattern);
                    } catch (DBException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String surroundByInputIfNeeded(String s, String fieldName, long rowId, String[] reqValues) {
        if (reqValues == null)
            return s;
        String prefix = "";
        String postfix = "";
        for (String v : reqValues) {
            long i = 0;
            try {
                i = Long.decode(v);
            } catch (NumberFormatException e) { }
            if (i == rowId) {
                prefix = "<input type =\"text\" name = \"update_" + fieldName + "_" + rowId + "\" value = \"";
                postfix = "\">";
            }
        }
        return prefix + s + postfix;
    }

    private boolean idInResponse(long id, String[] reqValues) {
        if (reqValues == null)
            return false;
        for (String v : reqValues) {
            long i = 0;
            try {
                i = Long.decode(v);
            } catch (NumberFormatException e) {
            }
            if (i == id)
                return true;

        }
        return false;
    }
}
