package edu.ucla.wise.client.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucla.wise.commons.DataBank;
import edu.ucla.wise.commons.StudySpace;
import edu.ucla.wise.commons.Survey;
import edu.ucla.wise.commons.SurveyorApplication;
import edu.ucla.wise.commons.WISEApplication;

/*
 Load a new survey and set up its Data tables. 
 (Called via URL request from load.jsp in the admin application)
 */

public class survey_loader extends HttpServlet {
    static final long serialVersionUID = 1000;

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
	    WISEApplication.log_error("WISE Surveyor Init Error: " + initErr,
		    null);// should write to file if no email
	    return;
	}

	out.println("<table border=0>");

	// get session
	// HttpSession session = req.getSession(true);

	// get the survey name and study ID
	String survey_name = (String) req.getParameter("SurveyName");
	String study_id = (String) req.getParameter("SID");
	if (survey_name == null || study_id == null) {
	    out.println("<tr><td align=center>SURVEY LOADER ERROR: can't get the survey name or study id from URL</td></tr></table>");
	    return;
	}

	out.println("<tr><td align=center>SURVEY Name:" + survey_name
		+ " STUDY ID: " + study_id + "</td></tr>");

	// get the study space
	StudySpace study_space = StudySpace.get_Space(study_id);
	if (study_space == null) {
	    out.println("<tr><td align=center>SURVEY LOADER ERROR: can't create study space</td></tr></table>");
	    return;
	}

	// get the survey
	String surveyID = study_space.load_survey(survey_name);
	Survey survey = study_space.get_Survey(surveyID);

	DataBank db = new DataBank(study_space);

	// connect to the database
	try {
	    // connect to the database
	    Connection conn = study_space.getDBConnection();
	    Statement stmt = conn.createStatement();

	    // create data table - archive old data - copy old data
	    out.println("<tr><td align=center>Creating new data table.<td></tr>");
	    db.setup_survey(survey);

	    // delete old data
	    // out.println("<tr><td align=center>Deleting data from tables update_trail and page_submit.</td></tr>");
	    // db.delete_survey_data(survey);

	    // remove the interview records from table - interview_assignment
	    out.println("<tr><td align=center>Deleting data from tables of interview_assignment and interview_session.</td><tr>");
	    String sql = "DELETE FROM interview_assignment WHERE survey = '"
		    + surveyID + "'";
	    stmt.execute(sql);

	    out.println("</table>");
	    stmt.close();
	    conn.close();
	} catch (Exception e) {
	    WISEApplication.log_error("WISE - SURVEY LOADER: " + e.toString(),
		    null);
	    out.println("<tr><td align=center>survey loader Error: "
		    + e.toString() + "</td></tr>");
	}

	return;
    }

}
