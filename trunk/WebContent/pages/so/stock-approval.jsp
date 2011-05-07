<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%
String title = "Stock Approval";
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
<script type="text/javascript" src="/ireport/script/calendar.js"></script>
<script type="text/javascript" src="/ireport/script/calendar-en.js"></script>
<script type="text/javascript" src="/ireport/script/calendar-setup.js"></script>
<link href="/ireport/script/calendar-blue2.css"rel="stylesheet" type="text/css" />
<script type="text/javascript">    
        
    function search(){
    	ChangeValue(document.info.Action,'Search')
    	document.info.submit();
    }
    
    function approves(total,message) {
        i = 0;
        var condition = false;      
        while(i < document.info.approved.length){
            if(document.info.approved[i].checked==true){
                condition = true;
                break;
            }
            i++;
        }
        if(condition || (total>0 && document.info.approved.checked==true)){
            if (confirm("Are you sure you want to "+message+"?")) {
	            ChangeValue(document.info.Action,message);
                document.info.submit();
            }                       
        } else {
            alert ('You didn\'t choose any of the checkboxes!');
        }
    }                        
    
</script>

<form id="info" name="info" action="" method="post">

<img src="<%=request.getContextPath()%>/images/icons/system-users.png">
<b>Search Stock Order</b> <br><br>

<table>
<tr>
<td>Tanggal SO</td>
<td><input type="text" size="10" readonly name="tanggal_so_awal" id="datebeforefrom" value="<%=tanggaSOAwal %>" >
<img src="<%=request.getContextPath()%>/script/calbtn.gif" width="18" height="18"
            id="datebeforefrom_trigger"
            style="cursor: pointer;" title="Date selector" align="middle"/>
		<script type="text/javascript">
            Calendar.setup(
            {
                inputField  : "datebeforefrom",
                ifFormat    : "%Y-%m-%d",
                button      : "datebeforefrom_trigger"
            }
                    );
        </script></td>
<td>-&nbsp;<input type="text" size="10" readonly name="tanggal_so_akhir" id="datebeforeto" value="<%=tanggaSOAkhir %>" >
<img src="<%=request.getContextPath()%>/script/calbtn.gif" width="18" height="18"
            id="datebeforeto_trigger"
            style="cursor: pointer;" title="Date selector" align="middle"/>
		<script type="text/javascript">
            Calendar.setup(
            {
                inputField  : "datebeforeto",
                ifFormat    : "%Y-%m-%d",
                button      : "datebeforeto_trigger"
            }
                    );
        </script></td>
        <td rowspan="3">
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
 </td>
</tr>
<tr>
<td>Nomor SO</td>
<td colspan="2"><input type="text" size="30" readonly name="nomor_so" value="<%=nomorSO %>" >&nbsp;<a onclick="OpenPop_UpList('<%=request.getContextPath()%>/pages/list?title=Cari%20NO%20SO%20SMS&tableTitle=Daftar%20NO%20SO%20SMS&itemName=nomor_so&showFields=no_so_sms,tgl_so_sms,no_po,tgl_po&queryData=kodeSOQuery');return false;" href="">Look up</a></td>
</tr>
</table>
<input type="button" value="Search" onClick="search()">
<input type="hidden" size=30 name="Action" value=""></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<%if(tableColumn!=null && tableColumn.size() > 0){ %>
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
		<td class="item"><input onClick="CheckItem(document.info.approved,document.info.deletedData)" type="checkbox" value="<%=rowData.get(0)+";"+rowData.get(2)+";"+rowData.get(4)%>" name="approved"></td>
	</tr>	
	<%	
		i++;
		}
	%>	
	<tr>
    <td class="item" colspan="<%=tableColumn.size()%>">
        <input type="button" name="Approve" value="Approve" onClick="approves(<%=i%>,'approve')"></button>      
        <input type="button" name="Protect" value="Protect" onClick="approves(<%=i%>,'protect')"></button>
        <input type="button" name="Cancel" value="Cancel" onClick="approves(<%=i%>,'cancel')"></button>
    </td>
    <td class="item">
        <input onClick="CheckAll(document.info.approved,document.info.deletedData)" type="checkbox" value="" name="all">
        <input type="hidden" name="deletedData" value="">
    </td>
    </tr>
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