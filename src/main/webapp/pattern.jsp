<%@ page import="java.util.List" %>
<%@ page import="lv.javaguru.java2.domain.PatternShift" %>
<%@ page import="lv.javaguru.java2.servlet.mvc.data.PatternEditControllerData" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%  PatternEditControllerData pattern;
  boolean editPattern = true;
  pattern = (PatternEditControllerData) request.getAttribute("model");
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
    <tr>
      <td colspan="2" align="right">
        <%  if (editPattern) { %>
        <input type="submit" name="act_delete" value="Delete Pattern">
        <input type="submit" name="act_update" value="Update">
        <input type="submit" name="act_discard" value="Discard Changes">
        <% } else { %>
        <input type="submit" name="act_add" value="Add New Pattern">
        <% } %>
      </td>
    </tr>
  </table>
</form>

<%  if (editPattern) { %>
<form method="get" name="patternshift">
  <table border = 1 cellpadding = 5 align="center">
    <th>ID</th>
    <th>Seq No</th>
    <th>Name</th>
    <%  List<PatternShift> patternShifts = pattern.getPatternShifts();
      for (PatternShift patternShift : patternShifts) { %>
    <tr>
      <td><a href="/roster/patternshift?pattern_id=<%= patternShift.getId()%>&amp;id=<%= patternShift.getId()%>">
        <%= patternShift.getId()%></a></td>
      <td><%= patternShift.getSeqNo() %>  </td>
      <td>  <%= patternShift.getShift().getName() %>
        (<%= patternShift.getShift().getShiftStarts() %>-<%= patternShift.getShift().getShiftEnds() %>) </td>
    </tr>
    <% } %>
  </table>
  <div align="center"><a href="/roster/patternshift?pattern_id=<%= pattern.getId()%>">Add shift to pattern</a></div>
</form>
<% } %>

</body>
</html>
