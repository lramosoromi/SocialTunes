<%--
  Created by IntelliJ IDEA.
  User: skylight
  Date: 08/04/14
  Time: 00:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title> Create new User </title>
    </head>
    <body>
        <form action="CreateNewUserServlet" method="POST">
            Name:
            <label> <input name="name"/> </label>
            <br/>
            Last Name:
            <label> <input name="last_name"/> </label>
            <br/>
            Email:
            <label> <input name="email"/> </label>
            ${ emailErrorMessage }
            <br/>
            Username:
            <label> <input name="username"/> </label>
            ${ usernameErrorMessage }
            <br/>
            Password:
            <label> <input type="password" name="password"/> </label>
            ${ passwordErrorMessage }
            <br/>
            Confirm Password:
            <label> <input type="password" name="confirmPassword"/> </label>
            ${ passwordEqualErrorMessage }
            <br/>
            ${ emptyInputErrorMessage }
            <br/>
            <input type="Submit" value="Create"/>
        </form>
    </body>
</html>
