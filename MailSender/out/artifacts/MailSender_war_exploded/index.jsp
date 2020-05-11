<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 26.04.2020
  Time: 1:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Send e-mail</title>
  </head>
  <body>
  <form action="sendEmail" method="post">
    <p>To:</p>
    <input type="text" name="toAddress" value=""/><br>
    <p>Topic:</p>
    <input type="text" name="subject" value=""><br>
    <p>Text:</p>
    <input type="text" name="message" value="" /><br>
    <input type="submit" name="send" value="Send"/><br>
  </form>
  </body>
</html>
