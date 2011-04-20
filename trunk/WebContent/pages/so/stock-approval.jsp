
<%
	String title = "Stock Approval";
%>
<%@ include file="/includes/header.jsp"%>

<form name="myForm" action="<%=request.getContextPath()%>/pages/approve"
	method="post">
<%
	ArrayList<ArrayList<String>> tableData = (ArrayList<ArrayList<String>>) request
			.getAttribute("tableData");
	ArrayList<String> tableColumn = (ArrayList<String>) request
			.getAttribute("tableColumn");
%> <img
	src="<%=request.getContextPath()%>/images/icons/system-users.png">
<b>Stock Order Information</b> <br>
<br>
<table class="item"
	style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;"
	width=400>
	<tr>
		<%
			for (String column : tableColumn) {
		%>
		<td class="item-header"><%=column%></td>
		<%
			}
		%>
	</tr>
	<%
		for (ArrayList<String> rowData : tableData) {
	%>
	<tr>
		<%
			for (String columnData : rowData) {
		%>
		<td class="item"><%=columnData%></td>
		<%
			}
		%>
	</tr>
	<%
		}
	%>
</table>
<input type="submit" value="Approved"
	onclick="ChangeValue(document.myForm.type,'approve')"> <input
	type="submit" value="Cancel"
	onclick="ChangeValue(document.myForm.type,'cancel')"> <input
	type="submit" value="Reject"
	onclick="ChangeValue(document.myForm.type,'reject')"> <input
	type="hidden" name="type" value=""></form>

<%@ include file="/includes/footer.jsp"%>