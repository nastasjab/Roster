<%@ page import="lv.javaguru.java2.domain.user.User" %>
<%@ page import="lv.javaguru.java2.domain.user.UserTypes" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%  User user;
    boolean editUser = true;
    user = (User) request.getAttribute("model");
    if (user.getId() == 0) {
        editUser = false;
    }
%>
<html>
<head>
    <title>
        <%  if (editUser) { %>Edit user<% } else {
    %>Add new user<% } %>
    </title>
</head>
<body>
<h2 align="center">
<% if (editUser) { %>
Edit user
    <br><%= user.getFirstName() %>
    <br><%= user.getLastName() %>
<% } else { %>
Add new user
<% } %>
</h2>
<div align="center"><a href="/roster/users">back to User List</a></div>
<form method="post">
<table align="center" cellpadding="5">
    <tr>
        <td>Login</td>
        <td><input type="text" name="login" value="<%= user.getLogin() %>"></td>
    </tr>
    <tr>
        <td>Password</td>
        <td><input type="text" name="password" value="<%= user.getPassword()%>"></td>
    </tr>
    <tr>
        <td>First Name</td>
        <td><input type="text" name="firstname" value="<%= user.getFirstName()%>"></td>
    </tr>
    <tr>
        <td>Last Name</td>
        <td><input type="text" name="lastname" value="<%= user.getLastName()%>"></td>
    </tr>
    <tr>
        <td>User Type</td>
        <td><select name="usertype">
            <%  UserTypes userTypes = new UserTypes();
                for (String key : userTypes.getKeys()) { %>
                <option value="<%= key%>"
                <%  if (key.equals(user.getUserType())) { %>
                     selected
                <% } %>
                ><%= userTypes.getName(key)%>
                </option>
            <%  } %>
        </select></td>
    </tr>
    <tr>
        <td>Email</td>
        <td><input type="text" name="email" value="<%= user.getEmail()%>"></td>
    </tr>
    <tr>
        <td>Phone</td>
        <td><input type="text" name="phone" value="<%= user.getPhone()%>"></td>
    </tr>
    <tr>
        <td colspan="2" align="right">
            <%  if (editUser) { %>
            <input type="submit" name="act_delete" value="Delete User">
            <input type="submit" name="act_update" value="Update">
            <input type="submit" name="act_discard" value="Discard Changes">
            <% } else { %>
            <input type="submit" name="act_add" value="Add New User">
            <% } %>
        </td>
    </tr>
</table>
</form>
</body>
</html>
