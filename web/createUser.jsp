<%--
  Created by IntelliJ IDEA.
  User: skylight
  Date: 08/04/14
  Time: 00:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Create User</h4>
            </div>
            <div class="modal-body">
                <form action="CreateNewUserServlet" method="POST">
                    Name:
                    <label> <input name="name"/> </label>
                    <br/>
                    Last Name:
                    <label> <input name="last_name"/> </label>
                    <br/>
                    Email:
                    <label> <input name="email"/> </label>
                    <label id="emailError" style="color: red"> ${ emailErrorMessage } </label>
                    <br/>
                    Username:
                    <label> <input name="username"/> </label>
                    <label id="usernameSignInError" style="color: red"> ${ usernameSignInErrorMessage } </label>
                    <br/>
                    Password:
                    <label> <input type="password" name="password"/> </label>
                    <label id="passwordSignInError" style="color: red"> ${ passwordSignInErrorMessage } </label>
                    <br/>
                    Confirm Password:
                    <label> <input type="password" name="confirmPassword"/> </label>
                    <label id="passwordEqualError" style="color: red"> ${ passwordEqualErrorMessage } </label>
                    <br/>
                    <label id="emptyInputError" style="color: red"> ${ emptyInputErrorMessage } </label>
                    <br/>
                    <input type="Submit" value="Create"/>
                </form>
            </div>
        </div>
    </div>
</div>
