<% String title = "Login"; %>
<%@ include file="/includes/header.jsp" %>
	<form action="" method="post">
		<table>
			<tr>
				<td>
					<img src="<%=request.getContextPath()%>/images/icons/system-lock-screen.png">
					<b>Please authenticate to proceed</b>
				</td>
			</tr>
			<%
				if((request.getAttribute("login-status") != null) && 
				(((String) request.getAttribute("login-status")).compareTo("fail") == 0)) {
			 %>
			 	<tr>
			 		<td><font color="red">The username and/or password you provided was incorrect.</font></td>
			 	</tr>
			<%
				}
			 %>
		</table>
		<table>
			<tr>
				<td>Username</td>
				<td>
					<input type="hidden" name="action" value="login">
					<input name="username" type="text" 
					value='<%=request.getAttribute("username") == null ? "" : request.getAttribute("username")%>'>
				</td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input name="password" type="password" value=""></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Login &raquo;"></td>
			</tr>
		</table>
	</form>
<%@ include file="/includes/footer.jsp" %>
					