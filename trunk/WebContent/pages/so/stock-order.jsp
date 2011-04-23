<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%
	String title = "Stock Order";
%>
<%@ include file="/includes/header.jsp"%>
<link rel="stylesheet" href="/ireport/script/example.css"
	TYPE="text/css" MEDIA="screen">
<link rel="stylesheet" href="/ireport/script/example-print.css"
	TYPE="text/css" MEDIA="print">
<script type="text/javascript" src="/ireport/script/tabber.js"></script>
<style type="text/css">
.tabberlive .tabbertab {
	height: 400px;
	width: 800px;
}
</style>
<form id="myForm" name="myForm" action="<%=request.getContextPath()%>/pages/stock-order"
	method="post">
	<%	
	ArrayList<String> comboTypeSO = (ArrayList<String>) request
			.getAttribute("comboTypeSO");
	ArrayList<String> comboJenisTransaksi = (ArrayList<String>) request
	.getAttribute("comboJenisTransaksi");
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	String currentDate = sdf.format(new Date());

%> <img
	src="<%=request.getContextPath()%>/images/icons/system-users.png">
<b>Stock Order Information</b> <br>
<br>
<div class="tabber">

<div class="tabbertab">
<h2>Type Sales Order</h2>
<p>
<table>
	<tr>
		<td>Type Sales Order</td>
		<td><select name=type_so>
		<% for(String combo : comboTypeSO){ %>
    <option value="<%=combo.split(",")[1] %>"><%=combo.split(",")[0] %></option>
    <% } %>        
</select></td>				
	</tr>
	<tr>
		<td>Purchase Order</td>
		<td>PO.<input name="po" type="text" value="">
		</td>
	</tr>
	<tr>
		<td>Tanggal PO</td>
		<td><input name="tanggal_po" readonly type="text" value="<%=currentDate%>">
		</td>
	</tr>
	<tr>
		<td>Jenis Transaksi</td>
		<td><select name=jenis_transaksi>
		<% for(String combo : comboJenisTransaksi){ %>
    <option value="<%=combo.split(",")[1] %>"><%=combo.split(",")[0] %></option>
    <% } %>        
</select></td>		
	</tr>	
</table>
</p>

</div>


<div class="tabbertab">
<h2>Detail Barang</h2>
<p>
<table>
	<tr>
		<td>Item 4</td>
		<td><input name="item-4" type="text" value="">
		</td>
	</tr>
	<tr>
		<td>Item 5</td>
		<td><input name="item-5" type="text" value="">
		</td>
	</tr>
	<tr>
		<td>Item 6&nbsp;&nbsp;</td>
		<td><input name="item-6" type="text" value="">
		</td>
	</tr>	
</table>
</p>
</div>


<div class="tabbertab">

<h2>Register SO</h2>
<p>
<table>
	<tr>
		<td>Item 7</td>
		<td><input name="item-7" type="text" value="">
		</td>
	</tr>
	<tr>
		<td>Item 8</td>
		<td><input name="item-8" type="text" value="">
		</td>
	</tr>
	<tr>
		<td>Item 9&nbsp;&nbsp;</td>
		<td><input name="item-9" type="text" value="">
		</td>
	</tr>	
</table>
</p>
</div>

</div>
<input type="submit" value="Order">
</form>
<%@ include file="/includes/footer.jsp"%>