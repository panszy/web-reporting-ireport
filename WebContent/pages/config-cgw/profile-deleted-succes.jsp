<%@page import="common.DateFormatter" %>
<%@page import="web.UserSession" %>
<%@page import="web.User" %>
<%@page import="web.HttpConstants" %>
<%
String title = "User Search";
String message = (String)request.getParameter("message");
%>
<%@ include file="/includes/header.jsp" %>
<table>
    <tr>
		<td>
   Rating Entry have been Deleted successfully.
    </td>
	    </tr>
    <%String[]del=(String[])request.getAttribute("delete"); 
       for(int i=0;i<del.length;i++)
       {
    %>   
    <tr>
       <td><%=i+1%>.<%=del[i]%></td>
    </tr>
    <%} %> 
</table>
<%@ include file="/includes/footer.jsp" %>