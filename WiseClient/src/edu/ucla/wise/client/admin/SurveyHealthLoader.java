package edu.ucla.wise.client.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucla.wise.client.healthmon.SurveyHealth;
import edu.ucla.wise.commons.StudySpace;
import edu.ucla.wise.commons.SurveyorApplication;
import edu.ucla.wise.commons.WISELogger;

/*
 Load a new survey and set up its Data tables. 
 (Called via URL request from load.jsp in the admin application)
 */

public class SurveyHealthLoader extends HttpServlet {
    static final long serialVersionUID = 1000;

    @Override
	public void service(HttpServletRequest req, HttpServletResponse res)
	    throws ServletException, IOException {

	// prepare for writing
	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	// Make sure local app is initialized
	String initErr = SurveyorApplication.check_init(req.getContextPath());
	if (initErr != null) {
	    out.println(initErr + "<p> Servlet called: Survey Loader </p>"
		    + SurveyorApplication.initErrorHtmlFoot);
	    WISELogger.logError("WISE Surveyor Init Error: " + initErr,
		    null);// should write to file if no email
	    return;
	}

	// get session
	// HttpSession session = req.getSession(true);

	// get the survey name and study ID
	String survey_name = req.getParameter("SurveyName");
	String study_id = req.getParameter("SID");
	if (survey_name == null || study_id == null) {
	    out.println("<tr><td align=center>SURVEY LOADER ERROR: can't get the survey name or study id from URL</td></tr></table>");
	    return;
	}
	// get the study space
	StudySpace study_space = StudySpace.get_Space(study_id);
	if (study_space == null) {
	    out.println("<tr><td align=center>SURVEY LOADER ERROR: can't create study space</td></tr></table>");
	    return;
	}
	SurveyHealth.monitor(study_space);
	return;
    }

}