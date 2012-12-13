package edu.ucla.wise.client.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucla.wise.commons.SurveyorApplication;
import edu.ucla.wise.commons.WISELogger;

/*
 Load a new survey and set up its Data tables. 
 (Called via URL request from load.jsp in the admin application)
 */

public class ReloadSurvey extends HttpServlet {
    static final long serialVersionUID = 1000;

    @Override
	public void service(HttpServletRequest req, HttpServletResponse res)
	    throws ServletException, IOException {
	// prepare for writing
	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	// Make sure local app is initialized
	String initErr = SurveyorApplication.force_init(req.getContextPath());
	if (initErr != null) {
	    out.println(initErr
		    + "<p> Servlet called: Application Reloader </p>"
		    + SurveyorApplication.initErrorHtmlFoot);
	    WISELogger.logError("WISE Surveyor Init Error: " + initErr,
		    null);// should write to file if no email
	} else {
	    out.println("<table border=0>");
	    out.println("<tr><td align=center>SURVEY Application Reload succeeded.</td></tr></table>");
	}
    }

}
