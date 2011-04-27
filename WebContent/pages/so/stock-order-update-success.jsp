
<%
	String title = "Stock Order";
%>
<%@ include file="/includes/header.jsp"%>
<b><%=request.getAttribute("message")%></b>
<br><br>
<table class="item"
	style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;"
	width=400>
	<tr>
		<td class="item-header">No. SO</td>
		<td class="item-header">Tanggal SO</td>
		<td class="item-header">No. PO</td>		
		<td class="item-header">Tanggal PO</td>		
		<td class="item-header">Kode Barang</td>		
		<td class="item-header">Quantity SO</td>				
		<td class="item-header">Tipe bayar</td>
		<td class="item-header">Catatan</td>		
	</tr>

	<tr>
		<td class="item"><%=request.getAttribute("no_so")%></td>
		<td class="item"><%=request.getAttribute("tanggal_so")%></td>
		<td class="item"><%=request.getAttribute("no_po")%></td>
		<td class="item"><%=request.getAttribute("tanggal_po")%></td>
		<td class="item"><%=request.getAttribute("kode_barang")%></td>
		<td class="item"><%=request.getAttribute("quantity")%></td>
		<td class="item"><%=request.getAttribute("tipe_bayar")%></td>
		<td class="item"><%=request.getAttribute("catatan")%></td>
	</tr>
</table>

<%@ include file="/includes/footer.jsp"%>