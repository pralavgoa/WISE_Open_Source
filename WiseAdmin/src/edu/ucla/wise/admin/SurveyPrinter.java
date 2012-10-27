package edu.ucla.wise.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ucla.wise.commons.AdminInfo;
import edu.ucla.wise.commons.WiseConstants;

public class SurveyPrinter extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest req, HttpServletResponse res)
	    throws ServletException, IOException {
	// prepare for writing
	PrintWriter out;
	res.setContentType("text/html");
	out = res.getWriter();
	HttpSession session = req.getSession(true);
	// check if the session is still valid
	String survey_id = req.getParameter("s");
	AdminInfo admin_info = (AdminInfo) session.getAttribute("ADMIN_INFO");
	if (admin_info == null || survey_id == null) // if session does not
						     // exists
	{
	    out.println("Wise Admin - Print Survey Error: Can't get the Admin Info");
	    return;
	}

	try {
	    // Changing the URL pattern
	    String new_url = admin_info.getStudyServerPath() + "/"
		    + WiseConstants.ADMIN_APP + "/" + "admin_print_survey?SID="
		    + admin_info.study_id + "&a=FIRSTPAGE&s=" + survey_id;
	    res.sendRedirect(new_url);

	} catch (Exception e) {
	    out.println("Wise Admin - Print Survey Error: " + e);
	}
	out.close();
    }

}
