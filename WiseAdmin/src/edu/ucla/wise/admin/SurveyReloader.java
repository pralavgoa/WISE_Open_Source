package edu.ucla.wise.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ucla.wise.commons.AdminInfo;
import edu.ucla.wise.commons.WISELogger;

/*
 Load a new survey and set up its Data tables. 
 (Called via URL request from load.jsp in the admin application)
 */

public class SurveyReloader extends HttpServlet {
    static final long serialVersionUID = 1000;

    @Override
	public void service(HttpServletRequest req, HttpServletResponse res)
	    throws ServletException, IOException {
	// prepare for writing
	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	HttpSession session = req.getSession(true);
	// check if the session is still valid
	AdminInfo admin_info = (AdminInfo) session.getAttribute("ADMIN_INFO");
	// if the session is invalid
	if (admin_info == null) {
	    out.println("Wise Admin - Reload Error: Can't get your Admin Info");
	    return;
	}
	String initErr = AdminInfo.force_init(req.getContextPath());
	out.println("<HTML><HEAD><TITLE>WISE Admin Reloader</TITLE>"
		+ "<LINK href='../file_product/style.css' type=text/css rel=stylesheet>"
		+ "<body text=#000000 bgColor=#ffffcc><center><table>");
	if (initErr != null) {
	    out.println("<tr><td>Sorry, the WISE Administration application failed to initialize. "
		    + "Please contact the system administrator with the following information."
		    + "<P>" + initErr + "</td></tr>");
			WISELogger.logError("WISE Admin Init Error: " + initErr,
		    null);// should write to file if no email
	} else
	    out.println("<tr><td align=center>WISE Admin Application Reload succeeded.</td></tr></table>");
	out.println("</table></center></body></html>");
    }

}
