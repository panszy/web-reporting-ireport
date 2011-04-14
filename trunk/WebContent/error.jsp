<%@page import="web.HttpConstants" %>
<% String title = (String) request.getAttribute(HttpConstants.ATTR_NAME_ERROR_TITLE); %>
<%@ include file="/includes/header.jsp" %>
<table>
	<tr>
		<td>
			<img src="<%=request.getContextPath()%>/images/icons/process-stop.png">
			<b><%=title %></b>
		</td>
	</tr>
	<tr>
		<td>
			<%=(String) request.getAttribute(HttpConstants.ATTR_NAME_ERROR_TEXT) %>
		</td>
	</tr>
</table>
<%@ include file="/includes/footer.jsp" %>
