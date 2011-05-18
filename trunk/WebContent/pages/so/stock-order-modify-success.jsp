
<%	
	String title = "Modify Stock Order";
String[] qty = (String[])request.getAttribute("quantity");
String[] kodebarang = (String[])request.getAttribute("kode_barang");
%>
<%@ include file="/includes/header.jsp"%>

<script type="text/javascript">    

var timer2;
timer2=setInterval("redirect()",1000);                      
function redirect(){    	
	window.location.href ='<%=request.getContextPath()%>/pages/stock-order-update?page=1&tanggal_so_awal=<%=request.getAttribute("tanggal_so")%>&tanggal_so_akhir=<%=request.getAttribute("tanggal_so")%>&nomor_so=';	
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
		<td rowspan="<%=qty.length%>" class="item"><%=request.getAttribute("no_so")%></td>
		<td rowspan="<%=qty.length%>" class="item"><%=request.getAttribute("tanggal_so")%></td>
		<td rowspan="<%=qty.length%>" class="item"><%=request.getAttribute("no_po")%></td>
		<td rowspan="<%=qty.length%>" class="item"><%=request.getAttribute("tanggal_po")%></td>
		<%for(int cnt = 0; cnt < qty.length ; cnt++){ %>
		<td class="item"><%=kodebarang[cnt]%></td>
		<td class="item"><%=qty[cnt]%></td>
		<%} %>
		<td rowspan="<%=qty.length%>" class="item"><%=request.getAttribute("tipe_bayar")%></td>
		<td rowspan="<%=qty.length%>" class="item"><%=request.getAttribute("catatan")%></td>
	</tr>
</table>

<%@ include file="/includes/footer.jsp"%>