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
</table>
<%
    	}
    }
%>