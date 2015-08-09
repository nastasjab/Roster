<%@ page import="lv.javaguru.java2.servlet.mvc.MessageContents" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% MessageContents messageContents = (MessageContents) request.getAttribute("model"); %>
<html>
<head>
    <title><%= messageContents.getCaption()%></title>
</head>
<body>
<h2 align="center"><%= messageContents.getMessage()%></h2>
<div align="center"><a href="<%= messageContents.getUrl()%>"><%= messageContents.getUrlCaption()%></a></div>
</body>
</html>
