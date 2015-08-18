<%@ page import="lv.javaguru.java2.domain.Roster" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="lv.javaguru.java2.domain.User" %>
<%@ page import="java.sql.Date" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Roster roster = (Roster) request.getAttribute("model"); %>
<html>
<head>
    <title>Roster</title>
</head>
<body>
<h2 align="center">Roster</h2>
<div align="center"><a href="/roster">Main Menu</a></div><br>
<form method="post">
<table align="center">
    <th>From</th>
    <th><input type="text" name="roster_date_from" value="<%= roster.getFrom() %>"></th>
    <th>Till</th>
    <th><input type="text" name="roster_date_till" value="<%= roster.getTill() %>"></th>
    <th><input type="submit" name="roster_date_refresh" value="Refresh"></th>
</table><br>
<table border="1" cellpadding="5" align="center">
    <th>User</th>
    <%  for(long epochDay = LocalDate.parse(roster.getFrom().toString()).toEpochDay();
            epochDay <= LocalDate.parse(roster.getTill().toString()).toEpochDay(); epochDay++) { %>
    <th><%= LocalDate.ofEpochDay(epochDay).getDayOfMonth() %></th>
    <% }
    for (User user : roster.getUserList()) { %>
    <tr><th><a href="/roster/userpatterns?user=<%= user.getId()%>"><%= user.getLastName() + " " + user.getFirstName() %></a></th>
        <%  String shift = " ";
            for(long epochDay = LocalDate.parse(roster.getFrom().toString()).toEpochDay();
                epochDay <= LocalDate.parse(roster.getTill().toString()).toEpochDay(); epochDay++) {
        if (roster.getUserShifts(user) != null)
            shift = roster.getUserShifts(user).getShift(Date.valueOf(LocalDate.ofEpochDay(epochDay))).getName(); %>
        <td><%= shift %></td>
        <% } %>
    </tr><% } %>
</table>
</form>
</body>
</html>
