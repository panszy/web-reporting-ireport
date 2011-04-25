<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%
String title = "User Search";
int pages=request.getAttribute("pages")==null?1:Integer.parseInt((String)request.getAttribute("pages"));
int total_pages=request.getAttribute("total_pages")==null?1:Integer.parseInt((String)request.getAttribute("total_pages"));
String tanggaSOAwal =request.getParameter("tanggal_so_awal")==null?"":request.getParameter("tanggal_so_awal");
String tanggaSOAkhir =request.getParameter("tanggal_so_akhir")==null?"":request.getParameter("tanggal_so_akhir");
String nomorSO =request.getParameter("nomor_so")==null?"":request.getParameter("nomor_so");
ArrayList<ArrayList<String>> tableData = (ArrayList<ArrayList<String>>) request
.getAttribute("tableData");
ArrayList<String> tableColumn = (ArrayList<String>) request
.getAttribute("tableColumn");
SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
String currentDate = sdf.format(new Date());
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
<form id="myForm" name="myForm" action="" method="post">
<img src="<%=request.getContextPath()%>/images/icons/system-users.png">
<b>Search Stock Order</b> <br><br>

<table>
<tr>
<td>Tanggal SO</td>
<td><input type="text" size="30" name="tanggal_so_awal" value="<%=tanggaSOAwal %>" ></td>
<td>-<input type="text" size="30" name="tanggal_so_akhir" value="<%=tanggaSOAkhir %>" ></td>
</tr>
<tr>
<td>Nomor SO</td>
<td colspan="2"><input type="text" size="30" readonly name="nomor_so" value="<%=nomorSO %>" >&nbsp;<a onclick="OpenPop_UpList('<%=request.getContextPath()%>/pages/list?title=Cari%20NO%20SO%20SMS&tableTitle=Daftar%20NO%20SO%20SMS&itemName=nomor_so&showFields=no_so_sms,tgl_so_sms,no_po,tgl_po&queryData=kodeSOQuery');return false;" href="">Look up</a></td>
</tr>
</table>
<input type="submit" value="Search" name="Action"></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<%if(tableColumn!=null && tableColumn.size() > 0){ %>
<table class="item"
	style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;"
	width=400>
	<script type="text/javascript">
    function checkAll(field,total)
    {
        if(document.frmSearch.checkall.checked == true){
            if(total>0){
                field.checked=true;
            }
            for (i = 0; i < field.length; i++)
                field[i].checked = true ;
        } else {
            if(total>0){
                field.checked=false;
            }
            for (i = 0; i < field.length; i++)
                field[i].checked = false ;
        }
    }
    
    function uncheckcheckAll(field,total)
    {
        var condition = false;
        if(document.frmSearch.checkall.checked == true){
            for (i = 0; i < field.length; i++){
                if(field[i].checked == false){
                    condition = true;
                    break;
                }
            }
            if(condition || (total>0 && field.checked==false)){
                document.frmSearch.checkall.checked = false;
            }
        }       
    }
        
    function deletes(total) {
        i = 0;
        var condition = false;      
        while(i < document.frmSearch.deleted.length){
            if(document.frmSearch.deleted[i].checked==true){
                condition = true;
                break;
            }
            i++;
        }
        if(condition || (total>0 && document.frmSearch.deleted.checked==true)){
            if (confirm("Are you sure you want to delete")) {
                document.frmSearch.submit();
            }                       
        } else {
            alert ('You didn\'t choose any of the checkboxes!');
        }
    }
    
        function deactives(total) {
        i = 0;
        var condition = false;      
        while(i < document.frmSearch.deleted.length){
            if(document.frmSearch.deleted[i].checked==true){
                condition = true;
                break;
            }
            i++;
        }
        if(condition || (total>0 && document.frmSearch.deleted.checked==true)){
            if (confirm("Are you sure you want to deactive")) {
                document.frmSearch.submit();
            }                       
        } else {
            alert ('You didn\'t choose any of the checkboxes!');
        }
    }
    
    
    </script>
	<tr>
		<%
			for (String column : tableColumn) {
		%>
		<td class="item-header"><%=column%></td>
		<%
			}
		%>
		<td class="item-header">Edit</td>
		<td class="item-header">Check</td>
	</tr>
	<%	
	int i=0;
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
		<td class="item"><a href="<%=request.getContextPath()%>/pages/stock-order-update?no_so=<%=rowData.get(0)%>"><u>Edit</u></a></td>
		<td class="item"><input onClick="uncheckcheckAll(document.frmSearch.deleted,<%=tableData.size()%>)" type="checkbox" value="<%=rowData.get(0)%>" name=deleted></td>
	</tr>
	<tr>
    <td colspan="<%=rowData.size()-1%>">
        <input type="submit" name="Action" value="Delete" onClick="deletes(<%=i%>)"></button>      
    </td>
    <td>
        <input onClick="checkAll(document.frmSearch.deleted,<%=i%>)" type="checkbox" value="" name=checkall> All
    </td>
    </tr>
	<%	
		i++;
		}
	%>	
</table>
<p align="right">
<%  if(pages>1){ %>
        <a href="<%=request.getContextPath()%>/pages/stock-order-update?page=<%=pages-1%>&tanggal_so_awal=<%=tanggaSOAwal%>&tanggal_so_akhir=<%=tanggaSOAkhir%>&nomor_so=<%=nomorSO%>"><u>Prev</u></a>
<%
    }
%>
   Page <%=pages%> of <%=total_pages%>   
<%  if (pages<total_pages){ %>   
        <a href="<%=request.getContextPath()%>/pages/stock-order-update?page=<%=pages+1%>&tanggal_so_awal=<%=tanggaSOAwal%>&tanggal_so_akhir=<%=tanggaSOAkhir%>&nomor_so=<%=nomorSO%>"><u>Next</u></a>
<% 
    }
%>        
</p>
<%} %>
</form>
<%@ include file="/includes/footer.jsp"%>