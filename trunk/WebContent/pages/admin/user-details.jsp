<%@page import="web.User" %>
<%@page import="web.HttpConstants" %>
<%@page import="java.util.Set" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@ page import="java.util.HashMap" %>
<% User user = (User) request.getAttribute(HttpConstants.ATTR_NAME_USER);
   HashMap groupMap = (HashMap) request.getAttribute("groupMap");
%>
<%String title;
if(request.getAttribute("createdUser")==null&&request.getAttribute("registeredUser")==null) 
       title = (user == null ? "Create New User" : "Create New User");
else if(request.getAttribute("registeredUser")!=null)
       title=(String)request.getAttribute("registeredUser");
else
       title=(String)request.getAttribute("createdUser");

%>
<%@ include file="/includes/header.jsp" %>
<table>
   
    <tr>
        <td>
            
            <%if(request.getAttribute("registeredUser")!=null){%>
                <img src="<%=request.getContextPath()%>/images/icons/warn.gif">
                <b><font color="red"><%=title %></font></b>
            <%}else{%>
                <img src="<%=request.getContextPath()%>/images/icons/system-users.png"> 
                <b><%=title %></b>
            <%} %>  
        </td>
    </tr>
    <tr>
        <td>
            Please insert fields you want to Add then click button Submit
        </td>
    </tr>
    
    <%
            String error = (String) request.getAttribute(HttpConstants.HTTP_VAR_ERROR);
            if(error != null) {
        %>
        <tr>
            <td>
                <font color="red"><%=error %></font>
            </td>
        </tr>
        <%
            }
        %>
</table>
<%if(request.getAttribute("user")!=null){
    User userNew=(User)request.getAttribute("user");
    String pass=(String)request.getAttribute("password");
    String confpass=(String)request.getAttribute("confpassword");
    
    %>
<form action="createuser" method="post" name="info">
    <table class="item" style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;" width=450>
     <tr>
                    <td class="item-header" colspan=2>
                       User Information
                    </td>
                </tr>
     <%if(userNew.getUsername()==null){ %>
      <tr><td class="item">Username*</td><td class="item"><input type="text" name="username"  size="30" ></td><tr>
    <%}else{ %>
    <tr><td class="item">Username*</td><td class="item"> <input type="text" name="username" value="<%=userNew.getUsername()%>" size="30" ></td><tr>
    <%} %>
    <%if(pass==null){ %>
     <tr><td class="item">Password*</td><td class="item"> <input type="password" name="password" size="30"></td></tr>
   <%}else{ %>
    <tr><td class="item">Password*</td><td class="item"> <input type="password" name="password" value="<%=pass%>" size="30"></td></tr>
    <%} %>
    <%if(confpass==null){%>
     <tr><td class="item">Confirm Password*</td><td class="item"><input type="password" name="confPassword" size="30"></td></tr>
   <%}else{ %>
    <tr><td class="item">Confirm Password*</td><td class="item"><input type="password" name="confPassword" value="<%=confpass%>" size="30"></td></tr>
    <%} %>
    <%if(userNew.getFullName()==null){ %>
      <tr><td class="item" >Full Name</td><td class="item"> <input type="text" name="fullname" size="30" ></td></tr>
    <%}else{ %>
    <tr><td class="item">Full Name</td><td class="item"> <input type="text" name="fullname" value="<%=userNew.getFullName()%>" size="30" ></td></tr>
    <%} %>
    <%if(userNew.getNik()==null){ %>
      <tr><td class="item">NIK*</td><td class="item"><input type="text" name="nik" size="30" ></td></tr>
   <%}else{ %>
    <tr><td class="item">NIK*</td><td class="item"><input type="text" name="nik" value="<%=userNew.getNik()%>" size="30" ></td></tr>
    <%} %>
   
    <%if(userNew.getEmailAddress()==null){ %>
      <tr><td class="item">Email*</td><td class="item"><input type="text" name="email" size="30"></td></tr>
    <%}else{ %>
    <tr><td class="item">Email*</td><td class="item"><input type="text" name="email" value="<%=userNew.getEmailAddress()%>" size="30"></td></tr>
    <%} %>
    <%if(userNew.getDepartemen()==null){ %>
      <tr><td class="item">Department</td><td class="item"><input type="text" name="department"size="30" ></td></tr>
    <%}else{ %>
     <tr><td class="item">Department</td><td class="item"><input type="text" name="department" value="<%=userNew.getDepartemen()%>" size="30" ></td><tr>
     <%} %>
     <%if(userNew.getDivision()==null){ %>
       <tr><td class="item">Division</td><td class="item"><input type="text" name="division" size="30"></td></tr>
     <%}else{ %>
     <tr><td class="item">Division</td><td class="item"><input type="text" name="division" value="<%=userNew.getDivision()%>" size="30" ></td></tr>
      <%} %>
     <%if(userNew.getAddress()==null){ %> 
        <tr><td class="item">Address :</td><td class="item"><textarea name="address" cols="20" rows="8" ></textarea></td></tr>
    <%}else{ %>
     <tr><td class="item">Address</td><td class="item"><textarea name="address" cols="20" rows="8" ><%=userNew.getAddress()%></textarea></td></tr>
    <%} %>
       <tr><td class="item">User Group*</td><td class="item"><select name="group">
       
     <%String group;
      Iterator iter=groupMap.keySet().iterator();
      while(iter.hasNext()){ 
         group=(String)iter.next();
      %>
                                                                <option value="<%=group%>"><%=groupMap.get(group)%></option>
                                                           <%} %></select></td></tr>
    
    </table>

<input type="submit" value="Submit">
<input type="reset" value="Reset">
</form>
<%}else {
 
%>
 
 
 <form action="createuser" method="post" name="info">
    <table class="item" style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;" width=450>
                <tr>
                    <td class="item-header" colspan=2>
                        User Information
                    </td>
                </tr>
               
            <tr><td class="item">Username*</td><td class="item"><input type="text" name="username" size="30" ></td><tr>
    <tr><td class="item">Password*</td><td class="item"> <input type="password" name="password" size="30" ></td></tr>
    <tr><td class="item">Confirm Password*</td><td class="item"><input type="password" name="confPassword" size="30"></td></tr>
    <tr><td class="item">Full Name</td><td class="item"> <input type="text" name="fullname" size="30" ></td></tr>
    <tr><td class="item">NIK*</td><td class="item"><input type="text" name="nik" size="30" ></td></tr>
    <tr><td class="item">Email*</td><td class="item"><input type="text" name="email" size="30"></td></tr>
    <tr><td class="item">Department</td><td class="item"><input type="text" name="department"size="30" ></td></tr>
    <tr><td class="item">Division</td><td class="item"><input type="text" name="division" size="30" ></td></tr>
    <tr><td class="item">Address</td><td class="item"><textarea name="address"cols="20" rows="8" ></textarea></td></tr>
    <tr><td class="item">User Group*</td><td class="item"><select name="group">
     <%String group;
      Iterator iter=groupMap.keySet().iterator();
      while(iter.hasNext()){ 
         group=(String)iter.next();
      %> 
                                                                <option value="<%=group%>"><%=groupMap.get(group)%></option>
                                                           <%} %></select></td></tr>
    
</table>

<input type="submit" value="Submit">
<input type="reset" value="Reset">
</form>
 <%} %>
<br><br><br>(*)Mandatory Field
<%@ include file="/includes/footer.jsp" %>
