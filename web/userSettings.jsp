<%--
  Created by IntelliJ IDEA.
  User: skylight
  Date: 14/05/14
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Modal -->
<div class="modal fade" id="userSettingsModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Settings</h4>
            </div>
            <div class="modal-body">
                <form action="UserSettingsServlet" method="POST">
                    New Email:
                    <label> <input name="email"/> </label>
                    <label id="newEmailError" style="color: red"> ${ newEmailErrorMessage } </label>
                    <br/>
                    New Password:
                    <label> <input type="password" name="password"/> </label>
                    <label id="newPasswordError" style="color: red"> ${ newPasswordErrorMessage } </label>
                    <br/>
                    Confirm New Password:
                    <label> <input type="password" name="confirmPassword"/> </label>
                    <label id="newPasswordEqualError" style="color: red"> ${ newPasswordEqualErrorMessage } </label>
                    <br/>
                    <input type="Submit" value="Update"/>
                </form>
            </div>
        </div>
    </div>
</div>