<%@ page import="lv.javaguru.java2.domain.Pattern" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Patterns</title>
</head>
<body>
<h2 align="center">Patterns</h2>
<div align="center"><a href="/roster">Main Menu</a></div>
<div align="center"><a href="/roster/pattern">Add new pattern</a></div>
<form method="get" name="patterns">
  <table border = 1 cellpadding = 5 align="center">
    <th>No</th>
    <th>Name</th>
    <%  List<Pattern> patterns = (List<Pattern>) request.getAttribute("model");
      for (Pattern pattern : patterns) { %>
    <tr>
      <td><a href="/roster/pattern?pattern_id=<%= pattern.getId()%>"><%= pattern.getId()%></a></td>
      <td><%= pattern.getName() %></td>
    </tr>
    <% } %>
  </table>
</form>

</body>
</html>
