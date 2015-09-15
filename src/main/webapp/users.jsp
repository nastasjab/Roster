<%@ page import="lv.javaguru.java2.domain.user.User" %>
<%@ page import="lv.javaguru.java2.domain.user.UserTypes" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h2 align="center">Users</h2>
<div align="center"><a href="/roster">Main Menu</a></div>
<div align="center"><a href="/roster/users?id=new">Add new user</a></div>
<form method="get" name="users">
    <table border = 1 cellpadding = 5 align="center">
        <th>No</th>
        <th>Login</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>type</th>
        <th>email</th>
        <th>Phone</th>
        <%  List<User> users = (List<User>) request.getAttribute("model");
            UserTypes userTypes = new UserTypes();
            for (User user : users) { %>
        <tr>
            <td><a href="/roster/users?id=<%= user.getId()%>"><%= user.getId()%></a></td>
            <td><%= user.getLogin() %></td>
            <td><%= user.getFirstName() %></td>
            <td><%= user.getLastName() %></td>
            <td><%= userTypes.getName(user.getUserType()) %></td>
            <td><%= user.getEmail() %></td>
            <td><%= user.getPhone() %></td>
        </tr>
        <% } %>
    </table>
</form>

</body>
</html>
