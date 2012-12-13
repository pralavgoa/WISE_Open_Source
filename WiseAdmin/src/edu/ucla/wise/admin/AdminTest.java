package edu.ucla.wise.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ucla.wise.commons.AdminInfo;
import edu.ucla.wise.commons.WISEApplication;
import edu.ucla.wise.commons.WISELogger;

/*
 Direct the user coming from email URL or interviewers to appropriate next step or page
 */

public class AdminTest extends HttpServlet {
	static final long serialVersionUID = 1000;

	@Override
	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		// prepare for writing
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		// Initialize AdminInfo class (application)
		String initErr = AdminInfo.check_init(req.getContextPath());
		if (initErr != null) {
			out.println("<HTML><HEAD><TITLE>WISE survey system -- Startup error</TITLE>"
					+ "<LINK href='../file_product/style.css' type=text/css rel=stylesheet>"
					+ "<body text=#000000 bgColor=#ffffcc><center><table>"
					+ "<tr><td>Sorry, the WISE Surveyor application failed to initialize. "
					+ "Please contact the system administrator with the following information."
					+ "<P>"
					+ initErr
					+ "</td></tr>"
					+ "</table></center></body></html>");
			WISELogger.logInfo("WISE Surveyor Init Error: " + initErr);// should
																		// write
																		// to
																		// file
																		// if no
																		// email
			return;
		}

		HttpSession session = req.getSession(true);
		session.getServletContext();


		String fromstr = "";
		try {
			// Define message
			MimeMessage message = new MimeMessage(AdminInfo.mail_session);
			if (req.getParameter("froma") != null)
				fromstr += "<" + req.getParameter("froma") + ">";
			else
				fromstr += "<merg@mednet.ucla.edu>";
			if (req.getParameter("from") != null)
				fromstr = req.getParameter("from") + fromstr;
			InternetAddress ia = new InternetAddress(fromstr);
			message.setFrom(ia);
			if (req.getParameter("repa") != null) {
				fromstr = "<" + req.getParameter("repa") + ">";
				InternetAddress ib = new InternetAddress(fromstr);
				message.setReplyTo(new InternetAddress[] { ib });
			}
			if (req.getParameter("senda") != null) {
				fromstr = "<" + req.getParameter("senda") + ">";
				InternetAddress ib = new InternetAddress(fromstr);
				message.setSender(ib);
			}

			message.addRecipient(javax.mail.Message.RecipientType.TO,
					new InternetAddress("<merg@mednet.ucla.edu>"));
			message.setSubject("This is a test");
			message.setText("this is a test body");

			// Send message
			Transport.send(message);
		} catch (Exception e) {
			String initError = e.toString();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			initError += sw.toString(); // get the new string out of the
			// StringWriter
			WISELogger.logError("Error in begin_test:" + initError, e);
		}

		out.println("<HTML><HEAD><TITLE>Begin Page</TITLE>"
				+ "<LINK href='../file_product/style.css' type=text/css rel=stylesheet>"
				+ "<body text=#000000 bgColor=#ffffcc><center><table>"
				// + "<tr><td>Successful test. StudySpace id [t]= " +
				// id2 + "</td></tr>"
				+ "<tr><td>Root URL= "
				+ AdminInfo.rootURL
				+ "</td></tr>"
				+ "<tr><td>XML path = "
				+ AdminInfo.xml_loc
				+ "</td></tr>"
				// + "<tr><td>SS file path = " + thesharedFile +
				// "</td></tr>"
				+ "<tr><td>Image path = "
				+ AdminInfo.image_root_path
				+ "</td></tr>"
				+ "<tr><td>DB backup path = "
				+ AdminInfo.db_backup_path
				+ "</td></tr>"
				+ "<tr><td>Context Path= "
				+ AdminInfo.ApplicationName
				+ "</td></tr>"
				+ "<tr><td>Servlet Path= "
				+ AdminInfo.servlet_url
				+ "</td></tr>"
				// + "<tr><td>message id= " + msgid_encode +
				// "</td></tr>"
				+ "<tr><td>Default email_from= "
				+ WISEApplication.email_from
				+ "</td></tr>"
				+ "<tr><td>constructed fromstr= "
				+ fromstr
				+ "</td></tr>" + "</table></center></body></html>");
	}

}
