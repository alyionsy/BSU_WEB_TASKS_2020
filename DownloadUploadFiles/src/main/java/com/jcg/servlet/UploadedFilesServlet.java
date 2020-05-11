package com.jcg.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(description = "List already uploaded files", urlPatterns = { "/uploadedFilesServlet" })
public class UploadedFilesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /***** This method is called by the servlet container to process a 'GET' request *****/
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String applicationPath = getServletContext().getRealPath(""),
                uploadPath = "C:/studying/web/uploadedFiles";

        File fileUploadDirectory = new File(uploadPath);

        UploadDetail details = null;
        File[] allFiles = fileUploadDirectory.listFiles();
        List<UploadDetail> fileList = new ArrayList<UploadDetail>();

        for (File file : allFiles) {
            details = new UploadDetail();
            details.setFileName(file.getName());
            details.setFileSize(file.length() / 1024);
            fileList.add(details);
        }

        request.setAttribute("uploadedFiles", fileList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/allfiles.jsp");
        dispatcher.forward(request, response);
    }
}