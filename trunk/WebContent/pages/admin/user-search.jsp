<%@page import="common.DateFormatter"%>
<%@page import="web.UserSession"%>
<%@page import="web.User"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set" %>
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
<%@ include file="/includes/header.jsp"%>
<%@page import="java.util.ArrayList"%>
<form action="" method="post" name="frmSearch">

<img src="<%=request.getContextPath()%>/images/icons/system-users.png">
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
                    <td class="item-header" colspan=8>
                        List of Users
                    </td>
                </tr>

    <tr>
        <td><b>Username</b></td>
        <td><b>Full Name</b></td>
        <td><b>NIK</b></td>
        <td><b>Email</b></td>
        <td><b>Cabang</b></td>
        <td><b>Division</b></td>
        <td><b>Address</b></td>
        <td><b>Role</b></td>
        <td><b>Status</b></td>
        <td><b>Edit</b></td>
        <td><b>Check</b></td>
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
//        if((!((User)listofuser.get(i)).getRoles().contains(4))&&(!((User)listofuser.get(i)).getRoles().contains(0))||(((User)listofuser.get(i)).getUsername().equals(userSession.getUser().getUsername()))){     

    %>
    <tr>
        <td><%=((User) listofuser.get(i)).getUsername()%></a></td>
        <td><%=((User) listofuser.get(i)).getFullName()%></td>
        <td><%=((User) listofuser.get(i)).getNik()%></td>
        <td><%=((User) listofuser.get(i)).getEmailAddress()%></td>
        <td><%=((User) listofuser.get(i)).getCabang()%></td>
        <td><%=((User) listofuser.get(i)).getDivision()%></td>
        <td><%=((User) listofuser.get(i)).getAddress()%></td>
        <td><%=((User) listofuser.get(i)).getGroup()%></td> 
        <td><%=mapStatus.get(new Integer(((User) listofuser.get(i)).getStatus()).toString()) %></td>
        <td><a href="<%=request.getContextPath()%>/pages/admin/update-user?user=<%=((User) listofuser.get(i)).getUsername()%>"><u>Edit</u></a></td>  
    
        <%if(! ((User) listofuser.get(i)).getUsername().equalsIgnoreCase("admin")){%>
        <td><input onClick="uncheckcheckAll(document.frmSearch.deleted,<%=listofuser.size()%>)" type="checkbox" value="<%=((User) listofuser.get(i)).getUsername()%>" name=deleted></td>
        <%}else{ %>
         <td></td>
          <%} %>
   </tr>
    <%  
  //  }
        i++;
        }
    %>  
    <tr>
    <td colspan=10>
        <input type="submit" name="Action" value="Deactivate" onClick="deactives(<%=i%>)"></button>      
    </td>
    <td>
        <input onClick="checkAll(document.frmSearch.deleted,<%=i%>)" type="checkbox" value="" name=checkall> All
    </td>
    </tr>   
</table>
<p align="right">
<%  if(pages>1){ %>
        <a href="<%=request.getContextPath()%>/pages/admin/search-user?page=<%=pages-1%>&KindOfsearch=<%=KindOfsearch%>&WordOfsearch=<%=WordOfsearch%>"><u>Prev</u></a>
<%
    }
%>
   Page <%=pages%> of <%=total_pages%>   
<%  if (pages<total_pages){ %>   
        <a href="<%=request.getContextPath()%>/pages/admin/search-user?page=<%=pages+1%>&KindOfsearch=<%=KindOfsearch%>&WordOfsearch=<%=WordOfsearch%>"><u>Next</u></a>
<% 
    }
%>        
</p>
<% } %>
</p>
<p>&nbsp;</p>    
</form>
<%@ include file="/includes/footer.jsp"%>
