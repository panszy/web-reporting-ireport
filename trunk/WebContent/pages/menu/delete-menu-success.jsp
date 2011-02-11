<%@ page import="com.torepo.web.web.User" %>
<%@ page import="com.torepo.web.web.HttpConstants" %>
<% User user = (User) request.getAttribute(HttpConstants.ATTR_NAME_USER);
    String menu_name = (String) request.getAttribute("menu_name");    
%>
<%
    String title = "Edit Menu";
%>
<%@ include file="/includes/header.jsp" %>
<table>
    <b> Menu <%=menu_name%> is Deleted successfully </b>
</table>
    <%@ include file="/includes/footer.jsp" %>
