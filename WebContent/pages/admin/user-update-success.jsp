<%@page import="common.DateFormatter" %>
<%@page import="web.UserSession" %>
<%@page import="web.User" %>
<%@page import="web.HttpConstants" %>
<%@page import="java.util.Set" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<% String title = "User Update"; %>
<% 
    User user = (User)request.getAttribute(HttpConstants.ATTR_NAME_USER); 
%>
<%@ include file="/includes/header.jsp" %>
<form action="" method="post">

<%Map mapRole=new HashMap();  
  Map statusMap=new HashMap();
  statusMap.put("0","Active");
  statusMap.put("1","deactive");
  statusMap.put("2","locked");

%>	

<table>
	<tr>
		<td>
			<img src="<%=request.getContextPath()%>/images/icons/system-lock-screen.png">
			<b><%=title%></b>
		</td>
	</tr>
	<tr>
		<td>
			User updated successfully.
		</td>
	</tr>
	<tr>
		<td>
			<table class="item" style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;" width=450>
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
						<%=user.getUserId() %>
					</td>
				</tr>
				<tr>
					<td class="item" width=120>
						Username
					</td>
					<td class="item">
						<%=user.getUsername() %>
					</td>
				</tr>
				<tr>
					<td class="item" width=120>
						Full name
					</td>
					<td class="item">
						<%=user.getFullName() %>
					</td>
				</tr>
				<tr>
					<td class="item">
						NIK
					</td>
					<td class="item">
						<%=user.getNik() %>
					</td>
				</tr>
				<tr>
					<td class="item">
						E-mail 
					</td>
					<td class="item">
					    <%=user.getEmailAddress() %>
					</td>
				</tr>
                <tr>
                    <td class="item">
                        Cabang
                    </td>
                    <td class="item">
                        <%=user.getCabang() %>
                    </td>
                </tr>
                <tr>
                    <td class="item">
                        Division
                    </td>
                    <td class="item">
                        <%=user.getDivision()%>
                    </td>
                </tr>
                <tr>
                    <td class="item">
                        Address 
                    </td>
                    <td class="item">
                        <%=user.getAddress() %>
                    </td>
                </tr>                				

				<tr>
					<td class="item">
						User Role
					</td>
					<td class="item">
					    <%=user.getGroup()%>
					</td>
				</tr>
				<tr>
					<td class="item">
						Status
					</td>
					<td class="item">
						<%=statusMap.get(new Integer(user.getStatus()).toString())%>
					</td>
				</tr>
				
			</table>
		</td>
	</tr>		
</table>
</form>
<%@ include file="/includes/footer.jsp" %>
