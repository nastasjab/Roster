package lv.javaguru.java2.servlet;


import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.database.jdbc.UserDAOImpl;
import lv.javaguru.java2.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class UsersServlet extends HttpServlet {

    UserDAO users = new UserDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        add(req);

        delete(req);

        update(req);

        List<User> userList = new ArrayList<User>();
        try {
            userList = users.getAll();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        out.println("<html><title>Users</title><body><center><h2>Users</h2>" +
                "<a href=\"/roster/\">Main Menu</a>" +
                "<form method = \"get\" name = \"patterns\">" +
                "<table border = 1 cellpadding = 5><tr>" +
                "<th>No</th>" +
                "<th>Login</th>" +
                "<th>First Name</th>" +
                "<th>Last Name</th>" +
                "<th>type</th>" +
                "<th>email</th>" +
                "<th>Phone</th>");
        if (req.getParameter("edit") == null)
            out.println(
                "<th><input type = \"submit\" name = \"edit\" value = \"Edit\"></th>" +
                "<th><input type = \"submit\" name = \"delete\" value = \"Delete\"></th>");
        else
            out.println("<th><input type = \"submit\" name = \"update\" value = \"Update\"></th>");

        for (User u : userList) {
            out.println("<tr><td>" + u.getId() +
                    "</td><td>" + surroundByInputIfNeeded(u.getLogin(), "login", u.getId(), req.getParameterValues("edit")) +
                    "</td><td>" + surroundByInputIfNeeded(u.getFirstName(), "firstname", u.getId(), req.getParameterValues("edit")) +
                    "</td><td>" + surroundByInputIfNeeded(u.getLastName(), "lastname", u.getId(), req.getParameterValues("edit")) +
                    "</td><td>" + surroundByInputIfNeeded(u.getUserType(), "usertype", u.getId(), req.getParameterValues("edit")) +
                    "</td><td>" + surroundByInputIfNeeded(u.getEmail(), "email", u.getId(), req.getParameterValues("edit")) +
                    "</td><td>" + surroundByInputIfNeeded(u.getPhone(), "phone", u.getId(), req.getParameterValues("edit")) +
                    "</td>");
            if (req.getParameter("edit") == null)
                out.println("<td><input type = \"checkbox\" name = \"edit\" value = \"" + u.getId() + "\"></td>" +
                            "</td><td><input type = \"checkbox\" name = \"delete\" value = \"" + u.getId() + "\"></td>");
            else
            if (idInResponse(u.getId(), req.getParameterValues("edit")))
                out.println("<td><input type = \"hidden\" name = \"update\" value = \"" + u.getId() + "\"></td>");
        }

        if (req.getParameter("edit") == null)
            out.println("<tr>" +
                    "<td>new</td><td><input type = \"text\" name = \"new_username\"></td>" +
                    "<td><input type = \"text\" name = \"new_firstname\"></td>" +
                    "<td><input type = \"text\" name = \"new_lastname\"></td>" +
                    "<td><select name = \"new_usertype\"><option value = \"A\">Admin</option><option selected value = \"U\">User</option></select></td>" +
                    "<td><input type = \"text\" name = \"new_email\"></td>" +
                    "<td><input type = \"text\" name = \"new_phone\"></td>" +
                    "<td><input type = \"submit\" name = \"add\" value = \"Add\"></td>");

        out.println("</table></form></center></body></html>");
    }

    private void add(HttpServletRequest req) {
        if (req.getParameter("add") != null) {
            User newUser = new User();
            newUser.setLogin(req.getParameter("new_username"));
            newUser.setPassword("empty");
            newUser.setFirstName(req.getParameter("new_firstname"));
            newUser.setLastName(req.getParameter("new_lastname"));
            newUser.setUserType(req.getParameter("new_usertype"));
            newUser.setEmail(req.getParameter("new_email"));
            newUser.setPhone(req.getParameter("new_phone"));
            try {
                users.create(newUser);
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
                        users.delete(id);
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
                    User updateUser = new User();
                    updateUser.setId(id);
                    updateUser.setLogin(req.getParameter("update_login_" + id));
                    updateUser.setPassword("empty");
                    updateUser.setFirstName(req.getParameter("update_firstname_" + id));
                    updateUser.setLastName(req.getParameter("update_lastname_" + id));
                    updateUser.setUserType(req.getParameter("update_usertype_" + id));
                    updateUser.setEmail(req.getParameter("update_email_" + id));
                    updateUser.setPhone(req.getParameter("update_phone_" + id));
                    try {
                        users.update(updateUser);
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
