<%@ page import="lv.javaguru.java2.domain.user.User" %>
<%@ page import="java.util.List" %>
<%@ page import="lv.javaguru.java2.domain.user.UserPattern" %>
<%@ page import="lv.javaguru.java2.servlet.mvc.data.UserPatternControllerData" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%  UserPatternControllerData data = (UserPatternControllerData) request.getAttribute("model");
    User user = data.getUser();
    List<UserPattern> userPatterns = data.getUserPatterns();
%>
<html>
<head>
    <title><%= user.getLastName() %> <%= user.getFirstName() %> Pattern</title>
</head>
<body>
<h2 align="center"><%= user.getLastName() %> <%= user.getFirstName() %> Pattern</h2>
<div align="center"><a href="/roster/roster">back to Roster</a></div>
<div align="center"><a href="/roster/userpatterns?user=<%= user.getId()%>&id=new">Add new user pattern</a></div>
    <table border = 1 cellpadding = 5 align="center">
        <th>Id</th>
        <th>Start Date</th>
        <th>End Date</th>
        <th>Pattern</th>
        <th>Starts on Day</th>
        <%  for (UserPattern userPattern : userPatterns) { %>
        <tr>
            <td><a href="/roster/userpatterns?user=<%= user.getId()%>&id=<%= userPattern.getId()%>"><%= userPattern.getId()%></a></td>
            <td><%= userPattern.getStartDay() %></td>
            <td><%= userPattern.getEndDay()%></td>
            <td><%= data.getPatternById(userPattern.getPattern().getId()).getName()%></td>
            <td><%= userPattern.getPatternStartDay()%></td>
        </tr>
        <% } %>
    </table>
</body>
</html>