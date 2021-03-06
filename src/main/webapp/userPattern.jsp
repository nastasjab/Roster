<%@ page import="lv.javaguru.java2.domain.user.User" %>
<%@ page import="lv.javaguru.java2.servlet.mvc.data.UserPatternEditControllerData" %>
<%@ page import="lv.javaguru.java2.domain.user.UserPattern" %>
<%@ page import="java.util.List" %>
<%@ page import="lv.javaguru.java2.domain.pattern.Pattern" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% UserPatternEditControllerData data = (UserPatternEditControllerData) request.getAttribute("model");
    User user = data.getUser();
    UserPattern userPattern = data.getUserPattern();
    List<Pattern> shiftPatterns = data.getPatterns();
  boolean edit = true;
  if (userPattern.getId() == 0) {
    edit = false;
  }
%>
<html>
<head>
  <title>
    <%  if (edit) { %>Edit user pattern<% } else {
  %>Add new user pattern<% } %>
  </title>
</head>
<body>
<h2 align="center">
  <% if (edit) { %>
  Edit user pattern
  <br><%= user.getFirstName() %>
  <br><%= user.getLastName() %>
  <% } else { %>
  Add new user pattern
  <% } %>
</h2>
<div align="center"><a href="/roster/userpatterns?user=<%= user.getId()%>">back to User Patterns List</a></div>
<form method="post">
  <table align="center" cellpadding="5">
    <tr>
      <td>Start Date</td>
      <td><input type="date" name="startday" value="<%= userPattern.getStartDay() %>"></td>
    </tr>
    <tr>
      <td>End Date</td>
      <td><input type="date" name="endday" value="<%= userPattern.getEndDay()%>"></td>
    </tr>
    <tr>
      <td>Pattern</td>
      <td><select name="pattern">
        <%
          for (Pattern pattern : shiftPatterns) { %>
        <option value="<%= pattern.getId()%>"
                <%  if (pattern.getId() == userPattern.getPattern().getId()) { %>
                selected
                <% } %>
                ><%= pattern.getName()%>
        </option>
        <%  } %>
      </select></td>
    </tr>
    <tr>
      <td>Starts on Day</td>
      <td><input type="text" name="patternstartday" value="<%= userPattern.getPatternStartDay()%>"></td>
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
