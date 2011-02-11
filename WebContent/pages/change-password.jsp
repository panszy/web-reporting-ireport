<%@page import="com.torepo.web.web.HttpConstants" %>
<% String title = "Change Password"; %>
<%@ include file="/includes/header.jsp" %>
<table>
	<tr>
		<td>
			<img src="<%=request.getContextPath()%>/images/icons/system-lock-screen.png">
			<b>Change Password</b>
		</td>
	</tr>
	
	<%
		if(request.getAttribute(HttpConstants.ATTR_VAR_CHANGE_PASSWORD_SUCCESS) == null) {
	%>
		<%
			if(request.getAttribute(HttpConstants.ATTR_VAR_PASSWORD_EXPIRE) != null) {
		%>
		<tr>
			<td>
				<font color="red">Your password has expired</font>
				<br>
				You must change your password prior to be able to use the system.
			</td>
		</tr>
		<%
			}
		%>
		<tr>
			<td>
				To change password, fill in your current password and desired new password twice.
				<br>
				Passwords must be at least 8 characters in length, and must contain alphanumeric characters.
			</td>
		</tr>
		<%
			String error = (String) request.getAttribute(HttpConstants.ATTR_VAR_CHANGE_PASSWORD_ERROR);
			if(error != null) {
		%>
		<tr>
			<td>
				<font color="red"><%=error %></font>
			</td>
		</tr>
		<%
			}
		%>
	</table>
	<form action="<%=request.getContextPath() %>/pages/change-password" method="post">
		<table>
			<tr>
				<td>
					Current password
				</td>
				<td>
					<input name="old-password" type="password" value="">
				</td>
			</tr>
			<tr>
				<td>
					New password
				</td>
				<td>
					<input name="new-password" type="password" value="">
				</td>
			</tr>
			<tr>
				<td>
					Confirm new password&nbsp;&nbsp;
				</td>
				<td>
					<input name="confirm-new-password" type="password" value="">
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td>
					<input type="submit" value="Change Password &raquo;">
				</td>
			</tr>
		</table>
	</form>
	<%
		}
		else {
	%>
		<tr>
			<td>
				Your password has been changed successfully.
			</td>
		</tr>
	<%
		}
	%>
<%@ include file="/includes/footer.jsp" %>
