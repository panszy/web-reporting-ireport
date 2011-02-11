<%@page import="javax.management.AttributeList" %>
<%@page import="javax.management.Attribute" %>

<%String title="Service Configuration Error"; %>
<%@ include file="/includes/header.jsp" %>
<table>
    <tr>
        <td>
            <img src="<%=request.getContextPath()%>/images/icons/process-stop.png">
            <b>Service Configuration Name Already Exist</b>
        </td>
    </tr>
    
   </table>

 
 
<%@ include file="/includes/footer.jsp" %>