package lv.javaguru.java2.servlet;


import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PatternDAO;
import lv.javaguru.java2.database.ShiftDAO;
import lv.javaguru.java2.database.ShiftPatternDAO;
import lv.javaguru.java2.database.jdbc.PatternDAOImpl;
import lv.javaguru.java2.database.jdbc.ShiftDAOImpl;
import lv.javaguru.java2.database.jdbc.ShiftPatternDAOImpl;
import lv.javaguru.java2.domain.Shift;
import lv.javaguru.java2.domain.ShiftPattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ShiftPatternServlet extends HttpServlet{

    final PatternDAO patternDAO = new PatternDAOImpl();
    final ShiftPatternDAO shiftPatternDAO = new ShiftPatternDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        add(req);

        delete(req);

        update(req);

        Long patternId = null;
        try {
            patternId = Long.decode(req.getParameter("pattern"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String patternName = null;
        try {
            patternName = patternDAO.getById(patternId).getName();
        } catch (DBException e) {
            e.printStackTrace();
        }

        List<ShiftPattern> shiftPatternList = new ArrayList<ShiftPattern>();
        try {
            shiftPatternList = shiftPatternDAO.getAll(patternId);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        out.println("<html><title>Shifts in Pattern" +
                patternName +
                "</title><body><center><h2>Shifts in Pattern " +
                patternName +
                "</h2><a href=\"/roster/\">Main Menu</a><br>" +
                "<a href=\"/roster/patterns\">back to Patterns</a>" +
                "<form method = \"get\"><input type = \"hidden\" name = \"pattern\" value = " +
                patternId +
                "><table border = 1 cellpadding = 5><tr>" +
                "<th>No</th>" +
                "<th>Shift</th>");
        if (req.getParameter("edit") == null)
            out.println(
                    "<th><input type = \"submit\" name = \"edit\" value = \"Edit\"></th>" +
                            "<th><input type = \"submit\" name = \"delete\" value = \"Delete\"></th>");
        else
            out.println("<th><input type = \"submit\" name = \"update\" value = \"Update\"></th>");

        int seqNo = 1;
        for (ShiftPattern s : shiftPatternList) {
            out.println("<tr><td>" + (seqNo++) +
                    "</td><td>" + surroundBySelectIfNeeded(s.getShiftId(), s.getShiftName(), "name", s.getId(), req.getParameterValues("edit")) +
                    "</td>");
            if (req.getParameter("edit") == null)
                out.println("<td><input type = \"checkbox\" name = \"edit\" value = \"" + s.getId() + "\"></td>" +
                        "</td><td><input type = \"checkbox\" name = \"delete\" value = \"" + s.getId() + "\"></td>");
            else
            if (idInResponse(s.getId(), req.getParameterValues("edit")))
                out.println("<td><input type = \"hidden\" name = \"update\" value = \"" + s.getId() + "\"></td>");
        }

        if (req.getParameter("edit") == null)
            out.println("<tr><td>new</td><td>" +
                    getShiftsDropdownList("add") +
                    "</td><td><input type = \"submit\" name = \"add\" value = \"Add\"></td>");

        out.println("</table></form></center></body></html>");
    }

    private String surroundBySelectIfNeeded(long fieldId, String fieldName, String columnName, long rowId, String[] reqValues) {
        if (reqValues == null)
            return fieldName;
        String prefix = "";
        String postfix = "";
        for (String v : reqValues) {
            long i = 0;
            try {
                i = Long.decode(v);
            } catch (NumberFormatException e) { }
            if (i == rowId) {
                fieldName = getShiftsDropdownList(fieldId, "row_" + rowId + "_update");
            }
        }
        return prefix + fieldName + postfix;
    }

    private String getShiftsDropdownList(String suffix) {
        return getShiftsDropdownList(0, suffix);
    }

    private String getShiftsDropdownList(long selectedShiftId, String suffix) {
        List<Shift> shifts = new ArrayList<Shift>();
        StringBuilder result = new StringBuilder();
        ShiftDAO shiftDAO = new ShiftDAOImpl();
        try {
            shifts = shiftDAO.getAll();
        } catch (DBException e) {
            e.printStackTrace();
        }
        result.append("<select name = \"shift_");
        result.append(suffix);
        result.append("\">");
        for (Shift s : shifts) {
            result.append("<option ");
            if (selectedShiftId == s.getId())
                result.append("selected ");
            result.append("value = \"");
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
            ShiftPattern newPattern = new ShiftPattern();
            Long patternId = null;
            try {
                patternId = Long.valueOf(req.getParameter("pattern"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            int nextSeqNo = 0;
            try {
                nextSeqNo = shiftPatternDAO.getNextSequenceNo(patternId);
            } catch (DBException e) {
                e.printStackTrace();
            }
            newPattern.setShiftId(Long.valueOf(req.getParameter("shift_add")));
            newPattern.setPatternId(patternId);
            newPattern.setSeqNo(nextSeqNo);
            try {
                shiftPatternDAO.create(newPattern);
            } catch (DBException e) {
                e.printStackTrace();
            }
        }
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
                        shiftPatternDAO.delete(id);
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
                    Long shiftId = null;
                    try {
                        shiftId = Long.valueOf(req.getParameter("shift_row_" + id + "_update"));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    try {
                        shiftPatternDAO.updateShiftId(id, shiftId);
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
