<%@ page import="web.User" %>
<%@ page import="web.HttpConstants" %>
<% User user = (User) request.getAttribute(HttpConstants.ATTR_NAME_USER);
    String submenu_name = (String) request.getAttribute("submenu_name");
%>
<%
    String title = "Edit Submenu";
%>
<%@ include file="/includes/header.jsp" %>
<table>
    <b> Submenu <%=submenu_name%> is Deleted successfully </b>
</table>
    <%@ include file="/includes/footer.jsp" %>
