package com.jcg.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(description = "Upload File To The Server", urlPatterns = { "/fileUploadServlet" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, maxFileSize = 1024 * 1024 * 30, maxRequestSize = 1024 * 1024 * 50)
public class FileUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /***** This method is called by the servlet container to process a 'POST' request *****/
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String applicationPath = getServletContext().getRealPath(""),
                uploadPath = "C:/studying/web/uploadedFiles";

        /***** Check if the upload directory already exists and create it if not *****/
        File fileUploadDirectory = new File(uploadPath);
        if (!fileUploadDirectory.exists()) {
            fileUploadDirectory.mkdirs();
        }

        String fileName = "";
        UploadDetail details = null;
        List<UploadDetail> fileList = new ArrayList<UploadDetail>();

        /***** Write uploaded files *****/
        for (Part part :
                request.getParts()) {
            fileName = extractFileName(part);

            /***** Check if file isn't a graphic one *****/
            if (fileName.contains(".png") || fileName.contains(".gif") || fileName.contains(".jpg")) {
                details = new UploadDetail();
                details.setFileName(fileName);
                details.setFileSize(part.getSize() / 1024);
                try {
                    part.write(uploadPath + File.separator + fileName);
                    details.setUploadStatus("Success");
                } catch (IOException ioObj) {
                    details.setUploadStatus("Failure : " + ioObj.getMessage());
                }
                fileList.add(details);

                request.setAttribute("uploadedFiles", fileList);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/fileuploadResponse.jsp");
                dispatcher.forward(request, response);
            }
            else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/fileuploadError.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    /***** Read file names *****/
    private String extractFileName(Part part) {
        String fileName = "",
                contentDisposition = part.getHeader("content-disposition");
        String[] items = contentDisposition.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                fileName = item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return fileName;
    }
}