<%@ page import="com.torepo.web.web.User" %>
<%@ page import="com.torepo.web.web.HttpConstants" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.PrintWriter" %>
<% User user = (User) request.getAttribute(HttpConstants.ATTR_NAME_USER);
%>
<%
    String title = "Edit Submenu";
    String isOpened = (String) request.getAttribute("isOpened");
    ArrayList<String> submenus = (ArrayList<String>) request.getAttribute("submenu");
    ArrayList<String> submenu_ids = (ArrayList<String>) request.getAttribute("submenu_id");
    ArrayList<String> name = (ArrayList<String>) request.getAttribute("name");
    ArrayList<String> type = (ArrayList<String>) request.getAttribute("type");
    ArrayList<String> size = (ArrayList<String>) request.getAttribute("size");
    ArrayList<String> mandatory = (ArrayList<String>) request.getAttribute("mandatory");
    ArrayList<String> editable = (ArrayList<String>) request.getAttribute("editable");
    ArrayList<String> searchable = (ArrayList<String>) request.getAttribute("searchable");
%>
<%@ include file="/includes/header.jsp" %>
<form action="edit-submenu" method="post" name="info">
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
            <%
                if (submenus != null) {
            %>
            Please select the menu you want to Edit then click button Submit
            <%
            } else {
            %>
            Please modify fields you want to Edit then click button Update
            <%
                }
            %>
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
        <td colspan='4'>Submenu Name:
            <%
                if (submenus != null) {
            %>
            <select name='submenu_id'>
                <%
                    for (int i = 0; i < submenus.size(); i++) {
                %>
                <option value='<%=submenu_ids.get(i)+"-"+submenus.get(i)%>'><%=submenus.get(i)%>
                </option>
                <%
                    }
                %>
            </select>
            <%
            } else {
            %>
            <input type='text' maxlength='50' name='submenu_name' value='<%=request.getAttribute("submenu_name")%>'></td>
            <input type="hidden" value="" name="submenu_id" value="<%=request.getAttribute("submenu_id")%>"/>
            <%
                }
            %>
    </tr>
</table>
<%
    if (isOpened == null) {
%>
<input type="submit" value="Submit">
<%
    }
%>
<br>
<%
    if (isOpened != null && isOpened.equals("yes")) {
%>
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
        <td class='item-header'>Remove Field</td>
    </tr>
</table>
<table>
    <input type="hidden" value="" name="deleted"/>
    <input type="hidden" value="" name="is_deleted"/>
    <div id="first_div">
    <%
        String names = "";
        JspWriter writer = pageContext.getOut();
        for (int i = 0; i < name.size(); i++) {
            writer.write("<tr><td><input type='text' maxlength='50' name='name' value='" + name.get(i) + "'></td><td><select name='type'><option " + (type.get(i).equals("VARCHAR") ? "selected" : "") + " value='VARCHAR'>String</option><option " + (type.get(i).equals("INT") ? "selected" : "") + " value='INT'>Integer</option><option " + (type.get(i).equals("TIMESTAMP") ? "selected" : "") + " value='TIMESTAMP'>Date Time</option></select></td><td><input type='text' maxlength='4' onkeypress='return onlyNumbers(event)' name='size'value='" + size.get(i) + "'></td><td><input type='checkbox' " + (mandatory.get(i).equals("on") ? "checked" : "") + " name='mandatory" + (i + 1) + "'></td><td><input type='checkbox' " + (editable.get(i).equals("on") ? "checked" : "") + " name='editable" + (i + 1) + "'></td><td><input type='checkbox' " + (searchable.get(i).equals("on") ? "checked" : "") + " name='searchable" + (i + 1) + "'></td><td><input type='checkbox' value='" + name.get(i) + "' onclick='CheckItem(document.info.removed,document.info.deleted)' name='removed'>*)Check to remove field</td></tr>");
            names += name.get(i) + ",";
        }
        names = names.substring(0, names.length() - 1);
    %>
    </div>
    <input type="hidden" name="oldFields" value="<%=names%>"/>
</table>
<table>
    <input type="hidden" value="<%=name.size()%>" id="theValue"/>
    <div id="my_div"></div>
</table>
<%
    }
%>
<%
    if (isOpened != null && isOpened.equals("yes")) {
%>
<input type="button" value="Add Field" onClick="addElementEdit()">
<input type="submit" value="Update">
<input type="submit" value="Delete" onClick="ChangeValue(document.info.is_deleted, 'yes')">
<input type="reset" value="Reset">
<%
    }
%>
</form>
<%@ include file="/includes/footer.jsp" %>
