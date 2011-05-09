
<%
SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd");
	String title = "Stock Order";
%>
<%@ include file="/includes/header.jsp"%>
<script type="text/javascript">    

var timer2;
timer2=setInterval("redirect()",1000);                      
function redirect(){    	
	window.location.href ='<%=request.getContextPath()%>/pages/stock-order-update?page=1&tanggal_so_awal=<%=sdfOutput.format(new Date())%>&tanggal_so_akhir=<%=sdfOutput.format(new Date())%>&nomor_so=';	
}
    
</script>
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