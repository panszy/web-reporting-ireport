<%@ page import="com.torepo.web.web.UserSession" %>
<%@ page import="com.torepo.web.web.User" %>
<%@ page import="com.torepo.web.util.MenuData" %>
<%@ page import="java.util.ArrayList" %>
<%
    UserSession userSess = UserSession.Factory.getUserSession(request);
    if (userSess.isLoggedIn()) {
        User us = userSess.getUser();
        ArrayList<MenuData> data = userSess.getData();
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
<tr>
    <td colspan=2 height="8"></td>
</tr>
<tr>
    <td>
        <img src="<%=request.getContextPath()%>/images/icons/system-search.png">
    </td>
    <td>
        <b>Menu</b>
    </td>
</tr>
<tr>
    <td>
    </td>
    <td>
        <a href="<%=request.getContextPath()%>/pages/menu/add-menu">Add Menu</a>
    </td>
</tr>
<tr>
    <td>
    </td>
    <td>
        <a href="<%=request.getContextPath()%>/pages/menu/edit-menu">Edit Menu</a>
    </td>
</tr>
<tr>
    <td>
        <img src="<%=request.getContextPath()%>/images/icons/system-search.png">
    </td>
    <td>
        <b>Submenu</b>
    </td>
</tr>
<tr>
    <td>
    </td>
    <td>
        <a href="<%=request.getContextPath()%>/pages/menu/add-submenu">Add Submenu</a>
    </td>
</tr>
<tr>
    <td>
    </td>
    <td>
        <a href="<%=request.getContextPath()%>/pages/menu/edit-submenu">Edit Submenu</a>
    </td>
</tr>
<tr>
    <td colspan=2 height="8"></td>
</tr>
<%
    }
    int cnt=0;
    for (MenuData md : data) {
        if(cnt==0)
            cnt++;
        else {
%>
<tr>
    <td>
        <img src="<%=request.getContextPath()%>/images/icons/system-users.png">
    </td>
    <td>
        <b><%=md.getMainLabel()%>
        </b>
    </td>
</tr>
<%
    }
    int counter = 0;
    for (String m : md.getSubMenu()) {
        if(md.getMenuStyle()[0] || md.getSubMenu().get(counter).indexOf("Create") < 0){
%>
<tr>
    <td>
    </td>
    <td>
        <a href="<%=request.getContextPath()%>/pages/menu/process?index=<%=counter%>&menu=<%=md.getMainLabel()%>"><%=md.getSubMenu().get(counter)%>
        </a>
    </td>
</tr>
<%
        }
        counter++;
    }
%>
<tr>
    <td colspan=2 height="8"></td>
</tr>
<%
    }
%>
</table>
<%
    }
%>