package edu.ucla.wise.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucla.wise.commons.StudySpace;
import edu.ucla.wise.commons.SurveyorApplication;
import edu.ucla.wise.commons.WISEApplication;

/*
 Print a study space, which should force a load
 */

public class StudySpacePrinter extends HttpServlet {
    static final long serialVersionUID = 1000;

    public void service(HttpServletRequest req, HttpServletResponse res)
	    throws ServletException, IOException {
	// prepare for writing
	PrintWriter out;
	res.setContentType("text/html");
	out = res.getWriter();
	String initErr = SurveyorApplication.check_init(req.getContextPath());
	if (initErr != null) {
	    out.println(initErr + "<p> Servlet called: Preface Loader </p>"
		    + SurveyorApplication.initErrorHtmlFoot);
	    WISEApplication.log_error("WISE Surveyor Init Error: " + initErr,
		    null);// should write to file if no email
	    return;
	}

	// get requested study space ID
	String spaceid = req.getParameter("ss");
	out.println("<p>Requested study space is: " + spaceid + "</p>\n");
	StudySpace theStudy = StudySpace.get_Space(spaceid);
	if (theStudy != null)
	    out.println(theStudy.toString());
	else
	    out.println("Retrieve of study failed.\n");
	out.println("<br>Done.");
	out.close();

    }

}
