<%@page import="common.DateFormatter" %>
<%@page import="web.UserSession" %>
<%@page import="web.User" %>
<% String title = "User Panel"; %>
<% User user = UserSession.Factory.getUserSession(request).getUser(); %>
<%@ include file="/includes/header.jsp" %>
<table>
	<tr>
		<td>
			<img src="<%=request.getContextPath()%>/images/icons/system-lock-screen.png">
			<b>User Panel</b>
		</td>
	</tr>
	<tr>
		<td>
			This page provides information and options on your user account.
		</td>
	</tr>
	<tr>
		<td>
			<table class="item" style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;" width=450>
				<tr>
					<td class="item-header" colspan=2>
						User Information
					</td>
				</tr>
				<tr>
					<td class="item" width=120>
						Username
					</td>
					<td class="item">
						<b><%=user.getUsername() %></b>
					</td>
				</tr>
				<tr>
					<td class="item" width=120>
						Full name
					</td>
					<td class="item">
						<b><%=user.getFullName() %></b>
					</td>
				</tr>
				<tr>
					<td class="item">
						NIK
					</td>
					<td class="item">
						<%=user.getNik() %>
					</td>
				</tr>
				<tr>
					<td class="item">
						E-mail address
					</td>
					<td class="item">
                        <%=user.getEmailAddress() %>
                    </td>
				</tr>
				<tr>
					<td class="item">
						Department
					</td>
					<td class="item">
                        <%=user.getDepartemen() %>
                    </td>
				</tr>
				<tr>
					<td class="item">
						Division
					</td>
					<td class="item">
                        <%=user.getDivision() %>
                    </td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height=10>
		</td>
	</tr>
	<tr>
		<td>
			<table class="item" style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;" width=450>
				<tr>
					<td class="item-header">
						Security
					</td>
				</tr>
				<tr>
					<td class="item-bare">
						Your password will be expired on 
						<%=DateFormatter.getDisplayDate(user.getPasswordExpiry())%>.
						<br>
						To change your current password, click on <b>Change Password</b> button.
					</td>
				</tr>
				<tr>
					<td class="item-bare">
						<button onclick="location.href='<%=request.getContextPath()%>/pages/change-password'">Change Password &raquo;</button>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<%@ include file="/includes/footer.jsp" %>
