<%@page import="common.DateFormatter"%>
<%@page import="web.UserSession"%>
<%@page import="web.User"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%
String title = "User Search";
int pages=request.getAttribute("pages")==null?1:Integer.parseInt((String)request.getAttribute("pages"));
int total_pages=request.getAttribute("total_pages")==null?1:Integer.parseInt((String)request.getAttribute("total_pages"));
String WordOfsearch =request.getParameter("WordOfsearch")==null?"":request.getParameter("WordOfsearch");
String KindOfsearch =request.getParameter("KindOfsearch")==null?"":request.getParameter("KindOfsearch");

%>
<%
    User user = UserSession.Factory.getUserSession(request).getUser();
    ArrayList listofuser = null;
    if (request.getAttribute("listOfUser") != null){
        listofuser = (ArrayList) request.getAttribute("listOfUser");
    }
%>
<%@page import="java.util.ArrayList"%>

<form action="" method="post" name="frmSearch">

	<script language="javascript">
        function validepopupform(field1,field2){
			field1.value=field2;
			window.close();
		}    	  
    </script>

<b>Search User</b> <br><br>
<select
    size=1 name=field>
    <option value="Username" <%if(KindOfsearch.equalsIgnoreCase("Username")){ %> selected <%} %>>Username</option>
    <option value="full_name" <%if(KindOfsearch.equalsIgnoreCase("full_name")){ %> selected <%} %>>Full Name</option>
    <option value="NIK" <%if(KindOfsearch.equalsIgnoreCase("NIK")){ %> selected <%} %>>NIK</option>
    <option value="email_address" <%if(KindOfsearch.equalsIgnoreCase("email_address")){ %> selected <%} %>>Email address</option>
    <option value="status" <%if(KindOfsearch.equalsIgnoreCase("status")){ %> selected <%} %>>Status</option>
    &lt;\SELECT&gt;
</select>
&nbsp;&nbsp;&nbsp;&nbsp; 
<%if(WordOfsearch!=null){ %>
<input type="text" size="30" name="User" value="<%=WordOfsearch%>">
<%}else{ %>

<input type="text" size="30" name="User" value="" >
<%} %>
&nbsp;&nbsp;&nbsp;
<input type="submit" value="Search" name="Action"></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<%if (listofuser != null && listofuser.size() > 0){ %>
<table class="item" border=1 cellSpacing=0 cellPadding=3 style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;" width=800>                         
    <tr>
                    <td class="item-header" colspan="9">
                        List of Users
                    </td>
                </tr>

    <tr>
        <td><b>Username</b></td>
        <td><b>Full Name</b></td>
        <td><b>NIK</b></td>
        <td><b>Email</b></td>
        <td><b>Department</b></td>
        <td><b>Division</b></td>
        <td><b>Address</b></td>
        <td><b>Role</b></td>
        <td><b>Status</b></td>           
   </tr>
    <%
      Map mapStatus=new HashMap();
      mapStatus.put("0","Active");
      mapStatus.put("1","Deactive");
      mapStatus.put("2","Locked");
    %>
    <%
        int i = 0;      
        StringBuffer buffer;
        while (listofuser != null && listofuser.size() > i) {     
    %>
    <tr>
        <td><a href="" onclick="validepopupform(window.opener.document.forms['myForm'].elements['item-2'],'<%=((User) listofuser.get(i)).getUsername()%>');return false;"><%=((User) listofuser.get(i)).getUsername()%></a></td>
        <td><%=((User) listofuser.get(i)).getFullName()%></td>
        <td><%=((User) listofuser.get(i)).getNik()%></td>
        <td><%=((User) listofuser.get(i)).getEmailAddress()%></td>
        <td><%=((User) listofuser.get(i)).getDepartemen()%></td>
        <td><%=((User) listofuser.get(i)).getDivision()%></td>
        <td><%=((User) listofuser.get(i)).getAddress()%></td>
        <td><%=((User) listofuser.get(i)).getGroup()%></td> 
        <td><%=mapStatus.get(new Integer(((User) listofuser.get(i)).getStatus()).toString()) %></td>                     
   </tr>
    <%  
  //  }
        i++;
        }
    %>       
</table>
<p align="right">
<%  if(pages>1){ %>
        <a href="<%=request.getContextPath()%>/pages/list?page=<%=pages-1%>&KindOfsearch=<%=KindOfsearch%>&WordOfsearch=<%=WordOfsearch%>"><u>Prev</u></a>
<%
    }
%>
   Page <%=pages%> of <%=total_pages%>   
<%  if (pages<total_pages){ %>   
        <a href="<%=request.getContextPath()%>/pages/list?page=<%=pages+1%>&KindOfsearch=<%=KindOfsearch%>&WordOfsearch=<%=WordOfsearch%>"><u>Next</u></a>
<% 
    }
%>        
</p>
<% } %>
</p>
<p>&nbsp;</p>    
</form>
