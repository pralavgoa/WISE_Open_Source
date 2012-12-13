package edu.ucla.wise.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ucla.wise.commons.Preface;
import edu.ucla.wise.commons.StudySpace;
import edu.ucla.wise.commons.SurveyorApplication;
import edu.ucla.wise.commons.ThankyouPage;
import edu.ucla.wise.commons.WISELogger;

/*
 * Servlet to handle the "Thank You" page
 */

public class ThankYouGenerator extends HttpServlet {

    private static final long serialVersionUID = 1000;

    @Override
	public void service(HttpServletRequest req, HttpServletResponse res)
	    throws ServletException, IOException {

	// Prepare for writing
	PrintWriter out;
	res.setContentType("text/html");
	out = res.getWriter();

	HttpSession session = req.getSession(true);
	// if the session is new, then show the session expired info

	if (session.isNew()) {
	    res.sendRedirect(SurveyorApplication.shared_file_url + "error"
		    + SurveyorApplication.html_ext);
	    return;
	}

	// get the study space from the session
	StudySpace study_space = (StudySpace) session
		.getAttribute("STUDYSPACE");
	if (study_space == null) {
	    out.println("<p>Error: Can't find the study space.</p>");
	    return;
	}

	// get the preface file which contains thank you element
	String error = null;
	Preface pf = study_space.get_preface();

	if (pf != null) {

	    ThankyouPage thankyou_page = pf.get_thankyou_page();
	    if (thankyou_page == null) {
		error = "Error: Can't find a default Thank You Page in the Preface for current survey.";
		WISELogger.logError("WISE - THANKYOU_GENERATE " + error,
			null);
		out.println("<p>" + error + "</p>");
		return;
	    }

	    String title, banner, logo, page_contents;

	    title = thankyou_page.title;
	    banner = thankyou_page.banner;
	    logo = thankyou_page.logo;
	    page_contents = thankyou_page.page_contents;

	    // clear session
	    session.invalidate();
	    // print the Thank You page
	    StringBuffer thankyou_html = new StringBuffer("");
	    // compose the common header
	    thankyou_html.append("<HTML><HEAD><TITLE>" + title
		    + " - Thanks</TITLE>");
	    thankyou_html
		    .append("<META http-equiv=Content-Type content='text/html; charset=iso-8859-1'>");
	    thankyou_html.append("<LINK href='" + "styleRender?app="
		    + study_space.study_name + "&css=style.css"
		    + "' type=text/css rel=stylesheet>");
	    thankyou_html
		    .append("<META content='MSHTML 6.00.2800.1170' name=GENERATOR></HEAD>");
	    // compose the top part of the body
	    thankyou_html.append("<body><center>");
	    thankyou_html
		    .append("<table width=100% cellspacing=1 cellpadding=9 border=0>");
	    thankyou_html
		    .append("<tr><td width=98 align=center valign=top><img src='"
			    + "imageRender?app="
			    + study_space.study_name
			    + "&img=" + logo + "' border=0 align=middle></td>");
	    thankyou_html
		    .append("<td width=695 align=center valign=middle><img src='"
			    + "imageRender?app="
			    + study_space.study_name
			    + "&img="
			    + banner
			    + "' border=0 align=middle></td>");
	    thankyou_html
		    .append("<td rowspan=6 align=center width=280>&nbsp;</td></tr>");
	    thankyou_html.append("<tr><td width=98 rowspan=3>&nbsp;</td>");
	    thankyou_html.append("<td class=head>THANK YOU</td></tr>");
	    thankyou_html.append("<tr><td width=695 align=left colspan=1>");
	    // get the welcome contents
	    thankyou_html.append(page_contents);
	    thankyou_html.append("</td></tr>");

	    thankyou_html.append("</table></center></body></html>");
	    // print out the html form
	    out.println(thankyou_html);
	} else {
	    error = "Error: Can't get the preface";
	}
	if (error != null) {
	    WISELogger.logError("WISE - THANKYOU GENERATE: " + error,
		    null);
	    out.println("<p>" + error + "</p>");
	}
	out.close();
	return;
    }

}
