<%@page import="javax.management.AttributeList" %>
<%@page import="javax.management.Attribute" %>
<%String title="Update Service Configuration"; %>
<%@ include file="/includes/header.jsp" %>
<%AttributeList attList=(AttributeList)request.getAttribute("attributeList");
  String profileId=request.getParameter("appsid");
 %>
<form method="post" action="">
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
						 <td><input type="text" size=12 name="appsid" value="<%=((Attribute)attList.get(1)).getValue()%>">
					</td>
				</tr>
				
				<tr>
					<td class="item" width=120>
						Password
					</td>
					<td class="item">
						 <td><input type="text" size=12 name="password" value="<%=((Attribute)attList.get(0)).getValue()%>">
					</td>
				</tr>
				
				<tr>
					<td class="item" width=120>
						Rating Group
					</td>
					<td class="item">
						 <td><input type="text" size=12 name="rating" value="<%=((Attribute)attList.get(2)).getValue()%>">
					</td>
				</tr>
				
				<tr>
					<td class="item" width=120>
						Content Descriptor
					</td>
					<td class="item">
						 <td><input type="text" size=12 name="content" value="<%=((Attribute)attList.get(3)).getValue()%>">
						 	<input type="hidden" size=30 name="profileid" value="<%=profileId%>">
						 
					</td>
				</tr><tr><td>
				<input type="submit" value="Update">
				<input type="reset" value="Reset"></td></tr>
				
</table>
</form>				
			   
<%@ include file="/includes/footer.jsp" %>