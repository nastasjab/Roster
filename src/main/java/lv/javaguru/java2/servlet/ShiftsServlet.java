package lv.javaguru.java2.servlet;


import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ShiftDAO;
import lv.javaguru.java2.database.jdbc.ShiftDAOImpl;
import lv.javaguru.java2.domain.Shift;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ShiftsServlet extends HttpServlet {
    ShiftDAO shifts = new ShiftDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        add(req);

        delete(req);

        update(req);

        List<Shift> shiftList = new ArrayList<Shift>();
        try {
            shiftList = shifts.getAll();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        out.println("<html><title>Shifts</title><body><center><h2>Shifts</h2>" +
                "<a href=\"/roster/\">Main Menu</a>" +
                "<form method = \"get\" name = \"patterns\">" +
                "<table border = 1 cellpadding = 5><tr>" +
                "<th>No</th>" +
                "<th>Shift</th>" +
                "<th>Begins</th>" +
                "<th>Ends</th>");
        if (req.getParameter("edit") == null)
            out.println(
                    "<th><input type = \"submit\" name = \"edit\" value = \"Edit\"></th>" +
                            "<th><input type = \"submit\" name = \"delete\" value = \"Delete\"></th>");
        else
            out.println("<th><input type = \"submit\" name = \"update\" value = \"Update\"></th>");

        for (Shift s : shiftList) {
            out.println("<tr><td>" + s.getId() +
                    "</td><td>" + surroundByInputIfNeeded(s.getName(), "name", s.getId(), req.getParameterValues("edit")) +
                    "</td><td>" + surroundByInputIfNeeded(s.getShiftStarts(), "starts", s.getId(), req.getParameterValues("edit")) +
                    "</td><td>" + surroundByInputIfNeeded(s.getShiftEnds(), "ends", s.getId(), req.getParameterValues("edit")) +
                    "</td>");
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
                    "<td><input type = \"text\" name = \"new_starts\"></td>" +
                    "<td><input type = \"text\" name = \"new_ends\"></td>" +
                    "<td><input type = \"submit\" name = \"add\" value = \"Add\"></td>");

        out.println("</table></form></center></body></html>");
    }

    private void add(HttpServletRequest req) {
        if (req.getParameter("add") != null) {
            Shift newShift = new Shift();
            newShift.setName(req.getParameter("new_name"));
            newShift.setShiftStarts(req.getParameter("new_starts"));
            newShift.setShiftEnds(req.getParameter("new_ends"));
            try {
                shifts.create(newShift);
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
                        shifts.delete(id);
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
                    Shift updateShift = new Shift();
                    updateShift.setId(id);
                    updateShift.setName(req.getParameter("update_name_" + id));
                    updateShift.setShiftStarts(req.getParameter("update_starts_" + id));
                    updateShift.setShiftEnds(req.getParameter("update_ends_" + id));
                    try {
                        shifts.update(updateShift);
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
