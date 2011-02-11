<%@page import="javax.management.AttributeList" %>
<%@page import="javax.management.Attribute" %>

<%String title="Service Configuration Created Success"; %>
<%@ include file="/includes/header.jsp" %>
 
 
<%AttributeList attList=(AttributeList)request.getAttribute("attributeList"); 
  String profileid=(String)request.getAttribute("profileid");
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
		  Service Configuration created successfully.
		</td>
	</tr>
	<tr>
		<td>
			<table class="item" style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;" width=450>
				<tr>
					<td class="item-header" colspan=2>
						Service Configuration Information
					</td>
				</tr>
                
				<tr>
					<td class="item" width=120>
						Package ID
					</td>
					<td class="item">
					<%=((Attribute)attList.get(1)).getValue()%>
					</td>
				</tr>
				<tr>
					<td class="item" width=120>
						Password
					</td>
					<td class="item">
					<%=((Attribute)attList.get(0)).getValue()%>
					</td>
				</tr>
				<tr>
					<td class="item" width=120>
						Rating Group
					</td>
					<td class="item">
				<%=((Attribute)attList.get(2)).getValue()%>
					</td>
				</tr>
				<tr>
					<td class="item">
						Content Description
					</td>
					<td class="item">
					<%=((Attribute)attList.get(3)).getValue()%>
					</td>
				</tr>
						
			</table>
		</td>
	</tr>		
</table>

 
 
<%@ include file="/includes/footer.jsp" %>