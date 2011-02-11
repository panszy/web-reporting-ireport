<%@page import="com.torepo.web.util.DateFormatter" %>
<%@page import="com.torepo.web.web.UserSession" %>
<%@page import="com.torepo.web.web.User" %>
<%@page import="com.torepo.web.web.HttpConstants" %>
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
  mapRole.put("0","Administrator");
  mapRole.put("1","Rating Entry Configuration");
  mapRole.put("2","Threshold Configuration");
  mapRole.put("3","Monitoring");
  mapRole.put("4","Super User");
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
                        Department
                    </td>
                    <td class="item">
                        <%=user.getDepartemen() %>
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
                
				<%Set setRole=user.getRoles();
				 Iterator iter=setRole.iterator();
				
				int koma=0;
				StringBuffer buffer=new StringBuffer();
				while(iter.hasNext())
				{   if(koma>0)
					   buffer.append(", ");
					int val=(Integer)iter.next();
				   	buffer.append(mapRole.get(new Integer(val).toString()));
				    koma++;
				}%>

				<tr>
					<td class="item">
						User Role
					</td>
					<td class="item">
					    <%=buffer.toString()%>
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
