package com.jcg.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(description = "Download File From The Server", urlPatterns = { "/downloadServlet" })
public class FileDownloadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static int BUFFER_SIZE = 1024 * 100;

    /***** This method is called by the servlet container to process a 'GET' request *****/
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("fileName"),
                downloadPath = "C:/studying/web/uploadedFiles",
                filePath = downloadPath + File.separator + fileName;

        File file = new File(filePath);
        OutputStream outStream = null;
        FileInputStream inputStream = null;

        if (file.exists()) {
            String mimeType = "application/octet-stream";
            response.setContentType(mimeType);

            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
            response.setHeader(headerKey, headerValue);
            try {
                outStream = response.getOutputStream();
                inputStream = new FileInputStream(file);
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
            } catch(IOException ioExObj) {
                System.out.println("Exception While Performing The I/O Operation?= " + ioExObj.getMessage());
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                outStream.flush();
                if (outStream != null) {
                    outStream.close();
                }
            }
        } else {
            response.setContentType("text/html");
            response.getWriter().println("<h3>File "+ fileName +" Is Not Present .....!</h3>");
        }
    }
}