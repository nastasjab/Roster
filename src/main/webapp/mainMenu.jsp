<%@ page import="lv.javaguru.java2.servlet.mvc.data.MenuItem" %>
<%@ page import="lv.javaguru.java2.servlet.mvc.data.Menu" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Menu menu = (Menu) request.getAttribute("model"); %>
<html>
<head>
    <title>Main Menu</title>
</head>
<body><table align="center" valign="center">
    <th>Main menu</th>
    <% for (MenuItem menuItem : menu.getMenu()) { %>
    <tr><td align="center"><a href="<%= menuItem.getUrl() %>"><%= menuItem.getCaption() %></a></td></tr>
    <% } %>
</table>
</body>
</html>
