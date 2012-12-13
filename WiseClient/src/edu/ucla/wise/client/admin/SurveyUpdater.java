package edu.ucla.wise.client.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucla.wise.commons.StudySpace;
import edu.ucla.wise.commons.SurveyorApplication;
import edu.ucla.wise.commons.WISEApplication;
import edu.ucla.wise.commons.WISELogger;

/*
 Update the local survey info 
 Called by the Admin tool's drop_survey.jsp page *assuming* no error
 SurveyStatus param to indicate change requested.
 */

public class SurveyUpdater extends HttpServlet {
    static final long serialVersionUID = 1000;

    @Override
	public void service(HttpServletRequest req, HttpServletResponse res)
	    throws ServletException, IOException {

	// prepare for writing
	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	// Make sure local app is initialized
	String initErr = SurveyorApplication.check_init(req.getContextPath());
	out.println("<p>WISE Surveyor Application "
		+ SurveyorApplication.ApplicationName + " on "
		+ WISEApplication.rootURL + ": ");
	if (initErr != null) {
	    out.println("<p> FAILED to initialize </p>");
	    WISELogger.logError(
		    "WISE Surveyor Init Error from survey_update servlet: "
			    + initErr, null);// should write to file if no email
	    return;
	}

	// get the survey ID, status and study ID
	// survey status:
	// D - delete submitted data from surveys in developing mode
	// R - remove the surveys in developing mode
	// P - clean up and archive the data of surveys in production mode
	String survey_id = req.getParameter("SurveyID");
	String survey_status = req.getParameter("SurveyStatus");
	String study_id = req.getParameter("SID");
	if (survey_id == null || study_id == null || survey_status == null) {
	    out.println("<tr><td align=center>SURVEY UPDATE ERROR: can't get the survey id/status or study id from URL</td></tr></table>");
	    return;
	}
	// get the study space
	StudySpace study_space = StudySpace.get_Space(study_id);
	if (study_space == null) {
	    out.println("<tr><td align=center>SURVEY UPDATE ERROR: can't find the requested study space</td></tr></table>");
	    return;
	}
	try {
	    if (survey_status.equalsIgnoreCase("R")
		    || survey_status.equalsIgnoreCase("P")) {
		study_space.drop_Survey(survey_id);
		out.println("Dropped survey " + survey_id);
	    } else
		out.println("Registered update of survey " + survey_id);
	} catch (Exception e) {
	    WISELogger.logError(
		    "WISE - DROP SURVEY DATA: " + e.toString(), null);
	    out.println("<tr><td align=center>Survey Update Error: "
		    + e.toString() + "</td></tr>");
	}

	return;
    }

}
