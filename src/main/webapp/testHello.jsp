
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello Test</title>
</head>
<body>
It works if you see 'hello' below:
<%= request.getAttribute("model").toString() %>

</body>
</html>
