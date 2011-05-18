<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@page import="web.UserSession"%>
<%@page import="web.User"%>
<%
	String title = "Create Stock Order";
%>
<%@ include file="/includes/header.jsp"%>
<link rel="stylesheet" href="/ireport/script/example.css"
	TYPE="text/css" MEDIA="screen">
<link rel="stylesheet" href="/ireport/script/example-print.css"
	TYPE="text/css" MEDIA="print">
<script type="text/javascript" src="/ireport/script/tabber.js"></script>
<style type="text/css">
.tabberlive .tabbertab {
	height: 300px;
	width: 800px;
}
</style>

<script type="text/javascript">    
        
function batal(){    	
	document.info.reset();		
}

function batal_detail(){    	
	document.info.reset();		
	document.info.buttonLookup.focus();
} 

function register_detail(){    		
	document.getElementById("tab1").tabber.tabShow(2);
}

function tambah_detail(fld1,fld2,fld3,fld4,fld5){
	addElement(fld1,fld2,fld3,fld4,fld5);
}

function hapus_detail(){
	removeElement();
}
    
</script>
<form id="info" name="info" action="<%=request.getContextPath()%>/pages/stock-order"
	method="post">
	<%	
	ArrayList<String> comboTypeSO = (ArrayList<String>) request
			.getAttribute("comboTypeSO");
	ArrayList<String> comboJenisTransaksi = (ArrayList<String>) request
	.getAttribute("comboJenisTransaksi");
	ArrayList<String> comboTipeTransaksi = (ArrayList<String>) request
	.getAttribute("comboTipeTransaksi");	
	ArrayList<String> comboJenisObat = (ArrayList<String>) request
	.getAttribute("comboJenisObat");
	ArrayList<String> tableColumn = new ArrayList<String>();
	tableColumn.add("No");
	tableColumn.add("Kode");
	tableColumn.add("Nama");
	tableColumn.add("Satuan");
	tableColumn.add("Kemasan");
	tableColumn.add("Qty");
	ArrayList<ArrayList<String>> tableData = (ArrayList<ArrayList<String>>) request
	.getAttribute("tableData");
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	String currentDate = sdf.format(new Date());
	User user = UserSession.Factory.getUserSession(request).getUser();

%>
<p align="right">
 <table bgcolor="white">
 <tr>
 <td>[<%=user.getKodeCustomer()%>]&nbsp;<%=user.getNamaCustomer()%> </td>
 </tr>
 <tr>
 <td><%=user.getAlamatCustomer()%> </td>
 </tr>
 <tr>
 <td><%=user.getKotaCustomer()%> </td>
 </tr>
 <tr>
 <td>NPWP&nbsp;:&nbsp;<%=user.getNpwpCustomer()%> </td>
 </tr>
 </table> 
</p>

 <img
	src="<%=request.getContextPath()%>/images/icons/system-users.png">
<b>Stock Order Information</b> <br>
<br>
<div class="tabber" id="tab1">

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
	<tr>
		<td>Jenis Obat</td>
		<td><select name=jenis_obat>
		<% for(String combo : comboJenisObat){ %>
    <option value="<%=combo.split(",")[1] %>"><%=combo.split(",")[0] %></option>
    <% } %>        
</select></td>		
	</tr>
</table>
</p>

</div>


<div class="tabbertab">
<h2>Detail Barang</h2>
<table>
	<tr>
		<td>Kode Barang</td>
		<td colspan="2">Nama Barang</td>
		<td>Pabrik</td>
	</tr>
	<tr>		
		<td><input name="kode_barang" size="20" readonly type="text" value="">&nbsp;<input type="button" name="buttonLookup" value="Lookup" onclick="OpenPop_UpList('<%=request.getContextPath()%>/pages/list?title=Pencarian%20Stok%20Barang&tableTitle=Daftar%20Stok%20Barang&itemName=kode_barang,nama_barang,pabrik,satuan_barang,kemasan_barang&showFields=Kode;Barang,Nama;Barang,Pabrik&queryData=kodeBarangQuery');return false;"></td>		
		<td colspan="2"><input name="nama_barang" size="50" readonly type="text" value=""></td>
		<td><input name="pabrik" size="30" readonly type="text" value=""></td>
	</tr>
	<tr>
		<td>Satuan</td>
		<td>Kemasan</td>
		<td>Qty</td>
		<td>Proses</td>
	</tr>
	<tr>
		<td><input name="satuan_barang" size="30" readonly type="text" value=""></td>
		<td><input name="kemasan_barang" size="25" readonly type="text" value=""></td>		
		<td><input name="quantity_so" size="25" type="text" value="" onkeypress='return onlyNumbers(event)'></td>
		<td>
			<input type="button" value="Tambah" onClick="tambah_detail(document.info.kode_barang.value,document.info.nama_barang.value,document.info.satuan_barang.value,document.info.kemasan_barang.value,document.info.quantity_so.value)">
			<input type="button" value="Hapus" onClick="hapus_detail()">
			<input type="button" value="Batal" onClick="batal_detail()">
			<input type="button" value="Register" onClick="register_detail()">	
		</td>
	</tr>	
</table>
<br>
Detail Barang
<div id="my_div_barang">
<table id="tabela" class="item"
	style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;"
	width=600>	
	<input type="hidden" value="0" id="theValue"/>  
	<tbody>          	
	<tr>
		<%
			for (String column : tableColumn) {
		%>
		<td class="item-header"><%=column%></td>
		<%
			}
		%>				
	</tr>
	</tbody>			        
	</table>
</div>
</div>

<div class="tabbertab">

<h2>Register SO</h2>
<p>
<table>
	<tr>
		<td>Type Delivery</td>
		<td><select name="tipe_transaksi">
		<% for(String combo : comboTipeTransaksi){ %>
    <option value="<%=combo.split(",")[1] %>"><%=combo.split(",")[0] %></option>
    <% } %>        
</select></td>
		</td>
	</tr>
	<tr>
		<td>Tanggal Sales Order</td>
		<td><input name="tanggal_so" readonly type="text" value="<%=currentDate%>">
		</td>
	</tr>
	<tr>
		<td>Type Bayar</td>
		<td><select name="tipe_bayar">
		<option value="tunai">tunai</option>
	    <option value="kredit">kredit</option>
</select></td>
		</td>
	</tr>	
	<tr>
		<td>Catatan</td>
		<td><input name="catatan" type="text" value="">
		</td>
	</tr>	
</table>
</p>
<br><br><br><br><br><br><br><br><br><br><br>
<input type="submit" value="Simpan" name="Action">
	<input type="submit" value="Batal" name="Action" onClick="batal()">
</div>

</div>
</form>
<%@ include file="/includes/footer.jsp"%>