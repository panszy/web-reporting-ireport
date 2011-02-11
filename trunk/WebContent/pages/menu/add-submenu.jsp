<%@ page import="web.User" %>
<%@ page import="web.HttpConstants" %>
<%@ page import="java.util.*" %>
<% User user = (User) request.getAttribute(HttpConstants.ATTR_NAME_USER);
    ArrayList<String> menus = (ArrayList<String>) request.getAttribute("menu");
    ArrayList<String> menu_ids = (ArrayList<String>) request.getAttribute("menu_id");
%>
<%
    String title = "Add Submenu";
%>
<%@ include file="/includes/header.jsp" %>
<form action="add-submenu" method="post" name="info">
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
                Please insert fields of the submenu you want to Add then click button Submit
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
            <td>Submenu Name:</td>
            <td colspan='3'><input type='text' maxlength='50' name='menu_name'></td>
        </tr>
        <tr>
            <td>Link to menu:</td> <td>
            <%
                if (menus != null) {
            %>
            <select name='menu_id'>
                <%
                    for (int i = 0; i < menus.size(); i++) {
                %>
                <option value='<%=menu_ids.get(i)+"-"+menus.get(i)%>'><%=menus.get(i)%>
                </option>
                <%
                    }
                %>
            </select>
            <%
            } 
            %>
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
