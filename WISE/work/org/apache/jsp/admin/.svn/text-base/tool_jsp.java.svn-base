package org.apache.jsp.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.apache.catalina.authenticator.Constants;
import edu.ucla.wise.commons.*;
import java.sql.*;
import java.util.Date;
import java.util.*;
import java.net.*;
import java.io.*;
import org.xml.sax.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.lang.*;
import java.text.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import com.oreilly.servlet.MultipartRequest;

public final class tool_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=windows-1252");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\"\r\n");
      out.write("\tcontent=\"text/html; charset=windows-1252\">\r\n");

	//get the server path
	String path = request.getContextPath();
	path = path + "/";
	AdminInfo admin_info;
	Date today1 = new Date();
	DateFormat f = new SimpleDateFormat("E");
	String wkday = f.format(today1);

      out.write("\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write("\tvar sid, jid, jstatus;\r\n");
      out.write("\t//sid - survey's internal ID\r\n");
      out.write("\t//jid - survey ID\r\n");
      out.write("\t//jstatus - D: clean up the survey data (developing mode)\r\n");
      out.write("\t//          R: remove the survey data table (developing mode)\r\n");
      out.write("\t//          P: archive the survey data (production mode)\r\n");
      out.write("\r\n");
      out.write("\t//manipulate the survey data according to the situation selected (jstatus)\r\n");
      out.write("\tfunction remove_confirm() {\r\n");
      out.write("\t\tvar msg;\r\n");
      out.write("\t\tif (jstatus.toUpperCase() == \"R\") {\r\n");
      out.write("\t\t\tmsg = \"\\nThis operation will remove the survey and permanently delete all data collected.\"\r\n");
      out.write("\t\t\t\t\t+ \"\\n(Note this operation is not available for surveys in Production mode.) \\nAre you sure you want to continue?\\n\";\r\n");
      out.write("\t\t} else if (jstatus.toUpperCase() == \"P\") {\r\n");
      out.write("\t\t\tmsg = \"\\nThis operation will remove the survey from the available list and will archive any data collected.\\n\"\r\n");
      out.write("\t\t\t\t\t+ \"Are you sure you want to continue?\\n\";\r\n");
      out.write("\t\t} else {\r\n");
      out.write("\t\t\tmsg = \"\\nThis operation will clear all submitted data and associated tracking data for this survey.\"\r\n");
      out.write("\t\t\t\t\t+ \"\\n(Note this operation is not available for surveys in Production mode.)\\nAre you sure you want to continue?\\n\";\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tvar url = \"drop_survey.jsp?s=\" + jid + \"&t=\" + jstatus;\r\n");
      out.write("\t\tif (confirm(msg))\r\n");
      out.write("\t\t\tlocation.replace(url);\r\n");
      out.write("\t\telse\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t//change the survey mode from developing to production\r\n");
      out.write("\tfunction change_mode() {\r\n");
      out.write("\t\tvar msg = \"\\nYou are about to change the survey mode from development to production.\\n\"\r\n");
      out.write("\t\t\t\t+ \"Are you sure to continue this operation?\\n\";\r\n");
      out.write("\t\tvar url = \"dev2prod?s=\" + sid;\r\n");
      out.write("\t\tif (confirm(msg))\r\n");
      out.write("\t\t\tlocation.replace(url);\r\n");
      out.write("\t\telse\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(path);
      out.write("/style.css\" type=\"text/css\">\r\n");
      out.write("<title>WISE Administration Tools</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body text=\"#333333\" bgcolor=\"#FFFFCC\">\r\n");

	try {
		session = request.getSession(true);
		//if the session is expired, go back to the logon page
		if (session.isNew()) {
			response.sendRedirect(path + WiseConstants.ADMIN_APP
					+ "/index.html");
			return;
		}
		//get the admin info object from session
		admin_info = (AdminInfo) session.getAttribute("ADMIN_INFO");
		if (admin_info == null) {
			response.sendRedirect(path + WiseConstants.ADMIN_APP
					+ "/error.htm");
			return;
		}
		//get the weekday format of today to name the data backup file
	} catch (Exception e) {
		WISE_Application.email_alert("WISE ADMIN - TOOL init: "
				+ e.toString());
		return;
	}

      out.write("\r\n");
      out.write("<center>\r\n");
      out.write("<table cellpadding=2 cellspacing=0 border=0>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td width=\"160\" align=center><img src=\"admin_images/somlogo.gif\"\r\n");
      out.write("\t\t\tborder=\"0\"></td>\r\n");
      out.write("\t\t<td width=\"400\" align=\"center\"><img src=\"admin_images/title.jpg\"\r\n");
      out.write("\t\t\tborder=\"0\"><br>\r\n");
      out.write("\t\t<br>\r\n");
      out.write("\t\t<font color=\"#CC6666\" face=\"Times New Roman\" size=\"4\"><b>");
      out.print(admin_info.study_title);
      out.write("</b></font>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t\t<td width=\"160\" align=center><a\r\n");
      out.write("\t\t\thref=\"");
      out.print(path + WiseConstants.ADMIN_APP);
      out.write("/logout\"><img\r\n");
      out.write("\t\t\tsrc=\"admin_images/logout_b.gif\" border=\"0\"></a></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("</center>\r\n");
      out.write("<p>\r\n");
      out.write("<p>\r\n");
      out.write("<center>\r\n");
      out.write("<table border=0>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td align=left valign=middle>\r\n");
      out.write("\t\t<table class=tth border=1 cellpadding=\"2\" cellspacing=\"0\"\r\n");
      out.write("\t\t\tbgcolor=#FFFFF5>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td height=30 width=400 bgcolor=\"#FF9900\" align=center><font\r\n");
      out.write("\t\t\t\t\tcolor=white><b>File Upload</b></font></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td align=\"center\" valign=\"middle\" width=\"400\">\r\n");
      out.write("\t\t\t\t<FORM action=\"load_data\" method=\"post\" encType=\"multipart/form-data\">Select\r\n");
      out.write("\t\t\t\ta survey(xml), message(xml), preface(xml), invitee(csv), consent\r\n");
      out.write("\t\t\t\tform(xml), style sheet(css) or image(jpg/gif) to upload:<br>\r\n");
      out.write("\t\t\t\t&nbsp;<br>\r\n");
      out.write("\t\t\t\t<INPUT type=file name=file> &nbsp; <input type=\"image\"\r\n");
      out.write("\t\t\t\t\talt=\"submit\" src=\"admin_images/upload.gif\"></FORM>\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t\t<td align=right valign=middle>\r\n");
      out.write("\t\t<table class=tth border=1 cellpadding=\"2\" cellspacing=\"2\"\r\n");
      out.write("\t\t\tbgcolor=#FFFFF5>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td height=30 width=250 bgcolor=\"#339999\" align=center><font\r\n");
      out.write("\t\t\t\t\tcolor=white><b>All-Survey Functions</b></font></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td align=left width=250><font size=-1> <a\r\n");
      out.write("\t\t\t\t\thref=\"load_invitee.jsp\">Manage</a> invitees<br>\r\n");
      out.write("\t\t\t\t&nbsp;<br>\r\n");
      out.write("\t\t\t\t<a href=\"list_interviewer.jsp\">Manage</a> interviewers<br>\r\n");
      out.write("\t\t\t\t&nbsp;<br>\r\n");
      out.write("\t\t\t\t<a href=\"download_file.jsp?fileName=preface.xml\">Download</a>\r\n");
      out.write("\t\t\t\tpreface file<br>\r\n");
      out.write("\t\t\t\t&nbsp;<br>\r\n");
      out.write("\t\t\t\t<a href=\"download_file.jsp?fileName=style.css\">Download</a> online\r\n");
      out.write("\t\t\t\tstyle sheet<br>\r\n");
      out.write("\t\t\t\t&nbsp;<br>\r\n");
      out.write("\t\t\t\t<a href=\"download_file.jsp?fileName=print.css\">Download</a> printing\r\n");
      out.write("\t\t\t\tstyle sheet<br>\r\n");
      out.write("\t\t\t\t&nbsp;<br>\r\n");
      out.write("\t\t\t\t<a\r\n");
      out.write("\t\t\t\t\thref=\"download_file.jsp?fileName=");
      out.print(admin_info.study_name);
      out.write('_');
      out.print(wkday);
      out.write(".sql\">Download</a>\r\n");
      out.write("\t\t\t\tMySQL dump file</font></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("</center>\r\n");
      out.write("<p>\r\n");

	try {
		//connect to the database
		Connection conn = admin_info.getDBConnection();
		Statement stmt = conn.createStatement();
		Statement stmt2 = conn.createStatement();

		String id, internal_id, filename, status, title, uploaded;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<center>\r\n");
      out.write("<table class=tth border=1 cellpadding=\"2\" cellspacing=\"0\"\r\n");
      out.write("\tbgcolor=#FFFFF5>\r\n");
      out.write("\t<tr bgcolor=#CC6666>\r\n");
      out.write("\t\t<th align=center colspan=4><font color=white>CURRENT\r\n");
      out.write("\t\tACTIVE SURVEYS</font></th>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<th class=sfon>Survey ID, <b>Title</b>, Uploaded Date & (<i>Status</i>)</th>\r\n");
      out.write("\t\t<th class=sfon>User State</th>\r\n");
      out.write("\t\t<th class=sfon>User Counts</th>\r\n");
      out.write("\t\t<th class=sfon width=40%>Actions</th>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t");

		//get the survey information from the database
			String sql2 = "select internal_id, id, filename, title, status, uploaded from surveys where status in ('P', 'D') and internal_id in"
					+ "(select max(internal_id) from surveys group by id) order by uploaded DESC";
			boolean dbtype = stmt2.execute(sql2);
			ResultSet rs2 = stmt2.getResultSet();
			while (rs2.next()) {
				internal_id = rs2.getString(1);
				id = rs2.getString(2);
				filename = rs2.getString(3);
				title = rs2.getString(4);
				status = rs2.getString(5);
				uploaded = rs2.getString(6);
				//assign the survey mode
				String status_exp = new String();
				if (status.equalsIgnoreCase("D"))
					status_exp = "Development";
				if (status.equalsIgnoreCase("P"))
					status_exp = "Production";
	
      out.write("\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td align=\"center\">");
      out.print(id);
      out.write("<br>\r\n");
      out.write("\t\t<br>\r\n");
      out.write("\t\t<b>");
      out.print(title);
      out.write("</b><br>\r\n");
      out.write("\t\t<br>");
      out.print(uploaded);
      out.write("<br>\r\n");
      out.write("\t\t<br>\r\n");
      out.write("\t\t(<i>");
      out.print(status_exp);
      out.write(" Mode</i>)</td>\r\n");
      out.write("\t\t<td align=\"center\" colspan=2>");
      out.print(admin_info.get_user_counts_in_states(id));
      out.write("\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t\t<td align=\"center\">\r\n");
      out.write("\t\t<table width=100% border=0 cellpadding=2>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td width=7>&nbsp;</td>\r\n");
      out.write("\t\t\t\t<td width=200><font size='-1'><a\r\n");
      out.write("\t\t\t\t\thref=\"initial_invite.jsp?s=");
      out.print(id);
      out.write("\">Send Initial Invitation</a></font></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td width=7>&nbsp;</td>\r\n");
      out.write("\t\t\t\t<td width=200><font size='-1'><a\r\n");
      out.write("\t\t\t\t\thref=\"other_invite.jsp?s=");
      out.print(id);
      out.write("\">Send Other Messages</a></font></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td width=7>&nbsp;</td>\r\n");
      out.write("\t\t\t\t<td width=200><font size='-1'><a\r\n");
      out.write("\t\t\t\t\thref=\"");
      out.print(path + WiseConstants.ADMIN_APP);
      out.write("/view_survey?s=");
      out.print(id);
      out.write("\">View\r\n");
      out.write("\t\t\t\tSurvey</a></font></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td width=7>&nbsp;</td>\r\n");
      out.write("\t\t\t\t<td width=200><font size='-1'><a\r\n");
      out.write("\t\t\t\t\thref=\"view_result.jsp?s=");
      out.print(id);
      out.write("\">View Results</a></font></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td width=7>&nbsp;</td>\r\n");
      out.write("\t\t\t\t<td width=200><font size='-1'><a\r\n");
      out.write("\t\t\t\t\thref=\"");
      out.print(path + WiseConstants.ADMIN_APP);
      out.write("/print_survey?a=FIRSTPAGE&s=");
      out.print(id);
      out.write("\">Print\r\n");
      out.write("\t\t\t\tSurvey</a></font></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td width=7>&nbsp;</td>\r\n");
      out.write("\t\t\t\t<td width=200><font size='-1'><a\r\n");
      out.write("\t\t\t\t\thref=\"download_file.jsp?fileName=");
      out.print(filename);
      out.write("\">Download\r\n");
      out.write("\t\t\t\tCurrent Survey File </a></font></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td width=7>&nbsp;</td>\r\n");
      out.write("\t\t\t\t<td width=200><font size='-1'><a\r\n");
      out.write("\t\t\t\t\thref=\"");
      out.print(path + WiseConstants.ADMIN_APP);
      out.write("/download_file?fileName=");
      out.print(id);
      out.write("_data.csv\">Download\r\n");
      out.write("\t\t\t\tSurvey Data (CSV)</a></font></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t");

				//if the survey is in the developing mode
						if (status.equalsIgnoreCase("D")) {
			
      out.write("\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td width=7>&nbsp;</td>\r\n");
      out.write("\t\t\t\t<td width=200><font size='-1'><a\r\n");
      out.write("\t\t\t\t\thref='javascript: jid=\"");
      out.print(id);
      out.write("\"; jstatus=\"");
      out.print(status);
      out.write("\"; remove_confirm();'>Clear\r\n");
      out.write("\t\t\t\tSurvey Data</a></font></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td width=7>&nbsp;</td>\r\n");
      out.write("\t\t\t\t<td width=200><font size='-1'><a\r\n");
      out.write("\t\t\t\t\thref='javascript: jid=\"");
      out.print(id);
      out.write("\"; jstatus=\"R\"; remove_confirm();'>Delete\r\n");
      out.write("\t\t\t\tSurvey</a></font></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td width=7>&nbsp;</td>\r\n");
      out.write("\t\t\t\t<td width=200><font size='-1'><a\r\n");
      out.write("\t\t\t\t\thref='javascript: sid=\"");
      out.print(internal_id);
      out.write("\"; change_mode();'>Change\r\n");
      out.write("\t\t\t\tto Production Mode</a></font></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t");

				}
						//if the survey is in the production mode
						if (status.equalsIgnoreCase("P")) {
			
      out.write("\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td width=7>&nbsp;</td>\r\n");
      out.write("\t\t\t\t<td width=200><font size='-1'><a\r\n");
      out.write("\t\t\t\t\thref=\"assign_wati.jsp?s=");
      out.print(id);
      out.write("\">Assign Interviewers</a></font></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td width=7>&nbsp;</td>\r\n");
      out.write("\t\t\t\t<td width=200><font size='-1'><a\r\n");
      out.write("\t\t\t\t\thref='javascript: jid=\"");
      out.print(id);
      out.write("\"; jstatus=\"");
      out.print(status);
      out.write("\"; remove_confirm();'>Close\r\n");
      out.write("\t\t\t\t& Archive Survey</a></font></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t");

				}
			
      out.write("\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t");

		} //end of while
	
      out.write("\r\n");
      out.write("</table>\r\n");

	stmt.close();
		stmt2.close();
		conn.close();
	} catch (Exception e) {
		WISE_Application.email_alert("WISE ADMIN - TOOL: "
				+ e.toString());
		return;
	}

      out.write("\r\n");
      out.write("<p>\r\n");
      out.write("<p>\r\n");
      out.write("<p>\r\n");
      out.write("</center>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("\r\n");
      out.write("\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
