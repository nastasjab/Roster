<%@ page import="lv.javaguru.java2.domain.roster.Roster" %>
<%@ page import="lv.javaguru.java2.domain.user.User" %>
<%@ page import="lv.javaguru.java2.domain.shift.Shift" %>
<%@ page import="lv.javaguru.java2.domain.Dates" %>
<%@ page import="static lv.javaguru.java2.domain.shift.ShiftBuilder.createShift" %>
<%@ page import="lv.javaguru.java2.servlet.mvc.data.RosterControllerData" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%  RosterControllerData data = (RosterControllerData) request.getAttribute("model");
    Roster roster = data.getRoster();
    List<Shift> workingShifts = data.getShifts();
%>
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
    <th><input type="date" name="roster_date_from" value="<%= roster.getFrom() %>"></th>
    <th>Till</th>
    <th><input type="date" name="roster_date_till" value="<%= roster.getTill() %>"></th>
    <th><input type="submit" name="roster_dates_refresh" value="Refresh"></th>
</table></form>
<table border="1" cellpadding="5" align="center">
    <th>User</th>
    <%  for(long epochDay = Dates.toEpochDay(roster.getFrom());
            epochDay <= Dates.toEpochDay(roster.getTill()); epochDay++) { %>
    <th><%= Dates.getDayOfMonth(epochDay) %></th>
    <% } %>
    <%  for(Shift workingShift : workingShifts) {
        %><tr><th>Staff in shift <%= workingShift.getName()%></th><%
            for(long epochDay = Dates.toEpochDay(roster.getFrom());
                epochDay <= Dates.toEpochDay(roster.getTill()); epochDay++) { %>
    <td><%= roster.getStaffInShift(Dates.toSqlDate(epochDay), workingShift) %></td>
    <%      }
        } %></tr>
    <% for (User user : roster.getUserList()) { %>
    <tr><th><a href="/roster/userpatterns?user=<%= user.getId()%>"><%= user.getLastName() + " " + user.getFirstName() %></a></th>
        <%  for(long epochDay = Dates.toEpochDay(roster.getFrom());
                epochDay <= Dates.toEpochDay(roster.getTill()); epochDay++) {
            Shift shift = createShift().build();
            shift.setName("&nbsp");
            if (roster.getUserShifts(user).getShift(Dates.toSqlDate(epochDay)) != null)
            shift = roster.getUserShifts(user).getShift(Dates.toSqlDate(epochDay)); %>
        <td><a href="/roster/setshift?user=<%= user.getId()%>&date=<%= Dates.toSqlDate(epochDay)%>">
            <%= shift.getName() %></a></td>
        <% } %>
    </tr><% } %>
</table>
</body>
</html>
