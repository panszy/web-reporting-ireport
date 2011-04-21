<%@  page import="common.DateFormatter"%>
<%@ page import="web.UserSession"%>
<%UserSession userSession = UserSession.Factory
					.getUserSession(request);
%>
<html>
<head>
<title><%=title%> - Kifar</title>
<script language="javascript">

        var timer;
    	var win;    	    	

    	function OpenPop_UpList(path)
    	{  
    	  win=window.open(path, "MyForm", "left=100,top=10,width=480,height=480,status=no,toolbar=no,menubar=no,location=no,scrollbars=yes");
    	  win.focus();
    	}
    	
        function ChangeValue(field, value) {
            field.value = value;
        }

        function CheckAll(field,fdelete) {
            fdelete.value = "";
            if (document.info.all.checked == true) {
                if(field.checked == false){
                    field.checked = true;
                    fdelete.value += field.value;
                    return;
                }
                for (var i = 0; i < field.length; i++) {
                    field[i].checked = true;
                    fdelete.value += field[i].value+",";
                }
                fdelete.value = fdelete.value.substring(0,fdelete.value.length-1);
            } else {
                if(field.checked == true){
                    field.checked = false;
                    fdelete.value = "";
                    return;
                }
                for (var i = 0; i < field.length; i++) {
                    field[i].checked = false;
                }
                fdelete.value = "";
            }
        }

        function CheckItem(field,fdelete) {
            var i = 0;
            var j = 0;
            fdelete.value = "";
            if(field.checked == true){
                fdelete.value += field.value+",";                
            } else {
                j++;
            }
            while (i < field.length) {
                if (field[i].checked == true){
                    fdelete.value += field[i].value+",";
                    j++;
                }
                i++;
            }
            fdelete.value = fdelete.value.substring(0,fdelete.value.length-1);
            if(i<=j)
                document.info.all.checked = true;
            else
                document.info.all.checked = false;
        }
                
        function onlyNumbers(evt)
        {
            var charCode = (evt.which) ? evt.which : event.keyCode
            if (charCode > 31 && (charCode < 48 || charCode > 57))
                return false;

            return true;
        }


        function addElement() {
            var ni = document.getElementById('my_div');
            var numi = document.getElementById('theValue');
            var num = (document.getElementById('theValue').value - 1) + 2;
            numi.value = num;
            var newdiv = document.createElement('div');
            var divIdName = 'my' + num + 'Div';
            newdiv.setAttribute('id', divIdName);
            newdiv.innerHTML = "<tr><td class='item' ><input type='text' maxlength='50' name='name'></td><td class='item' ><select name='type' <option value='VARCHAR'>String</option> <option value='INT'>Integer</option> <option value='TIMESTAMP'>Date Time</option></td><td class='item' ><input type='text' maxlength='4' onkeypress='return onlyNumbers(event)' name='size'></td><td class='item'><input type='checkbox' name='mandatory" + num + "'></td><td class='item'><input type='checkbox' name='editable" + num + "'></td><td class='item'><input type='checkbox' name='searchable" + num + "'></td><td class='item'><input type='button' value='Remove Field' onClick='removeElement(" + num + ")' name='button'></td></tr>";
            ni.appendChild(newdiv);
        }

        function addElementEdit() {
            var ni = document.getElementById('my_div');
            var numi = document.getElementById('theValue');
            var num = (document.getElementById('theValue').value - 1) + 2;
            numi.value = num;
            var newdiv = document.createElement('div');
            var divIdName = 'my' + num + 'Div';
            newdiv.setAttribute('id', divIdName);
            newdiv.innerHTML = "<tr><td class='item' ><b>NEW</b><input type='text' maxlength='50' name='name'></td><td class='item' ><select name='type' <option value='VARCHAR'>String</option> <option value='INT'>Integer</option> <option value='TIMESTAMP'>Date Time</option></td><td class='item' ><input type='text' maxlength='4' onkeypress='return onlyNumbers(event)' name='size'></td><td class='item'><input type='checkbox' name='mandatory" + num + "'></td><td class='item'><input type='checkbox' name='editable" + num + "'></td><td class='item'><input type='checkbox' name='searchable" + num + "'></td><td class='item'><input type='button' value='Remove Field' onClick='removeElement(" + num + ")' name='button'></td></tr>";
            ni.appendChild(newdiv);
        }

        function removeElement(divNum) {
            var d = document.getElementById('my_div');
            var olddiv = document.getElementById('my' + divNum + 'Div');
            d.removeChild(olddiv);
        }                

        function set_interval()
        {
        //the interval 'timer' is set as soon as the page loads
        timer=setInterval("auto_logout()",300000);
        // the figure '10000' above indicates how many milliseconds the timer be set to.
        //Eg: to set it to 5 mins, calculate 5min= 5x60=300 sec = 300,000 millisec. So set it to 3000000
        }
         
        function reset_interval()
        {
        //resets the timer. The timer is reset on each of the below events:
        // 1. mousemove   2. mouseclick   3. key press 4. scroliing
        //first step: clear the existing timer
        clearInterval(timer);
        //second step: implement the timer again
        timer=setInterval("auto_logout()",300000);        
        }
         
        function auto_logout()
        {
        //this function will redirect the user to the logout script        
        window.location.href ='<%=request.getContextPath()%>/logout';
        }

    </script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/style.css"></link>
</head>
<%if (!title.equals("Login") && !title.equals("Logout")) {

			%>
<body onmousemove="reset_interval()"
	onclick="reset_interval()" onkeypress="reset_interval()"
	onload="reset_interval()">
<%} else {

			%>
<body>
<%}

			%>
<table width="100%" class="header"
	style="background-image: url('<%=request.getContextPath()%>/images/header-space.jpg'); background-repeat: repeat-x;">
	<tr>
		<td width="266" class="header" rowspan=2><img
			src="<%=request.getContextPath()%>/images/header.jpg"></td>
		<td height="49" class="header"
			style="text-align: right; padding-top: 26px; padding-right: 12px;"><%=DateFormatter.getDisplayDate(new java.util.Date())%>
		</td>
	</tr>
	<tr>
		<td class="menu"
			style="text-align: right; padding-bottom: 31px; padding-right:12px;">
		<%if (userSession.isLoggedIn()) {

				%> <b><%=userSession.getUser().getFullName()%> </b> &#149; <a
			class="menu" href="<%=request.getContextPath()%>/pages/user-panel">User
		Panel</a> &#149; <a class="menu"
			href="<%=request.getContextPath()%>/logout">Logout</a> <%}

		%></td>
	</tr>
</table>
<table class="container">
	<tr>
		<td class="container" width="250"><%@ include
			file="/includes/menu-panel.jsp"%></td>
		<td class="container">