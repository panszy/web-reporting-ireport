<%@ page import="com.torepo.web.util.DateFormatter" %>
<%@ page import="com.torepo.web.web.UserSession" %>
<%@ page import="com.torepo.web.web.User" %>
<%@ page import="com.torepo.web.web.HttpConstants" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.HashMap" %>
<% String title = "User Update"; %>
<% User user = (User) request.getAttribute(HttpConstants.ATTR_NAME_USER);
    HashMap groupMap = (HashMap) request.getAttribute("groupMap");
%>
<%@ include file="/includes/header.jsp" %>
<form action="" method="post" name="frmUpdate">
<table>
<script type="text/javascript">
    function confirmationUpdate() {
        if (confirm("Are you sure you want to update user")) {
            document.frmUpdate.Action.value = 'Update';
            document.frmUpdate.submit();
        }
    }

    function confirmationReset() {
        if (confirm("Are you sure you want to reset password")) {
            document.frmUpdate.Action.value = 'Reset Password';
            document.frmUpdate.submit();
        }
    }
    function uppercase()
    {
        key = window.event.keyCode;
        if ((key > 0x60) && (key < 0x7B))
            window.event.keyCode = key - 0x20;
    }

</script>
<tr>
    <td>
        <img src="<%=request.getContextPath()%>/images/icons/system-lock-screen.png">
        <b><%=title%>
        </b>
    </td>
</tr>
<tr>
    <td>
        Please modify fields you want to change then click button Update.
    </td>
</tr>
<%if (request.getAttribute("error") != null) {%>
<tr>
    <td><font color="red"><%=request.getAttribute("error")%>
    </font></td>
</tr>
<%} %>

<tr>
<td>
<table class="item"
       style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;"
       width=450>
<tr>
    <td class="item-header" colspan=2>
        User Information
    </td>
</tr>
<tr>
    <td class="item" width=120>
        User Id
    </td>
    <td class="item">
        <%=user.getUserId()%>
    </td>
</tr>
<tr>
    <td class="item" width=120>
        Username
    </td>
    <td class="item">
        <%=user.getUsername()%>
        <input type="hidden" size=30 name="origin_username" value="<%=user.getUsername()%>">
    </td>
</tr>
<tr>
    <td class="item" width=120>
        Full name
    </td>
    <td class="item">
        <input type="text" size=50 name="fullname" value="<%=user.getFullName()%>" onKeypress="uppercase();">
    </td>
</tr>
<tr>
    <td class="item">
        NIK
    </td>
    <td class="item">
        <input type="text" size=12 name="nik" value="<%=user.getNik()%>" onKeypress="uppercase();">
    </td>
</tr>
<tr>
    <td class="item">
        E-mail
    </td>
    <td class="item">
        <input type="text" size=30 name="email" value="<%=user.getEmailAddress()%>">
    </td>
</tr>
<tr>
    <td class="item">
        Department
    </td>
    <td class="item">
        <input type="text" size=30 name="department" value="<%=user.getDepartemen()%>" onKeypress="uppercase();">
    </td>
</tr>
<tr>
    <td class="item">
        Division
    </td>
    <td class="item">
        <input type="text" size=30 name="division" value="<%=user.getDivision()%>" onKeypress="uppercase();">
    </td>
</tr>

<tr>
    <td class="item">
        Address
    </td>
    <td class="item">
        <textarea name="address" cols="20" rows="8" onKeypress="uppercase();"><%=user.getAddress()%>
        </textarea>
    </td>
</tr>

<tr>
    <td class="item">User Group*</td>
    <td class="item"><select name="group">
        <%
            String group;
            Iterator iter = groupMap.keySet().iterator();
            while (iter.hasNext()) {
                group = (String) iter.next();
        %>
        <option value="<%=group%>"><%=groupMap.get(group)%>
        </option>
        <%} %></select></td>
</tr>

<tr>
    <td class="item" width=120>
        Status
    </td>
    <td class="item">

        <select name="status">
            <%if (user.getStatus() == user.STATUS_ACTIVE) { %>
            <option value="active" selected>Active</option>
            <%} else { %>
            <option value="active">Active</option>
            <%} %>
            <%if (user.getStatus() == user.STATUS_DEACTIVE) { %>
            <option value="deactive" selected>Deactive</option>
            <%} else { %>
            <option value="deactive">Deactive</option>
            <%} %>

            <%if (user.getStatus() == user.STATUS_LOCKED) { %>
            <option value="locked" selected>Locked</option>
            <%} else { %>
            <option value="locked">Locked</option>
            <%} %>

        </select>
    </td>
</tr>

</table>
</td>
</tr>

<tr>
    <td height=10>
        <button onClick="confirmationUpdate()">Update</button>
        <button onClick="confirmationReset()">Reset Password</button>
        <input type="hidden" size=30 name="Action" value="">
    </td>
</tr>
</table>
</form>
<%@ include file="/includes/footer.jsp" %>
