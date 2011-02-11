<%@page import="javax.management.Attribute"%>
<%@page import="javax.management.AttributeList"%>
<%@page import="java.util.List" %>
<%String title="Service Configuration"; %>

<%@ include file="/includes/header.jsp" %>


<form action="" method="post" name="frmSearch">
<table>
<%
int pages=request.getParameter("pages")==null?1:Integer.parseInt((String)request.getParameter("pages"));
int startnumber=request.getParameter("startnumber")==null?0:Integer.parseInt((String)request.getParameter("startnumber"));
int lastnumber=request.getParameter("lastnumber")==null?10:Integer.parseInt((String)request.getParameter("lastnumber"));
String appsidtext = request.getParameter("appsidtexts")==null?"":request.getParameter("appsidtexts");
String ratinggrouptext = request.getParameter("ratinggrouptexts")==null?"":request.getParameter("ratinggrouptexts");
String contenttext = request.getParameter("contenttexts")==null?"":request.getParameter("contenttexts");

%>

     
<tr><td><b>Service Configuration</b></td></tr>
<tr><td><input type="checkbox" name="appsid" <%if(!appsidtext.equals("")){ %> checked <%} %>>Package ID</td><td><input type="text" name="appsidtext" value="<%=appsidtext %>"></td></tr>
<tr><td><input type="checkbox" name="ratinggroup" <%if(!ratinggrouptext.equals("")){ %> checked <%} %>>Rating Group</td><td><input type="text" name="ratinggrouptext" value="<%=ratinggrouptext %>"></td></tr>
<tr><td><input type="checkbox" name="content" <%if(!contenttext.equals("")){ %> checked <%} %>>Content Descriptor</td><td><input type="text" name="contenttext" value="<%=contenttext %>"></td></tr>
<tr><td><input type="submit"   value="Search" name="Action"></td></tr>

</table>

<% 
int iTotalRecord=0;
int total_pages=0;
if(request.getAttribute("attribute")!=null){
    List listpar=(List)request.getAttribute("attribute");
    List list=(List)listpar.get(0);    
    iTotalRecord=(Integer)listpar.get(2);  
    total_pages=iTotalRecord/10;
    if((iTotalRecord % 10) > 0) total_pages++; 
    
 List listProf=(List)listpar.get(1);
 if(listpar!=null){
 %>
<table class="item" border=1 cellSpacing=0 cellPadding=3 style="background-image: url('<%=request.getContextPath()%>/images/item-header-space.jpg'); background-repeat: repeat-x;" width=800>
 <tr>
    <td class="item-header" colspan=5>
        Profile Table
    </td>
 </tr>
 <tr>
     <td class="item"><b>No</b></td>
     <td class="item"><b>Package ID</b></td>
     <td class="item"><b>Password</b></td>
     <td class="item"><b>Rating Group</b></td>
     <td class="item"><b>Content Descriptor</b></td>
     <td class="item"><b>Delete</b></td>
   </tr>  
   <%
    //Check maximum number of last page with maximum record
    if(lastnumber > iTotalRecord || iTotalRecord<10) { 
        lastnumber=iTotalRecord % 10;
    } else {
        lastnumber=10;
    }
   
    for(int i=0;i<lastnumber;i++){
        
       AttributeList attributeList=(AttributeList)list.get(i);
   %> 
   
   <tr> 
        <td class="item"><%=((pages-1)*10)+1+i%> </td>
        <td class="item"><a href="<%=request.getContextPath()%>/pages/config-cgw/config-update?appsid=<%=listProf.get(i)%>"><%=((Attribute)attributeList.get(1)).getValue()%></a></td>
        <td class="item"><%=((Attribute)attributeList.get(0)).getValue()%></td>
        <td class="item"><%=((Attribute)attributeList.get(2)).getValue()%></td>
        <td class="item"><%=((Attribute)attributeList.get(3)).getValue()%></td>
        <td class="item"><input onClick="uncheckcheckAll(document.frmSearch.deleted,10)" type="checkbox" value="<%=listProf.get(i)%>" name="deleted"></td>
    
    </tr>  
    <%} %>
    <tr>
    <td colspan=5 class="item">
        <button name="Action" value="Delete" onClick="deletes(10)">Delete</button>      
    </td>
    <td class="item">
        <input onClick="checkAll(document.frmSearch.deleted,10)" type="checkbox" value="" name="checkall"> All
    </td>
    </tr>
 </table>
 <%} 
  
 %>
 </form>
 <p align="right">
<%  if(pages>1){
    startnumber = (pages-2)*10;
    lastnumber = ((pages-2)*10)+10;
    %>
        <a href="<%=request.getContextPath()%>/pages/config-cgw/config?pages=<%=pages-1%>&startnumber=<%=startnumber%>&lastnumber=<%=lastnumber%>&appsidtexts=<%=appsidtext%>&ratinggrouptexts=<%=ratinggrouptext%>&contenttexts=<%=contenttext%>"><u>Prev</u></a>
<%
    }
%>
   Page <%=pages%> of <%=total_pages%>   
   
<% 
   if (pages<total_pages){
      startnumber = (pages*10);
      lastnumber = (pages*10)+10;
    %>   
        <a href="<%=request.getContextPath()%>/pages/config-cgw/config?pages=<%=pages+1%>&startnumber=<%=startnumber%>&lastnumber=<%=lastnumber%>&appsidtexts=<%=appsidtext%>&ratinggrouptexts=<%=ratinggrouptext%>&contenttexts=<%=contenttext%>"><u>Next</u></a>
<% 
    }

}
%>        
</p>
 
<%@ include file="/includes/footer.jsp" %>
