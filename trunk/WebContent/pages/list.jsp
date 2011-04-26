<%@page import="common.DateFormatter"%>
<%@page import="web.UserSession"%>
<%@page import="web.User"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%
String title = request.getParameter("title");
String tableTitle = request.getParameter("tableTitle");
String[] showFields = request.getParameter("showFields").split(","); 
String showFieldsFlat = request.getParameter("showFields");
String itemName = request.getParameter("itemName");
String queryData = request.getParameter("queryData");
int pages=request.getAttribute("pages")==null?1:Integer.parseInt((String)request.getAttribute("pages"));
int total_pages=request.getAttribute("total_pages")==null?1:Integer.parseInt((String)request.getAttribute("total_pages"));
String WordOfsearch =request.getParameter("WordOfsearch")==null?"":request.getParameter("WordOfsearch");
String KindOfsearch =request.getParameter("KindOfsearch")==null?"":request.getParameter("KindOfsearch");

%>
<%    
    List<ArrayList<String>> listofuser = null;
    if (request.getAttribute("listOfUser") != null){
        listofuser = (List<ArrayList<String>>) request.getAttribute("listOfUser");
    }
%>
<%@page import="java.util.ArrayList"%>

<form action="" method="post" name="frmSearch">
<input type="hidden" value="<%=title %>" name="title">
<input type="hidden" value="<%=tableTitle %>" name="tableTitle">
<input type="hidden" value="<%=showFieldsFlat %>" name="showFields">
<input type="hidden" value="<%=itemName %>" name="itemName">
<input type="hidden" value="<%=queryData %>" name="queryData">
<link rel="stylesheet" href="<%=request.getContextPath()%>/style.css"></link>
	<script language="javascript">
        function validepopupform(field1,field2){
			field1.value=field2;
			window.close();
		}    	  
    </script>

<b><%=title%></b> <br><br>
<select
    size=1 name=field>
    <% for(String showField : showFields){ %>
    <option value="<%=showField %>" <%if(KindOfsearch.equalsIgnoreCase(showField)){ %> selected <%} %>><%=showField %></option>
    <%} %>    
    &lt;\SELECT&gt;
</select>
&nbsp;&nbsp;&nbsp;&nbsp; 
<%if(WordOfsearch!=null){ %>
<input type="text" size="30" name="Value" value="<%=WordOfsearch%>">
<%}else{ %>

<input type="text" size="30" name="Value" value="" >
<%} %>
&nbsp;&nbsp;&nbsp;
<input type="submit" value="Search" name="Action"></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<%if (listofuser != null && listofuser.size() > 0){ %>
<table class="item" border=1 cellSpacing=0 cellPadding=3 style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;" width=800>                         
    <tr>
                    <td class="item-header" colspan="<%=showFields.length %>">
                        <%=tableTitle%>
                    </td>
                </tr>

    <tr>
    <% for (String showField: showFields){ %>    
        <td><b><%=showField%></b></td>
        <%} %>                  
   </tr>    
    <%
        int i = 0;      
        StringBuffer buffer;
        while (listofuser != null && listofuser.size() > i) {
        	int j = 0;
    %>
    <tr>
    <% for (String data: listofuser.get(i)){
    	if(j==0){
    %>    
        <td><a href="" onclick="validepopupform(window.opener.document.forms['info'].elements['<%=itemName%>'],'<%=data%>');return false;"><%=data%></a></td>
        <%} else { %> 
        <td><%=data%></td>
        <%     	
    	}  	
    	j++;
    }
    %>
    </tr>
    <%
        i++;
        }
    %>           
</table>
<p align="right">
<%  if(pages>1){ %>
        <a href="<%=request.getContextPath()%>/pages/list?page=<%=pages-1%>&KindOfsearch=<%=KindOfsearch%>&WordOfsearch=<%=WordOfsearch%>&title=<%= title.replaceAll(" ","%20")%>&tableTitle=<%= tableTitle.replaceAll(" ","%20")%>&itemName=<%= itemName.replaceAll(" ","%20")%>&showFields=<%= showFieldsFlat.replaceAll(" ","%20")%>&queryData=<%= queryData.replaceAll(" ","%20")%>"><u>Prev</u></a>
<%
    }
%>
   Page <%=pages%> of <%=total_pages%>   
<%  if (pages<total_pages){ %>   
        <a href="<%=request.getContextPath()%>/pages/list?page=<%=pages+1%>&KindOfsearch=<%=KindOfsearch%>&WordOfsearch=<%=WordOfsearch%>&title=<%= title.replaceAll(" ","%20")%>&tableTitle=<%= tableTitle.replaceAll(" ","%20")%>&itemName=<%= itemName.replaceAll(" ","%20")%>&showFields=<%= showFieldsFlat.replaceAll(" ","%20")%>&queryData=<%= queryData.replaceAll(" ","%20")%>"><u>Next</u></a>
<% 
    }
%>        
</p>
<% } %>
</p>
<p>&nbsp;</p>    
</form>
