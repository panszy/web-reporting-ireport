<%@ page import="web.UserSession" %>
<%@ page import="web.User" %>
<%@ page import="java.util.ArrayList" %>
<%
    UserSession userSess = UserSession.Factory.getUserSession(request);
    if (userSess.isLoggedIn()) {
        User us = userSess.getUser();        
%>
<table>
<%
    if (us.getMenus().contains("user management")) {
%>
<tr>
    <td>
        <img src="<%=request.getContextPath()%>/images/icons/system-users.png">
    </td>
    <td>
        <b>User Management</b>
    </td>
</tr>
<tr>
    <td>
    </td>
    <td>
        <a href="<%=request.getContextPath()%>/pages/admin/createuser">Create new user</a>
    </td>
</tr>
<tr>
    <td>
    </td>
    <td>
        <a href="<%=request.getContextPath()%>/pages/admin/search-user">Search user</a>
    </td>
</tr>
<tr>
    <td>
    </td>
    <td>
        <a href="<%=request.getContextPath()%>/pages/admin/update-group">Create user group</a>
    </td>
</tr>
<%
    }
    if (us.getMenus().contains("stock order") || us.getMenus().contains("approve")) {
%>
<tr>
    <td>
        <img src="<%=request.getContextPath()%>/images/icons/system-users.png">
    </td>
    <td>
        <b>Stock Order</b>
    </td>
</tr>
<% } if (us.getMenus().contains("stock order")){%>
<tr>
    <td>
    </td>
    <td>
        <a href="<%=request.getContextPath()%>/pages/stock-order-update">Stock Order</a>
    </td>
</tr>
<% } 
if (us.getMenus().contains("approve")){%>
<tr>
    <td>
    </td>
    <td>
        <a href="<%=request.getContextPath()%>/pages/approve">Approve</a>
    </td>
</tr>
<%
	}    	   
    if (us.getMenus().contains("sample report")) {
%>
<tr>
    <td>
        <img src="<%=request.getContextPath()%>/images/icons/system-users.png">
    </td>
    <td>
        <b>Reporting</b>
    </td>
</tr>
<tr>
    <td>
    </td>
    <td>
        <a href="<%=request.getContextPath()%>/pages/report/rincian-invoice.jsp">Laporan Rincian Invoice</a>
    </td>
</tr>
<tr>
    <td>
    </td>
    <td>
        <a href="<%=request.getContextPath()%>/pages/report/metrep.jsp">Laporan Metrep</a>
    </td>
</tr>
<%
    	}
    %>
    </table>
    <%
    }
%>