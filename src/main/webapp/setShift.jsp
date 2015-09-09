<%@ page import="lv.javaguru.java2.domain.user.User" %>
<%@ page import="java.sql.Date" %>
<%@ page import="lv.javaguru.java2.domain.shift.Shift" %>
<%@ page import="java.util.List" %>
<%@ page import="lv.javaguru.java2.servlet.mvc.data.SetShiftControllerData" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%  SetShiftControllerData data = (SetShiftControllerData) request.getAttribute("model");
    User user = data.getUser();
    Date date = data.getDate();
    long currentShiftId = data.getCurrentShiftId();
    List<Shift> shifts = data.getShifts();
%>
<html>
<head>
    <title><%= user.getFirstName()%> <%= user.getLastName()%> shift on <%= date.toString() %></title>
</head>
<body>
<h2 align="center"><%= user.getFirstName()%> <%= user.getLastName()%> shift on <%= date.toString() %></h2>
<div align="center">
    <a href="/roster/roster">back to Roster without saving changes</a><br>
<form method="post">
    <select name="shift">
        <% for (Shift shift : shifts) { %>
        <option <% if (shift.getId() == currentShiftId) { %> selected <% } %> value = "<%= shift.getId()%>">
            <%= shift.getName()%>
        </option>
        <% } %>
    </select>
    <input type="submit" name="act_update" value="Change">
</form>
</div>
</body>
</html>
