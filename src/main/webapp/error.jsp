<%@ page import="lv.javaguru.java2.servlet.mvc.data.MessageContents" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% MessageContents messageContents = (MessageContents) request.getAttribute("model"); %>
<html>
<head>
  <script type="text/javascript">
    function alertName(){
      alert("<%= messageContents.getMessage()%>");
      window.location = '<%= messageContents.getUrl()%>';
    }
  </script>
  <script type="text/javascript"> window.onload = alertName; </script>
</head>
<body>

</body>
</html>
