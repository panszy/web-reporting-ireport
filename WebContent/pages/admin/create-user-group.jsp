<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>   
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %> 
<%String title="Add User Group"; 
  List menuList=(List)request.getAttribute("menu");
 Map menuMap=(Map)request.getAttribute("menuMap");
  Iterator iter=menuMap.keySet().iterator();
  String menu;
  String groupId=(String)request.getAttribute("groupId");
  String groupName=(String)request.getAttribute("groupName");
%>
<%@ include file="/includes/header.jsp"%>
<table>
   
    <tr>
        <td>
                <img src="<%=request.getContextPath()%>/images/icons/system-users.png"> 
                <b>Input User Group</b>
        </td>
    </tr>
    <tr>
        <td>
            Input Data for New User Group.
        </td>
    </tr>
          <%if(request.getAttribute("error")!=null){ %>
                
                    <tr><td><font color="red"><%=request.getAttribute("error")%></font></td></tr>
                <%}%>
    
</table>

  <form action="add-user-group" method="post">

 <table class="item" style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;" width=400>
	 <tr>
                    <td class="item-header" colspan=2>
                       User Group Information
                    </td>
                </tr>                   
     <tr><td class="item">Group Name</td><td class="item">
     <%if(groupName!=null){ %>
     <input type="text" name="groupname" value="<%=groupName%>"><%}else{ %>
     <input type="text" name="groupname">
     <%}%>
     </td></tr>
        </table>
   <table class="item" style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;" width=400>
   <tr>
                    <td class="item-header" colspan=2>
                       Menu List
                    </td>
                </tr>
 
     <%if(menuList==null){while(iter.hasNext()){ 
      menu=(String)iter.next();
     %>
 
    <tr><td class="item"> <input type="checkbox" name="<%=menu%>"></td><td class="item"><%=menuMap.get(menu)%></td></tr>
     <%} }else{
    while(iter.hasNext()){ 
      menu=(String)iter.next();if(menuList.contains(menu)){%>
     <tr><td class="item"> <input type="checkbox" name="<%=menu%>" checked></td><td class="item"><%=menuMap.get(menu) %></td></tr>
    <%}else{ %>
     <tr><td class="item"> <input type="checkbox" name="<%=menu%>"></td><td class="item"><%=menuMap.get(menu)%></td></tr>
    <%}}} %>
   </table>  
     <input type="submit" value="Submit"><input type="reset">
  </form>
 
<%@ include file="/includes/footer.jsp"%>