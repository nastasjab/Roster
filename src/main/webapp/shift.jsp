a<%@ page import="lv.javaguru.java2.domain.Shift" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%  Shift shift;
  boolean editShift = true;
  shift = (Shift) request.getAttribute("model");
  if (shift.getId() == 0) {
    editShift = false;
  }
%>
<html>
<head>
  <title>
    <%  if (editShift) { %>Edit shift<% } else {
  %>Add new shift<% } %>
  </title>
</head>
<body>
<h2 align="center">
  <% if (editShift) { %>
  Edit shift
  <br><%= shift.getShiftStarts() %>
  <br><%= shift.getShiftEnds() %>
  <% } else { %>
  Add new shift
  <% } %>
</h2>
<div align="center"><a href="/roster/shifts">back to Shift List</a></div>
<form method="post">
  <table align="center" cellpadding="5">
    <tr>
      <td>Name</td>
      <td><input type="text" name="name" value="<%= shift.getName() %>"></td>
    </tr>
    <tr>
      <td>Shift Starts</td>
      <td><input type="text" name="shiftstarts" value="<%= shift.getShiftStarts()%>"></td>
    </tr>
    <tr>
      <td>Shift Ends</td>
      <td><input type="text" name="shiftends" value="<%= shift.getShiftEnds()%>"></td>
    </tr>
    <tr>
      <td colspan="2" align="right">
        <%  if (editShift) { %>
        <input type="submit" name="shift_delete" value="Delete Shift">
        <input type="submit" name="shift_update" value="Update">
        <input type="submit" name="shift_discard" value="Discard Changes">
        <% } else { %>
        <input type="submit" name="shift_add" value="Add New Shift">
        <% } %>
      </td>
    </tr>
  </table>
</form>
</body>
</html>
