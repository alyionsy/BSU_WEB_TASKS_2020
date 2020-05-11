package by.ignot.web.email;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/sendEmail",})
public class EmailServlet extends HttpServlet{
    private static final Logger logger = LogManager.getLogger(EmailServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String toAddress = request.getParameter("toAddress");
        logger.info("Will be sent to: " + toAddress);

        String subject = request.getParameter("subject");
        logger.info("Got subject: \'" + subject + "\'");

        String message = request.getParameter("message");
        logger.info("Got message: \'" + message + "\'");

        EmailUtility.sendEmail(toAddress, subject, message);

        request.setAttribute("res", "If you see this, E-mail was sent successfully");

        request.getRequestDispatcher("pages/result.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
