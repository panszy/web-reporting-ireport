<%@ page import="web.HttpConstants" %>
<%String title="Create New Service Configuration"; %>
<%@ include file="/includes/header.jsp"%>
<form action="createconfig" method="post" name="config" >
<img src="<%=request.getContextPath()%>/images/icons/system-users.png"><b>Create Service Configuration</b>
<br>
<%if(request.getAttribute(HttpConstants.HTTP_VAR_ERROR)!=null ){
  String error=(String)request.getAttribute(HttpConstants.HTTP_VAR_ERROR);
%>
    <font color="red"><%=error%></font>
<%} %>    
  
<table>
    <tr><td>Package ID*</td><td><input type="text" name="appsid"></td><tr>
    <tr><td>Password*</td><td> <input type="password" name="password"></td></tr>
    <tr><td>Confirm Password*</td><td><input type="password" name="confpassword"></td></tr>
    <tr><td>Rating Group*</td><td> <input type="text" name="rating" value="80"></td></tr>
    <tr><td>Content Descriptor*</td><td><input type="text" name="content"></td></tr>
   </table>

<input type="submit" value="Submit">
<input type="reset" value="Reset">
</form>
(*)Mandatory Field


<%@ include file="/includes/footer.jsp"%>