<% String title = "Logout"; %>
<%@ include file="/includes/header.jsp" %>
		<table>
			<tr>
				<td>
					<img src="<%=request.getContextPath()%>/images/icons/system-lock-screen.png">
					<b>You have been logged out successfully</b>
				</td>
			</tr>
			<tr>
				<td>
					To log back in the system, click <a href="<%=request.getContextPath()%>/pages/login?login=true">here</a>.
				</td>
			</tr>
		</table>
<%@ include file="/includes/footer.jsp" %>
