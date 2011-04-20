<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%String title="User Group"; %>
<%@ include file="/includes/header.jsp" %>
<script type="text/javascript">
 function deletes() {
	    i = 0;
        var condition = false;	    
        while(i < document.groupform.deleted.length){
	        if(document.groupform.deleted[i].checked==true){
	            condition = true;
	            break;
	        }
	        i++;
	    }
        if(condition ){
	        if (confirm("Are you sure you want to delete")) {
                document.groupform.submit();
            } 	        	        
	    } else {
	        alert ('You didn\'t choose any of the checkboxes!');
	    }
	}
  function add()
  {
    document.groupform.submit();
  }
</script>
<table>
   
    <tr>
        <td>
                <img src="<%=request.getContextPath()%>/images/icons/system-users.png"> 
                <b>User Group</b>
        </td>
    </tr>
    <tr>
        <td>
            Add,Modify, and Delete User Group.
        </td>
    </tr>
</table>
<table class="item" border=1  style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;" width=200>
<tr><td class="item-header"></td><td class="item-header">User Group Name</td></tr>

<% 
  Map groupMap=(Map)request.getAttribute("menuMap");
  String groupid;
  Iterator iter=groupMap.keySet().iterator();
  while(iter.hasNext()){
      groupid=(String)iter.next();
%>

<form action="user-group-servlet" method="get" name="groupform">
<tr><td><input type="checkbox" name='deleted' value="<%=groupid%>"></td><td><a href="<%=request.getContextPath()%>/pages/admin/edit-user-group?groupid=<%=groupid%>&groupname=<%=groupMap.get(groupid)%>"><%=groupMap.get(groupid)%></a></td></tr>
  <%} %>
    </table>
<button name="ActionAdd" value="Add" onClick="add()">Add</button><button name="Action" value="Delete" onClick="deletes()">Delete</button>  
</form>  
<%@ include file="/includes/footer.jsp" %>
