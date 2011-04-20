
<%
	String title = "Stock Approval";
%>
<%@ include file="/includes/header.jsp"%>
<b><%=request.getAttribute("message")%></b>
<br><br>
<table class="item"
	style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;"
	width=400>
	<tr>
		<td class="item-header">Stock Order Id</td>
	</tr>

	<tr>

		<td class="item"><%=request.getAttribute("stock_order_id")%></td>
	</tr>
</table>

<%@ include file="/includes/footer.jsp"%>