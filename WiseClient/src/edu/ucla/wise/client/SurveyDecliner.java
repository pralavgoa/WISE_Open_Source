package edu.ucla.wise.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ucla.wise.commons.StudySpace;
import edu.ucla.wise.commons.SurveyorApplication;
import edu.ucla.wise.commons.User;
import edu.ucla.wise.commons.WISELogger;
import edu.ucla.wise.commons.WiseConstants;
import edu.ucla.wise.shared.StringEncoderDecoder;

/*
 If user declined the invitation from the email link, then
 forward him to the decline reason page
 */

public class SurveyDecliner extends HttpServlet {
    static final long serialVersionUID = 1000;

    @Override
	public void service(HttpServletRequest req, HttpServletResponse res)
	    throws ServletException, IOException {
	// prepare for writing
	PrintWriter out;
	res.setContentType("text/html");
	out = res.getWriter();

	HttpSession session = req.getSession(true);
	SurveyorApplication s = (SurveyorApplication) session
		.getAttribute("SurveyorInst");

	// get the ecoded study space ID
	String spaceid_encode = req.getParameter("t");
	// get the email message ID
	String msgid_encode = req.getParameter("m");

	// if can't get sufficient information, then the email URL maybe broken
	// into lines
	if (msgid_encode == null || msgid_encode.equalsIgnoreCase("")
		|| spaceid_encode == null
		|| spaceid_encode.equalsIgnoreCase("")) {
	    res.sendRedirect(s.shared_file_url + "link_error"
		    + SurveyorApplication.html_ext);
	    return;
	}

	// decode the message ID & study space ID
		String spaceid = StringEncoderDecoder.decode(spaceid_encode);
		String msgid = StringEncoderDecoder.decode(msgid_encode);

	// initiate the study space ID and put it into the session
	StudySpace theStudy = StudySpace.get_Space(spaceid);
	User theUser = theStudy == null ? null : theStudy.get_User(msgid);

	// if the user can't be created, send error info
	if (theUser == null) {
	    out.println("<HTML><HEAD><TITLE>Begin Page</TITLE>");
	    out.println("<LINK href='" + s.shared_file_url
		    + "style.css' type=text/css rel=stylesheet>");
	    out.println("<body><center><table>");
	    // out.println("<body text=#000000 bgColor=#ffffcc><center><table>");
	    out.println("<tr><td>Error: Can't get the user information.</td></tr>");
	    out.println("</table></center></body></html>");
	    WISELogger.logError(
		    "WISE BEGIN - Error: Can't create the user.", null);
	    return;
	}

	// put the user into the session
	session.setAttribute("USER", theUser);

	// record this visit
	theUser.record_decline_hit(msgid, spaceid);

	// mark user as declining
	theUser.decline();

	// forward to ask for reason of declining
	String url = s.rootURL + "/WISE/" + WiseConstants.SURVEY_APP
		+ "/decline" + SurveyorApplication.html_ext;
	res.sendRedirect(url);
	out.close();
    }

}
