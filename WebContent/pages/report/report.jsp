<% String title = "Sample Report 1";%>
<%@ include file="/includes/header.jsp" %>
<a href="<%=request.getContextPath()%>/viewer?reportname=<%=request.getParameter("reportname")%>">Download <%=request.getParameter("reportlabel")%></a>
<br>
<br>

<!--"CONVERTED_APPLET"-->
<!-- HTML CONVERTER -->
<SCRIPT LANGUAGE="JavaScript"><!--
    var _info = navigator.userAgent; 
    var _ns = false; 
    var _ns6 = false;
    var _ie = (_info.indexOf("MSIE") > 0 && _info.indexOf("Win") > 0 && _info.indexOf("Windows 3.1") < 0);
//--></SCRIPT>
    <COMMENT>
        <SCRIPT LANGUAGE="JavaScript1.1"><!--
        var _ns = (navigator.appName.indexOf("Netscape") >= 0 && ((_info.indexOf("Win") > 0 && _info.indexOf("Win16") < 0 && java.lang.System.getProperty("os.version").indexOf("3.5") < 0) || (_info.indexOf("Sun") > 0) || (_info.indexOf("Linux") > 0) || (_info.indexOf("AIX") > 0) || (_info.indexOf("OS/2") > 0)));
        var _ns6 = ((_ns == true) && (_info.indexOf("Mozilla/5") >= 0));
//--></SCRIPT>
    </COMMENT>

<SCRIPT LANGUAGE="JavaScript"><!--
    if (_ie == true) document.writeln('<OBJECT classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93" WIDTH = "600" HEIGHT = "400"  codebase="http://java.sun.com/products/plugin/1.1.2/jinstall-112-win32.cab#Version=1,1,2,0"><NOEMBED><XMP>');
    else if (_ns == true && _ns6 == false) document.writeln('<EMBED type="application/x-java-applet;version=1.1.2" CODE = "EmbeddedViewerApplet.class" CODEBASE = "<%=request.getContextPath()%>/applet" ARCHIVE = "jasperreports-applet-4.0.0.jar,commons-logging-1.0.4.jar,commons-collections-2.1.1.jar" WIDTH = "900" HEIGHT = "533" REPORT_URL = "<%=request.getContextPath()%>/jasper?reportname=<%=request.getParameter("reportname")%>" scriptable=false pluginspage="http://java.sun.com/products/plugin/1.1.2/plugin-install.html"><NOEMBED><XMP>');
//--></SCRIPT>
<APPLET  CODE = "EmbeddedViewerApplet.class" CODEBASE = "<%=request.getContextPath()%>/applet" ARCHIVE = "jasperreports-applet-4.0.0.jar,commons-logging-1.0.4.jar,commons-collections-2.1.1.jar" WIDTH = "900" HEIGHT = "533"></XMP>
    <PARAM NAME = CODE VALUE = "EmbeddedViewerApplet.class" >
<PARAM NAME = CODEBASE VALUE = "<%=request.getContextPath()%>/applet" >
<PARAM NAME = ARCHIVE VALUE = "jasperreports-applet-4.0.0.jar,commons-logging-1.0.4.jar,commons-collections-2.1.1.jar" >

    <PARAM NAME="type" VALUE="application/x-java-applet;version=1.2.2">
    <PARAM NAME="scriptable" VALUE="false">
    <PARAM NAME = "REPORT_URL" VALUE ="<%=request.getContextPath()%>/jasper?reportname=<%=request.getParameter("reportname")%>">


</APPLET>
</NOEMBED>
</EMBED>
</OBJECT>

<!--
<APPLET CODE = "EmbeddedViewerApplet.class" CODEBASE = "<%=request.getContextPath()%>/applet" ARCHIVE = "jasperreports-applet-4.0.0.jar,commons-logging-1.0.4.jar,commons-collections-2.1.1.jar" WIDTH = "900" HEIGHT = "533">
<PARAM NAME = "REPORT_URL" VALUE ="<%=request.getContextPath()%>/jasper?reportname=<%=request.getParameter("reportname")%>">


</APPLET>
-->
<!--"END_CONVERTED_APPLET"-->
 
<%@ include file="/includes/footer.jsp" %>
