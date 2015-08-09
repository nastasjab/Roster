<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="lv.javaguru.java2.domain.RosterMap" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% RosterMap rosterMap = (RosterMap) request.getAttribute("model"); %>
<html>
<head>
    <title>Roster</title>
</head>
<body>
<h2 align="center">Roster</h2>
<div align="center"><a href="/roster">Main Menu</a></div><br>
<form method="get">
<table align="center">
    <th>From</th>
    <th><input type="text" name="roster_date_from" value="<%= rosterMap.getFrom() %>"></th>
    <th>Till</th>
    <th><input type="text" name="roster_date_till" value="<%= rosterMap.getTill() %>"></th>
    <th><input type="submit" name="roster_date_refresh" value="Refresh"></th>
</table><br>
<table border="1" cellpadding="5" align="center">
    <th>User</th>
    <% for(int column = 1; column <= 31; column++) { %>
    <th><%= column %></th>
    <% } %>
    <%-- Start of user listing loop --%>
    <tr><th><%-- User firstname, lastname --%></th>
        <%-- Start of loop diplaying shifts for a user --%>
        <td><%-- shift --%></td>
        <%-- End of loop diplaying shifts for a user --%>
    </tr><%-- End of user listing loop --%>
</table>
</form>
</body>
</html>
