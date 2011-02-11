<%@ page import="web.User" %>
<%@ page import="web.HttpConstants" %>
<% User user = (User) request.getAttribute(HttpConstants.ATTR_NAME_USER);
    String menu_name = (String) request.getAttribute("menu_name");
    String[] paramName = (String[]) request.getAttribute("name");
    String[] paramType = (String[]) request.getAttribute("type");
    String[] paramSize = (String[]) request.getAttribute("size");
    String[] paramMandatory = (String[]) request.getAttribute("mandatory");
    String[] paramEditable = (String[]) request.getAttribute("editable");
    String[] paramSearchable = (String[]) request.getAttribute("searchable");
%>
<%
    String title = "Add Menu";
%>
<%@ include file="/includes/header.jsp" %>
<table>
    <b> Menu is added successfully </b>
    <tr>
        <td>Menu Name:</td>
        <td class='item'><%=menu_name%>
        </td>
    </tr>
    <br>
    <table class="item"
           style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;"
           width=450>
        <tr>
            <td class='item-header'>Field Name</td>
            <td class='item-header'>Field Type</td>
            <td class='item-header'>Field Length</td>
            <td class='item-header'>Mandatory</td>
            <td class='item-header'>Editable</td>
            <td class='item-header'>Searchable</td>
        </tr>
        <%
            for (int i = 0; i < paramName.length; i++) {
        %>
        <tr>
            <td class='item'><%=paramName[i]%>
            </td>
            <td class='item'><%=paramType[i]%>
            </td>
            <td class='item'><%=paramSize[i]%>
            </td>
            <td class='item'><%=paramMandatory[i]%>
            </td>
            <td class='item'><%=paramEditable[i]%>
            </td>
            <td class='item'><%=paramSearchable[i]%>
            </td>
        </tr>
        <%
            }
        %>
    </table>
    <%@ include file="/includes/footer.jsp" %>
