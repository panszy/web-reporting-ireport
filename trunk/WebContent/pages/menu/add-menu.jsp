<%@ page import="com.torepo.web.web.User" %>
<%@ page import="com.torepo.web.web.HttpConstants" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.HashMap" %>
<% User user = (User) request.getAttribute(HttpConstants.ATTR_NAME_USER);
%>
<%
    String title = "Add Menu";
%>
<%@ include file="/includes/header.jsp" %>
<form action="add-menu" method="post" name="info">
    <table>

        <tr>
            <td colspan='4'>

                <img src="<%=request.getContextPath()%>/images/icons/system-users.png">
                <b><%=title %>
                </b>
            </td>
        </tr>
        <tr>
            <td colspan='4'>
                Please insert fields of the menu you want to Add then click button Submit
            </td>
        </tr>
        <%




            String error = (String) request.getAttribute(HttpConstants.HTTP_VAR_ERROR);
            if (error != null) {




        %>
        <tr>
            <td colspan='4'>
                <font color="red"><%=error %>
                </font>
            </td>
        </tr>
        <%


            }


        %>
        <tr>
            <td>Menu Name:</td>
            <td colspan='3'><input type='text' maxlength='50' name='menu_name'></td>
        </tr>
        <tr>
            <td colspan='4'>Submenu Option:</td>
        </tr>
        <tr>
            <td>Create<input type='checkbox' name='create_submenu' value='Create'></td>
            <td>Search<input type='checkbox' name='search_submenu' value='Search'></td>
            <td>Update<input type='checkbox' name='update_submenu' value='Update'></td>
            <td>Delete<input type='checkbox' name='delete_submenu' value='Delete'></td>
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
        </table>

        <table class="item"
               style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;"
               width=450>
            <input type="hidden" value="0" id="theValue"/>

            <div id="my_div">
            </div>
        </table>
        <input type="button" value="Add Field" onClick="addElement()">
        <input type="submit" value="Submit">
        <input type="reset" value="Reset">
</form>
<%@ include file="/includes/footer.jsp" %>
