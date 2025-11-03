<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Download Reports</title>
</head>
<body>
    <h2>Download Reports as CSV</h2>

    <form action="DownloadReportServlet" method="get">
        <input type="hidden" name="reportType" value="report1">
        <button type="submit">Download Report 1</button>
    </form>

    <form action="DownloadReportServlet" method="get">
        <input type="hidden" name="reportType" value="report2">
        <button type="submit">Download Report 2</button>
    </form>
</body>
</html>