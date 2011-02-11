<%@ page import="com.torepo.web.web.User" %>
<%@ page import="com.torepo.web.web.HttpConstants" %>
<%@ page import="com.torepo.web.util.MenuData" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<% User user = (User) request.getAttribute(HttpConstants.ATTR_NAME_USER);
    MenuData datas = (MenuData) request.getAttribute("menu");
    ArrayList<String> detailData = (ArrayList<String>) request.getAttribute("detail");
    SimpleDateFormat sdfBefore = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    SimpleDateFormat sdfAfter = new SimpleDateFormat("dd/MM/yyyyHHmmss");
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
    <td>Please modify fields of the <%=datas.getMainLabel()%> you want to update then click button Update</td>
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
    <input type="hidden" value="update" name="action"/>
    <input type="hidden" value="<%=detailData.get(datas.getFieldName().size())%>" name="fieldId"/>
    <tr>
        <td class='item-header' colspan="5"><%=datas.getMainLabel() + " Information"%>
        </td>
    </tr>
    <%  int counter = 0;
        for (int j = 0; j < datas.getFieldName().size(); j++) {
            String field = detailData.get(counter);
            counter++;
    %>
    <tr>
        <td class='item'><%=datas.getFieldName().get(j)%>
        </td>
        <%
            if (datas.getFieldType().get(j).equals("TIMESTAMP")) {
            String all = sdfAfter.format(sdfBefore.parse(field));
            String tanggal = all.substring(0,10);
            String jam = all.substring(10,12);
            String menit = all.substring(12,14);
            String detik = all.substring(14,16);
        %>
        <td class='item'><input readonly name="<%=datas.getFieldName().get(j)%>" id="datebefore<%=j%>" value="<%=tanggal%>"><img
                src="<%=request.getContextPath()%>/script/calbtn.gif" width="30" height="18" id="datebefore_trigger<%=j%>"
                style="cursor: pointer;" title="Date selector" align="middle"/>
            <%
                if(datas.getEditable().get(j).equals("on")){
            %>
            <script type="text/javascript">
                Calendar.setup(
                {
                    inputField  : "datebefore<%=j%>",
                    ifFormat    : "%d/%m/%Y",
                    button      : "datebefore_trigger<%=j%>"
                }
                        );
            </script>
            <%
                }
            %>

            <select <%=datas.getEditable().get(j).equals("off")?"readonly":""%>
                    size=1 name="<%=datas.getFieldName().get(j)+"_jam"%>">
                <%
                    for (int i = 0; i < 24; i++) {
                        String val = Integer.toString(i);
                        val = (val.length() == 1) ? "0" + val : val;
                %>
                <option <%=val.equals(jam)?"selected":""%> value="<%=val %>"><%=val %>
                </option>
                <%
                    }
                %>
                &lt;\SELECT&gt;
            </select>
            <select <%=datas.getEditable().get(j).equals("off")?"readonly":""%>
                    size=1 name="<%=datas.getFieldName().get(j)+"_menit"%>">
                <%
                    for (int i = 0; i < 60; i++) {
                        String val = Integer.toString(i);
                        val = (val.length() == 1) ? "0" + val : val;
                %>
                <option <%=val.equals(menit)?"selected":""%> value="<%=val %>"><%=val %>
                </option>
                <%
                    }
                %>
                &lt;\SELECT&gt;
            </select>
            <select  <%=datas.getEditable().get(j).equals("off")?"readonly":""%>
                    size=1 name="<%=datas.getFieldName().get(j)+"_detik"%>">
                <%
                    for (int i = 0; i < 60; i++) {
                        String val = Integer.toString(i);
                        val = (val.length() == 1) ? "0" + val : val;
                %>
                <option <%=val.equals(detik)?"selected":""%> value="<%=val %>"><%=val %>
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
        <td class='item' colspan="4"><input type='text' <%=datas.getEditable().get(j).equals("off")?"readonly":""%>
                                            value='<%=field%>'
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
<input type="submit" value="Update">
<input type="reset" value="Reset">
</form>
<%@ include file="/includes/footer.jsp" %>
