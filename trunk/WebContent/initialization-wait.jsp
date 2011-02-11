<%@page import="web.HttpConstants" %>
<% String title = "Web Reporting Report"; %>
<%@ include file="/includes/header.jsp" %>
<table>
	<tr>
		<td>
			<img src="<%=request.getContextPath()%>/images/icons/appointment-new.png">
			<b>IN Gateway OA&amp;M is currently being initialized</b>
		</td>
	</tr>
	<tr>
		<td>
			Sorry, the IN Gateway OA&amp;M is being initialized, please wait for a few moments and then refresh the page.
		</td>
	</tr>
</table>
<%@ include file="/includes/footer.jsp" %>
