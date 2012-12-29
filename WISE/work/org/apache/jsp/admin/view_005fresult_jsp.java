package org.apache.jsp.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
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
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.servlet.jsp.JspWriter;
import javax.xml.transform.stream.*;
import com.oreilly.servlet.MultipartRequest;

public final class view_005fresult_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\"\r\n");
      out.write("\tcontent=\"text/html; charset=windows-1252\">\r\n");
      out.write("<script>\r\n");
      out.write("\t//remove the check of the option for all invitees\r\n");
      out.write("\tfunction remove_check_allusers() {\r\n");
      out.write("\t\tif (document.form1.alluser.checked)\r\n");
      out.write("\t\t\tdocument.form1.alluser.checked = false;\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\t//remove the check of the option for all the single invitees \r\n");
      out.write("\tfunction remove_check_oneuser() {\r\n");
      out.write("\t\tfor (i = 0; i < document.form1.length; i++) {\r\n");
      out.write("\t\t\tif (document.form1.elements[i].type == \"checkbox\"\r\n");
      out.write("\t\t\t\t\t&& document.form1.elements[i].name != \"alluser\")\r\n");
      out.write("\t\t\t\tdocument.form1.elements[i].checked = false;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");

	//get the server path
	String path = request.getContextPath();

      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(path);
      out.write("/admin/style.css\" type=\"text/css\">\r\n");
      out.write("<title>WISE Administration Tools - View Results</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body text=\"#333333\" bgcolor=\"#FFFFCC\">\r\n");

	session = request.getSession(true);
	//if the session is expired, go back to the logon page
	if (session.isNew()) {
		response.sendRedirect(path + "/" + WiseConstants.ADMIN_APP
				+ "/index.html");
		return;
	}
	//get the admin info object from the session
	AdminInfo admin_info = (AdminInfo) session
			.getAttribute("ADMIN_INFO");
	//get the survey ID
	String survey_id = request.getParameter("s");
	//if the session is invalid, display the error
	if (admin_info == null || survey_id == null) {
		response.sendRedirect(path + "/" + WiseConstants.ADMIN_APP
				+ "/error.htm");
		return;
	}

      out.write("\r\n");
      out.write("<center>\r\n");
      out.write("<table cellpadding=2 cellspacing=\"0\" border=0>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td width=\"160\" align=center><img src=\"admin_images/somlogo.gif\"\r\n");
      out.write("\t\t\tborder=\"0\"></td>\r\n");
      out.write("\t\t<td width=\"400\" align=\"center\"><img src=\"admin_images/title.jpg\"\r\n");
      out.write("\t\t\tborder=\"0\"><br>\r\n");
      out.write("\t\t<br>\r\n");
      out.write("\t\t<font color=\"#CC6666\" face=\"Times New Roman\" size=\"4\"><b>VIEW\r\n");
      out.write("\t\tRESULTS</b></font></td>\r\n");
      out.write("\t\t<td width=\"160\" align=center><a href=\"tool.jsp\"><img\r\n");
      out.write("\t\t\tsrc=\"admin_images/back.gif\" border=\"0\"></a></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("<p>\r\n");
      out.write("<p>\r\n");
      out.write("<form name=\"form1\" method='post' action='");
      out.print(path);
      out.write("/admin/survey_result'><input\r\n");
      out.write("\ttype='hidden' name='s' value='");
      out.print(survey_id);
      out.write("'> <br>\r\n");
      out.write("\r\n");
      out.write("<table class=tth border=1 cellpadding=\"2\" cellspacing=\"0\"\r\n");
      out.write("\tbgcolor=#FFFFF5>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td colspan=4>Enter WHERE clause for invitees in the data table:\r\n");
      out.write("\t\t<input type='text' name='whereclause' width=60></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td colspan=4>OR <input type='checkbox' name='alluser'\r\n");
      out.write("\t\t\tvalue='ALL' onClick='javascript: remove_check_oneuser()'>\r\n");
      out.write("\t\tSelect ALL recipients</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td colspan=4>OR select recipients for the message:</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\r\n");
      out.write("\t");

		try {
			//connect to the database
			Connection conn = admin_info.getDBConnection();
			Statement stmt = conn.createStatement();
			//get the survey responders' info
			String sql = "SELECT d.invitee, i.firstname, i.lastname, i.salutation, AES_DECRYPT(i.email,'"
					+ admin_info.myStudySpace.db.email_encryption_key
					+ "') FROM invitee as i, ";
			sql += survey_id
					+ "_data as d where d.invitee=i.id order by i.id";
			boolean dbtype = stmt.execute(sql);
			ResultSet rs = stmt.getResultSet();

			out.println("<tr>");
			out.println("<td class=sfon>&nbsp;</td>");
			out.println("<td class=sfon align=center>User ID</td>");
			out.println("<td class=sfon align=center>User Name</td>");
			out.println("<td class=sfon align=center>User's Email Address</td></tr>");

			while (rs.next()) {
				out.println("<tr>");
				out.println("<td align=center><input type='checkbox' name='user' value='"
						+ rs.getString(1)
						+ "' onClick='javascript: remove_check_allusers()'></td>");
				out.println("<td align=center>" + rs.getString(1) + "</td>");
				out.println("<td align=center>" + rs.getString(4) + " "
						+ rs.getString(2) + " " + rs.getString(3) + "</td>");
				out.println("<td align=center>" + rs.getString(5) + "</td>");
				out.println("</tr>");
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			AdminInfo.email_alert("WISE ADMIN - VIEW RESULT:"
					+ e.toString());
		}
	
      out.write("\r\n");
      out.write("</table>\r\n");
      out.write("<br>\r\n");
      out.write("<center><input type=\"image\" alt=\"submit\"\r\n");
      out.write("\tsrc=\"admin_images/viewresults.gif\"><br>\r\n");
      out.write("</form>\r\n");
      out.write("<hr>\r\n");
      out.write("</center>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
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
