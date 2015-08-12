<%@ page import="lv.javaguru.java2.domain.Pattern" %>
<%@ page import="java.util.List" %>
<%@ page import="lv.javaguru.java2.domain.ShiftPattern" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%  Pattern pattern;
  boolean editPattern = true;
  pattern = (Pattern) request.getAttribute("model");
  if (pattern.getId() == 0) {
    editPattern = false;
  }
%>
<html>
<head>
  <title>
    <%  if (editPattern) { %>
      Edit pattern
    <% } else { %>
      Add new pattern
    <% } %>
  </title>
</head>
<body>
<h2 align="center">
  <% if (editPattern) { %>
  Edit pattern #<%=pattern.getId() %>
  <% } else { %>
  Add new pattern
  <% } %>
</h2>
<div align="center"><a href="/roster/patterns">back to Pattern List</a></div>
<form method="post">
  <table align="center" cellpadding="5">
    <tr>
      <td>Name</td>
      <td><input type="text" name="name" value="<%= pattern.getName() %>"></td>
    </tr>
      <td colspan="2" align="right">
        <%  if (editPattern) { %>
        <input type="submit" name="pattern_delete" value="Delete Pattern">
        <input type="submit" name="pattern_update" value="Update">
        <input type="submit" name="pattern_discard" value="Discard Changes">
        <% } else { %>
        <input type="submit" name="pattern_add" value="Add New Pattern">
        <% } %>
      </td>
    </tr>
  </table>
</form>

<form method="get" name="shiftpatterns">
  <table border = 1 cellpadding = 5 align="center">
    <th>ID</th>
    <th>Seq No</th>
    <th>Name</th>
    <%  List<ShiftPattern> shiftPatterns = (List<ShiftPattern>) pattern.getShiftPatterns();
      for (ShiftPattern shiftPattern : shiftPatterns) { %>
    <tr>
      <td><a href="/roster/shiftpattern?pattern_id=<%= pattern.getId()%>&amp;id=<%= shiftPattern.getId()%>">
        <%= shiftPattern.getId()%></a></td>
      <td><%= shiftPattern.getSeqNo() %>  </td>
      <td>  <%= shiftPattern.getShiftName() %> </td>
    </tr>
    <% } %>
  </table>
  <div align="center"><a href="/roster/shiftpattern?pattern_id=<%= pattern.getId()%>">Add shift to pattern</a></div>
</form>

</body>
</html>
