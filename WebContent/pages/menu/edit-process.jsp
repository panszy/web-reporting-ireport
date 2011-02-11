<%@ page import="com.torepo.web.web.User" %>
<%@ page import="com.torepo.web.web.HttpConstants" %>
<%@ page import="com.torepo.web.util.MenuData" %>
<%@ page import="java.util.ArrayList" %>
<% User user = (User) request.getAttribute(HttpConstants.ATTR_NAME_USER);
    MenuData datas = (MenuData) request.getAttribute("menu");
    String title = "Create " + datas.getMainLabel();
    ArrayList<ArrayList<String>> dataDetail = (ArrayList<ArrayList<String>>) request.getAttribute("datas");
    String isOpened = (String) request.getAttribute("isOpened");
    String pages = (String) request.getAttribute("page");
    boolean isSearchableExist = false;
%>
<%@ include file="/includes/header.jsp" %>
<form action="process" method="post" name="info">
<link rel="stylesheet" href="<%=request.getContextPath()%>/script/calendar-blue2.css" type="text/css"
      media="screen">
<script type="text/javascript" src="<%=request.getContextPath()%>/script/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/calendar-en.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/calendar-setup.js"></script>
<table>
    <input type="hidden" value="" name="action"/>
    <tr>
        <%
            for (int i = 0; i < datas.getFieldName().size(); i++) {
                if (datas.getSearchable().get(i).equals("on")) {
                    isSearchableExist = true;
                    break;
                }
            }
            if (isSearchableExist && isOpened == null && datas.getMenuStyle()[1]) {
        %>
        <td>Please type the field of the <%=datas.getMainLabel()%> you want to search then click button Search</td>
        <%
        } else if (datas.getMenuStyle()[2]) {
        %>
        <td>Please click record of the <%=datas.getMainLabel()%> you want to edit</td>
        <%
        } else {
        %>
        Here are records of the <%=datas.getMainLabel()%>
        <%
            }
        %>
    </tr>
    <br>


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
</table>
<%
    if (datas.getMenuStyle()[1]) {
%>
<table>
<tr>
    <%
        for (int i = 0; i < datas.getFieldName().size(); i++) {
            if (!datas.getFieldType().get(i).equals("TIMESTAMP") && datas.getSearchable().get(i).equals("on")) {
    %>
    <td>
        <%=datas.getFieldName().get(i)%>:
    </td>
    <td>
        <input type='text' name='<%=datas.getFieldName().get(i)%>'>
    </td>
    <%
    } else if (datas.getSearchable().get(i).equals("on")) {
    %>
</tr>
<tr>
    <td>
        <%=datas.getFieldName().get(i) + " From:"%>
    </td>
    <td colspan='3'><input readonly name="<%=datas.getFieldName().get(i)%>from" id="datebeforefrom<%=i%>" value=""><img
            src="<%=request.getContextPath()%>/script/calbtn.gif" width="30" height="18"
            id="datebeforefrom_trigger<%=i%>"
            style="cursor: pointer;" title="Date selector" align="middle"/>
        <script type="text/javascript">
            Calendar.setup(
            {
                inputField  : "datebeforefrom<%=i%>",
                ifFormat    : "%d/%m/%Y",
                button      : "datebeforefrom_trigger<%=i%>"
            }
                    );
        </script>

        <select
                size=1 name="<%=datas.getFieldName().get(i)+"from_jam"%>">
            <%
                for (int j = 0; j < 24; j++) {
                    String val = Integer.toString(j);
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
                size=1 name="<%=datas.getFieldName().get(i)+"from_menit"%>">
            <%
                for (int j = 0; j < 60; j++) {
                    String val = Integer.toString(j);
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
                size=1 name="<%=datas.getFieldName().get(i)+"from_detik"%>">
            <%
                for (int j = 0; j < 60; j++) {
                    String val = Integer.toString(j);
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
</tr>
<tr>
    <td>
        <%=datas.getFieldName().get(i) + " To:"%>
    </td>
    <td colspan='3'><input readonly name="<%=datas.getFieldName().get(i)%>to" id="datebeforeto<%=i%>" value=""><img
            src="<%=request.getContextPath()%>/script/calbtn.gif" width="30" height="18" id="datebeforeto_trigger<%=i%>"
            style="cursor: pointer;" title="Date selector" align="middle"/>
        <script type="text/javascript">
            Calendar.setup(
            {
                inputField  : "datebeforeto<%=i%>",
                ifFormat    : "%d/%m/%Y",
                button      : "datebeforeto_trigger<%=i%>"
            }
                    );
        </script>

        <select
                size=1 name="<%=datas.getFieldName().get(i)+"to_jam"%>">
            <%
                for (int j = 0; j < 24; j++) {
                    String val = Integer.toString(j);
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
                size=1 name="<%=datas.getFieldName().get(i)+"to_menit"%>">
            <%
                for (int j = 0; j < 60; j++) {
                    String val = Integer.toString(j);
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
                size=1 name="<%=datas.getFieldName().get(i)+"to_detik"%>">
            <%
                for (int j = 0; j < 60; j++) {
                    String val = Integer.toString(j);
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
</tr>
<tr>
    <%
        }
        if ((i + 1) % 3 == 0 && i != datas.getFieldName().size()) {
    %>
</tr>
<tr>
    <%
            }
        }
    %>
</tr>
</table>
<%
    if (isSearchableExist) {
%>
<input type="submit" value="Search" onclick='ChangeValue(document.info.action,"searching")'>
<br>
<%
        }
    }
    if (((isOpened != null && isOpened.equals("yes")) || !isSearchableExist || !datas.getMenuStyle()[1]) && dataDetail.size() > 0) {
%>
<div ALIGN='right'>
    <%

        if (pages != null && !pages.equals("1")) {
    %>
    <a href="<%=request.getContextPath()%>/pages/menu/process?edit=pagingprev<%=pages%>">Prev</a>
    <%
        }
        if (pages != null) {
    %>
    Page <%=pages%>
    <a href="<%=request.getContextPath()%>/pages/menu/process?edit=pagingnext<%=pages%>">Next</a>
</div>
<%
    }
%>
<table class="item"
       style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;"
       width=450>
    <input type="hidden" value="" name="deleted"/>
    <tr>
        <% for (int j = 0; j < datas.getFieldName().size(); j++) {
        %>
        <td class='item-header'><%=datas.getFieldName().get(j)%>
        </td>
        <%
            }
            if (datas.getMenuStyle()[3]) {
        %>
        <td class='item-header'><input type='checkbox' name='all'
                                       onclick="CheckAll(document.info.cb,document.info.deleted)">
        </td>
        <%
            }
        %>
    </tr>

    <% for (int j = 0; j < dataDetail.size(); j++) {
    %>
    <tr>
        <% for (int k = 0; k < dataDetail.get(j).size(); k++) {
            if (k == 0 && datas.getMenuStyle()[2]) {
        %>
        <td class='item'><a
                href="<%=request.getContextPath()%>/pages/menu/process?edit=<%=dataDetail.get(j).get(dataDetail.get(j).size()-1)%>"><%=dataDetail.get(j).get(k)%>
        </a>
        </td>
        <%
        } else if (k == dataDetail.get(j).size() - 1 && datas.getMenuStyle()[3]) {
        %>
        <td class='item'><input type='checkbox' name='cb'
                                onclick="CheckItem(document.info.cb,document.info.deleted)"
                                value='<%=dataDetail.get(j).get(k)%>'>
        </td>
        <%
        } else if (k != dataDetail.get(j).size() - 1) {
        %>
        <td class='item'><%=dataDetail.get(j).get(k)%>
        </td>
        <%
                    }
                }
            }
        %>
    </tr>
</table>
<%
    if (datas.getMenuStyle()[3]) {
%>
<input type="submit" value="Delete">
<%
    }
} else if (isOpened != null && isOpened.equals("yes")) {
%>
<br><b>No Data Exist according to the search criteria typed</b>
<%
    }
%>
</form>
<%@ include file="/includes/footer.jsp" %>
