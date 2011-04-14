<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%String title="Edit User Group"; %>
<%@ include file="/includes/header.jsp"%>
<% String groupId=(String)request.getAttribute("groupid");
   String groupName=(String)request.getAttribute("groupname");
   List groupMenuList=(List)request.getAttribute("menuList");
   Map menuMap=(Map)request.getAttribute("menuMap");
   String menuId;
   Iterator iter=menuMap.keySet().iterator();
%>
<table>
   
    <tr>
        <td>
                <img src="<%=request.getContextPath()%>/images/icons/system-users.png"> 
                <b>Edit User Group</b>
        </td>
    </tr>
    <tr>
        <td>
            Edit Data for User Group.
        </td>
    </tr>
</table><br>
 <table class="item" style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;" width=400>
	 <tr>
                    <td class="item-header" colspan=2>
                       User Group Information
                    </td>
                </tr>
<tr><td>Group ID</td>  <td>: <%=groupId%></td></tr>
<tr><td>Group Name</td><td>: <%=groupName%></td></tr>
</table>
  <table class="item" style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;" width=400>
   <tr>
                    <td class="item-header" colspan=2>
                       Menu List
                    </td>
                </tr>
 
 
<form action="update-user-group" method="post">
<input type="hidden" name="groupid" value="<%=groupId%>">
<input type="hidden" name="groupname" value="<%=groupName%>">

<%while(iter.hasNext()){
    menuId=(String)iter.next();
    if(groupMenuList.contains(menuId)){
  %>
   <tr><td><input type="checkbox" name=<%=menuId%> checked></td><td><%=menuMap.get(menuId)%></td></tr>
   <%}else{ %>
    <tr><td><input type="checkbox" name=<%=menuId%>></td><td><%=menuMap.get(menuId)%></td></tr>
  <%}} %>
  </table>
  <input type="submit" value= "Update">
  <input type="reset" value="Reset">
</form>  
<%@ include file="/includes/footer.jsp"%>
