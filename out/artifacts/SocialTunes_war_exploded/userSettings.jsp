<%--
  Created by IntelliJ IDEA.
  User: skylight
  Date: 14/05/14
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title> User Settings </title>
    </head>
    <body>
    <form action="UserSettingsServlet" method="POST">
        New Email:
        <label> <input name="email"/> </label>
        ${ emailErrorMessage }
        <br/>
        New Password:
        <label> <input type="password" name="password"/> </label>
        ${ passwordErrorMessage }
        <br/>
        Confirm New Password:
        <label> <input type="password" name="confirmPassword"/> </label>
        ${ passwordEqualErrorMessage }
        <br/>
        <input type="Submit" value="Update"/>
    </form>
    </body>
</html>
