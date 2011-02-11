<%@ page import="com.torepo.web.web.User" %>
<%@ page import="com.torepo.web.web.HttpConstants" %>
<%@ page import="com.torepo.web.util.MenuData" %>
<% User user = (User) request.getAttribute(HttpConstants.ATTR_NAME_USER);
    MenuData datas = (MenuData) request.getAttribute("menu");
%>
<%
    String title = "Edit " + datas.getMainLabel();
    String message = (String) request.getAttribute("message");
%>
<%@ include file="/includes/header.jsp" %>
<table class="item"
       style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;"
       width=450>
    <b><%=message%>
    </b>
    <%
        if (message!=null && message.indexOf("deleted") < 0) {
    %>
    <br>
    <tr>
        <td class='item-header' colspan="5"><%=datas.getMainLabel() + " Information"%>
        </td>
    </tr>
    <% for (int j = 0; j < datas.getFieldName().size(); j++) {
    %>
    <tr>
        <td class='item'><%=datas.getFieldName().get(j)%>
        </td>
        <%
            if (datas.getFieldType().get(j).equals("TIMESTAMP")) {
        %>
        <td class='item'><input readonly name="<%=datas.getFieldName().get(j)%>"
                                value="<%=request.getParameter(datas.getFieldName().get(j))+" "+request.getParameter(datas.getFieldName().get(j)+"_jam")+":"+request.getParameter(datas.getFieldName().get(j)+"_menit")+":"+request.getParameter(datas.getFieldName().get(j)+"_detik")%>">
        </td>
        <%
        } else {
        %>
        <td class='item'><input readonly name="<%=datas.getFieldName().get(j)%>"
                                value="<%=request.getParameter(datas.getFieldName().get(j))%>"></td>
        <%
            }
        %>
    </tr>
    <%
            }
        }
    %>

</table>
<%@ include file="/includes/footer.jsp" %>
