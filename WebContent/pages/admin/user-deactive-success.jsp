<%@page import="com.torepo.web.util.DateFormatter" %>
<%@page import="com.torepo.web.web.UserSession" %>
<%@page import="com.torepo.web.web.User" %>
<%@page import="com.torepo.web.web.HttpConstants" %>
<%
String title = "User Search";
String message = (String)request.getParameter("message");
%>
<%@ include file="/includes/header.jsp" %>
<table>
    <tr>
		<td>
			<img src="<%=request.getContextPath()%>/images/icons/system-lock-screen.png"><b>Deactive User</b>			
		</td>
	</tr>
	<tr>
		<td>
    User has been deactive sucessfully
         </td>
    </tr>
</table>
<%@ include file="/includes/footer.jsp" %>