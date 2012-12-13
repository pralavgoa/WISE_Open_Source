package edu.ucla.wise.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ucla.wise.commons.ConsentForm;
import edu.ucla.wise.commons.IRBSet;
import edu.ucla.wise.commons.Preface;
import edu.ucla.wise.commons.StudySpace;
import edu.ucla.wise.commons.SurveyorApplication;
import edu.ucla.wise.commons.User;
import edu.ucla.wise.commons.WISELogger;

/*
 Generate the consent form
 */

public class ConsentFormGenerator extends HttpServlet {
    static final long serialVersionUID = 1000;

    @Override
	public void service(HttpServletRequest req, HttpServletResponse res)
	    throws ServletException, IOException {
	// prepare for writing
	PrintWriter out;
	res.setContentType("text/html");
	out = res.getWriter();

	HttpSession session = req.getSession(true);

	// if session is new, then show the session expired info
	if (session.isNew()) {
	    res.sendRedirect(SurveyorApplication.shared_file_url + "error"
		    + SurveyorApplication.html_ext);
	    return;
	}

	// get the user object
	User theUser = (User) session.getAttribute("USER");
	StudySpace study_space = (StudySpace) session
		.getAttribute("STUDYSPACE");
	if (theUser == null || study_space == null || theUser.id == null) {
	    out.println("<p>Error: Can't find the user & study space info.</p>");
	    return;
	}

	// get the preface
	Preface pf = study_space.get_preface();
	if (pf != null) {
	    if (theUser.irb_id == null || theUser.currentSurvey.id == null) {
		out.println("<p>Error: the user's IRB/Survey ID should not be null</p>");
		return;
	    }

	    String apr_numb = "", exp_date = "";
	    // get the irb set from the list
	    IRBSet irb_set = pf.get_irb_set(theUser.irb_id);
	    if (irb_set != null) {
		if (!irb_set.irb_logo.equalsIgnoreCase("")) {
		}
		if (!irb_set.approval_number.equalsIgnoreCase(""))
		    apr_numb = irb_set.approval_number;
		if (!irb_set.expir_date.equalsIgnoreCase(""))
		    exp_date = irb_set.expir_date;
	    } else {
		out.println("<p>Can't find the IRB set in list with the user's IRB ID</p>");
		return;
	    }

	    // get the consent form
	    ConsentForm consent_f = pf.get_consent_form_survey_irb(
		    theUser.currentSurvey.id, theUser.irb_id);
	    if (consent_f == null) {
		out.println("<p>Error: can't find consent form with the specified IRB/Survey ID</p>");
		return;
	    }

	    // print out the consent form
	    String consent_html = "", consent_header = "", consent_notes = "";
	    String consent_header_html = "", consent_p = "", consent_ul = "", consent_s = "";
	    String title, sub_title, consent_title = "";

	    consent_header_html = consent_f.consent_header_html;
	    consent_p = consent_f.consent_p;
	    consent_s = consent_f.consent_s;
	    consent_ul = consent_f.consent_ul;
	    title = consent_f.title;
	    sub_title = consent_f.sub_title;

	    // compose the common header
	    consent_header += "<HTML><HEAD><TITLE>Cancer Screening Update: Consent Form</TITLE>";
	    consent_header += "<META http-equiv=Content-Type content='text/html; charset=iso-8859-1'>";
	    consent_header += "<SCRIPT> function FormSubmit(answerVal) { ";
	    consent_header += "document.form.answer.value = answerVal; document.form.submit(); } </SCRIPT>";
	    consent_header += "<LINK href='" + SurveyorApplication.shared_file_url
		    + "style.css' type=text/css rel=stylesheet>";
	    consent_header += "<META content='MSHTML 6.00.2800.1170' name=GENERATOR></HEAD>";
	    // let style.css take care of background color and text color
	    // consent_header += "<body text=#000000 bgColor=#ffffcc>";
	    consent_header += "<body>";
	    consent_header += "<table cellSpacing=3 cellPadding=9 width=100% align=left border=0>";

	    // compose the common part at bottom
	    consent_notes += "<tr><td align=middle>";
	    consent_notes += "<FORM name=form action='consent_record' method='post'>";
	    consent_notes += "<DIV align=center><INPUT type=hidden name=answer>";
	    consent_notes += "<A href=\"javascript:FormSubmit('yes')\"><IMG src='"
		    + "imageRender?img=accept.gif' border=0></A>";
	    consent_notes += "&nbsp;<A href=\"javascript:FormSubmit('no')\"><IMG src='"
		    + "imageRender?img=decline.gif' border=0></A></DIV>";
	    consent_notes += "</FORM></td></tr>";

	    // add the header part
	    consent_html += consent_header;

	    // compose the consent title
	    if (!title.equalsIgnoreCase(""))
		consent_title += "<B><FONT face='Arial, Helvetica, sans-serif' size=3>"
			+ title + "</FONT></B>";
	    if (!sub_title.equalsIgnoreCase(""))
		consent_title += "<B> &#8212; <BR><FONT face='Arial, Helvetica, sans-serif' size=2>"
			+ sub_title + "</FONT></B><BR>";

	    // add the custormerized html code if it exits
	    if (!consent_header_html.equalsIgnoreCase(""))
		consent_html += "<tr><td align=center>" + consent_header_html
			+ "</td></tr>";

	    if (!consent_title.equalsIgnoreCase(""))
		consent_html += "<tr><td align=center>" + consent_title
			+ "</td></tr>";

	    if (!consent_p.equalsIgnoreCase(""))
		consent_html += "<tr><td align=left>" + consent_p
			+ "</td></tr>";

	    if (!consent_ul.equalsIgnoreCase(""))
		consent_html += "<tr><td align=left>" + consent_ul
			+ "</td></tr>";

	    // add in the acceptance cell
	    consent_html += "<tr><td align=center>";
	    consent_html += "<TABLE cellSpacing=2 cellPadding=7 width=640 align=center border=1>";
	    consent_html += "<TR><TD align=center valign=top bgColor=#ffffff>";
	    consent_html += "<B><FONT face='Times New Roman, Times, serif' size=3>ACCEPTANCE</FONT></B>";
	    consent_html += "</TD></TR></TABLE>";
	    consent_html += "</td></tr>";

	    // add the bottom sign
	    consent_html += "<tr><td align=left>" + consent_s + "</td></tr>";

	    // add the bottom part to consent form
	    consent_html += consent_notes;
	    consent_html += "<tr><td><p align=left><font size=2><b>IRB Number: "
		    + apr_numb + "<br>";
	    consent_html += "Expiration Date: " + exp_date + "</b></font></p>";
	    consent_html += "</td></tr><tr><td>";
	    consent_html += "<DIV align=center><FONT face='Arial, Helvetica, sans-serif' size=1>";
	    consent_html += "<I>This survey system is powered by the Web-based Interactive Survey Environment (WISE) at UCLA. ";
	    consent_html += "<a href='mailto:merg@mednet.ucla.edu'>Click here</a> ";
	    consent_html += "to report questions or problems regarding the use of the system.</font></I></FONT></DIV>";
	    consent_html += "</td></tr>";

	    consent_html += "</table></body></html>";

	    // print out the html form
	    out.println(consent_html);
	} else {
	    WISELogger.logError(
		    "WISE - CONSENT GENERATE: can't find the preface object",
		    null);
	}
	return;
    }

}
