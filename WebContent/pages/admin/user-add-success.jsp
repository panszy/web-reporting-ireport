<%String title="Create User Success"; %>
<%@ include file="/includes/header.jsp" %>
<table>
  <%if(request.getAttribute("registeredUser")==null){ %>
    <tr>
    
        <td>
            <img src="<%=request.getContextPath()%>/images/icons/system-users.png"><b>Create User</b>  
                  
        </td>
     </tr>
     <tr>
        <td>
          User Has Been Created Successfully
         </td>
      </tr>
    <%}else{ %> 
    
       <tr>
    
        <td>
            <img src="<%=request.getContextPath()%>/images/icons/warn.gif"> <b>Create User </b> 
                  
        </td>
     </tr>
     <tr>
        <td>
           <font color="red">User is registered</font>
         </td>
      </tr>
   <%} %>
</table>
<%@ include file="/includes/footer.jsp" %>