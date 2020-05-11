<%@page import="java.util.List"%>
<%@page import="com.jcg.servlet.UploadDetail"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Servlet File Upload/Download</title>

    <link rel="stylesheet" href="resource/css/main.css" />
</head>
<body>
<div class="panel">
    <h1>File Upload Status</h1>
    <table class="bordered_table">
        <thead>
        <tr align="center"><th>File Name</th><th>File Size</th><th>Upload Status</th><th>Action</th></tr>
        </thead>
        <tbody>
        <% List<UploadDetail> uploadDetails = (List<UploadDetail>) request.getAttribute("uploadedFiles");
            for (UploadDetail uploadDetail : uploadDetails) {
        %>
        <tr>
            <img src="<%=request.getContextPath()%>/downloadServlet?fileName=<%=uploadDetail.getFileName() %>" style="width: 50%; height: 50%"/>
            <td align="center"><span id="fileName"><%=uploadDetail.getFileName() %></span></td>
            <td align="center"><span id="fileSize"><%=uploadDetail.getFileSize() %> KB</span></td>
            <td align="center"><span id="fileuploadStatus"><%=uploadDetail.getUploadStatus() %></span></td>
            <td align="center"><span id="fileDownload"><a id="downloadLink" class="hyperLink"
                                                          href="<%=request.getContextPath()%>/downloadServlet?fileName=<%=uploadDetail.getFileName() %>">Download</a></span>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <div class="margin_top_15px">
        <a id="fileUpload" class="hyperLink" href="<%=request.getContextPath()%>/fileupload.jsp">Back</a>
    </div>
</div>
</body>
</html>