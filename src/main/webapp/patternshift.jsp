<%@ page import="lv.javaguru.java2.servlet.mvc.data.PatternShiftEditControllerData" %>
<%@ page import="lv.javaguru.java2.domain.shift.Shift" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% PatternShiftEditControllerData data = (PatternShiftEditControllerData) request.getAttribute("model");
  boolean edit = true;
  if (data.getId() == 0) {
    edit = false;
  }
%>
<html>
<head>
  <title>
    <%  if (edit) { %>Edit shift in pattern<% } else {
  %>Add new shift to pattern<% } %>
  </title>
</head>
<body>
<h2 align="center">
  <% if (edit) { %>
  Edit shift in pattern #
  <% } else { %>
  Add new shift to pattern #
  <% } %>
  <%=data.getPattern().getId() %>
</h2>
<div align="center"><a href="/roster/patterns?id=<%= data.getPattern().getId()%>">back to pattern definition</a></div>
<form method="post">
  <table align="center" cellpadding="5">
    <tr>
      <td>Seq No</td>
      <td><input type="text" name="seqno" value="<%= data.getSeqNo() %>"></td>
    </tr>
    <tr>
      <td>Shift</td>
      <td><select name="shift">
        <%
          for (Shift shift : data.getAllShifts()) { %>
        <option value="<%= shift.getId()%>"
                <%  if (shift.getId() == data.getShift().getId()) { %>
                selected
                <% } %>
                ><%= shift.getName()%>
        </option>
        <%  } %>
      </select></td>



    </tr>
    <tr>
      <td colspan="2" align="right">
        <%  if (edit) { %>
        <input type="submit" name="act_delete" value="Delete">
        <input type="submit" name="act_update" value="Update">
        <input type="submit" name="act_discard" value="Discard Changes">
        <% } else { %>
        <input type="submit" name="act_add" value="Add New">
        <% } %>
      </td>
    </tr>
  </table>
</form>
</body>
</html>
