<%@ page import="lv.javaguru.java2.domain.shift.Shift" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Shifts</title>
</head>
<body>
<h2 align="center">Shifts</h2>
<div align="center"><a href="/roster">Main Menu</a></div>
<div align="center"><a href="/roster/shift">Add new shift</a></div>
<form method="get" name="shifts">
  <table border = 1 cellpadding = 5 align="center">
    <th>No</th>
    <th>Name</th>
    <th>Start</th>
    <th>End</th>
    <%  List<Shift> shifts = (List<Shift>) request.getAttribute("model");
      for (Shift shift : shifts) { %>
    <tr>
      <td><a href="/roster/shift?id=<%= shift.getId()%>"><%= shift.getId()%></a></td>
      <td><%= shift.getName() %></td>
      <td><%= shift.getShiftStarts() %></td>
      <td><%= shift.getShiftEnds() %></td>
    </tr>
    <% } %>
  </table>
</form>

</body>
</html>
