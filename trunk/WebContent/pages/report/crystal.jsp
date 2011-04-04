
<%@ page import= "com.crystaldecisions.report.web.viewer.*,
       com.crystaldecisions.sdk.occa.report.data.*" %>
<%@ page import="com.crystaldecisions.reports.reportengineinterface.JPEReportSourceFactory"%>
<%@ page import="com.crystaldecisions.sdk.occa.report.lib.ReportSDKException"%>
<%@ page import="com.crystaldecisions.sdk.occa.report.reportsource.IReportSource"%>
<%@ page import="com.crystaldecisions.sdk.occa.report.reportsource.IReportSourceFactory2"%>

<% String title = "Sample Report 1";%>
<%@ include file="/includes/header.jsp" %>
<%
    String report = "test.rpt";
    IReportSourceFactory2 rptSrcFactory = new JPEReportSourceFactory();
    IReportSource reportSource = (IReportSource) rptSrcFactory.createReportSource(report, request.getLocale());
    CrystalReportViewer viewer = new CrystalReportViewer();
    viewer.setReportSource(reportSource);
    viewer.setZoomFactor(45);
    viewer.refresh();
    viewer.processHttpRequest(request, response, getServletConfig().getServletContext(), out);
    //viewer.dispose();
%>

<%@ include file="/includes/footer.jsp" %>