<%@ page import="com.torepo.web.web.User" %>
<%@ page import="com.torepo.web.web.HttpConstants" %>
<%@ page import="com.torepo.web.util.MenuData" %>
<% User user = (User) request.getAttribute(HttpConstants.ATTR_NAME_USER);
    MenuData datas = (MenuData) request.getAttribute("menu");
%>
<%
    String title = "Create " + datas.getMainLabel();
%>
<%@ include file="/includes/header.jsp" %>
<form action="process" method="post" name="info">
<link rel="stylesheet" href="<%=request.getContextPath()%>/script/calendar-blue2.css" type="text/css"
      media="screen">
<script type="text/javascript" src="<%=request.getContextPath()%>/script/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/calendar-en.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/calendar-setup.js"></script>
<table>
<tr>
    <td>Please insert fields of the <%=datas.getMainLabel()%> you want to Add then click button Submit</td>
</tr>
<%
    String error = (String) request.getAttribute(HttpConstants.HTTP_VAR_ERROR);
    if (error != null) {
%>
<tr>
    <td>
        <font color="red"><%=error %>
        </font>
    </td>
</tr>
<%
    }
%>
<table class="item"
       style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;"
       width=450>
    <input type="hidden" value="create" name="action"/>
    <tr>
        <td class='item-header' colspan="5"><%=datas.getMainLabel() + " Information"%>
        </td>
    </tr>
    <% for (int j = 0; j < datas.getFieldName().size(); j++) {
    %>
    <tr>
        <td class='item'><%=datas.getFieldName().get(j)%><%=datas.getMandatory().get(j).equals("on")?"*":""%>
        </td>
        <%
            if (datas.getFieldType().get(j).equals("TIMESTAMP")) {
        %>
        <td class='item'><input readonly name="<%=datas.getFieldName().get(j)%>" id="datebefore<%=j%>" value=""><img
                src="<%=request.getContextPath()%>/script/calbtn.gif" width="30" height="18" id="datebefore_trigger<%=j%>"
                style="cursor: pointer;" title="Date selector" align="middle"/>
            <script type="text/javascript">
                Calendar.setup(
                {
                    inputField  : "datebefore<%=j%>",
                    ifFormat    : "%d/%m/%Y",
                    button      : "datebefore_trigger<%=j%>"
                }
                        );
            </script>

            <select
                    size=1 name="<%=datas.getFieldName().get(j)+"_jam"%>">
                <%
                    for (int i = 0; i < 24; i++) {
                        String val = Integer.toString(i);
                        val = (val.length() == 1) ? "0" + val : val;
                %>
                <option value="<%=val %>"><%=val %>
                </option>
                <%
                    }
                %>
                &lt;\SELECT&gt;
            </select>
            <select
                    size=1 name="<%=datas.getFieldName().get(j)+"_menit"%>">
                <%
                    for (int i = 0; i < 60; i++) {
                        String val = Integer.toString(i);
                        val = (val.length() == 1) ? "0" + val : val;
                %>
                <option value="<%=val %>"><%=val %>
                </option>
                <%
                    }
                %>
                &lt;\SELECT&gt;
            </select>
            <select
                    size=1 name="<%=datas.getFieldName().get(j)+"_detik"%>">
                <%
                    for (int i = 0; i < 60; i++) {
                        String val = Integer.toString(i);
                        val = (val.length() == 1) ? "0" + val : val;
                %>
                <option value="<%=val %>"><%=val %>
                </option>
                <%
                    }
                %>
                &lt;\SELECT&gt;
            </select>
        </td>
        <%
        } else {
        %>
        <td class='item' colspan="4"><input type='text'
                                            maxlength='<%=datas.getFieldSize().get(j)%>' <%if(datas.getFieldType().get(j).equals("INT")){%>
                                            onkeypress='return onlyNumbers(event)' <% } %>
                                            name='<%=datas.getFieldName().get(j)%>'></td>
        <%
            }
        %>
    </tr>
    <%
        }
    %>

</table>
<input type="submit" value="Submit">
<input type="reset" value="Reset">
</form>
<%@ include file="/includes/footer.jsp" %>
