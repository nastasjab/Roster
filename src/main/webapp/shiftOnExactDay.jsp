<%@ page import="lv.javaguru.java2.domain.User" %>
<%@ page import="java.sql.Date" %>
<%@ page import="lv.javaguru.java2.domain.Shift" %>
<%@ page import="java.util.List" %>
<%@ page import="lv.javaguru.java2.servlet.mvc.data.ShiftOnExactDayControllerData" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%  ShiftOnExactDayControllerData data = (ShiftOnExactDayControllerData) request.getAttribute("model");
    User user = data.getUser();
    Date date = data.getShiftOnExactDay().getDate();
    long shiftOnExactDayId = data.getShiftOnExactDay().getId();
    long currentShiftId = data.getShiftOnExactDay().getShift().getId();
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
    <select name="shift_new">
        <option value="0">Select Shift</option>
        <% for (Shift shift : shifts) { %>
        <option <% if (shift.getId() == currentShiftId) { %> selected <% } %> value = "<%= shift.getId()%>">
            <%= shift.getName()%>
        </option>
        <% } %>
    </select>
    <input type="submit" name="act_update" value="Change">
    <input type="hidden" name="id" value="<%= shiftOnExactDayId%>">
    <input type="submit" name="act_delete" value="Delete">
</form>
</div>
</body>
</html>
