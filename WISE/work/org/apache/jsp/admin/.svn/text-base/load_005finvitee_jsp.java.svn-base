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
import javax.xml.transform.stream.*;
import com.oreilly.servlet.MultipartRequest;

public final class load_005finvitee_jsp extends org.apache.jasper.runtime.HttpJspBase
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

	//get the server path
	String path = request.getContextPath();

      out.write("\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write("function delete_inv(iid)\r\n");
      out.write("{\r\n");
      out.write("  \tif (confirm(\"Are you sure you want to delete invitee \"+iid+\"?\"))\r\n");
      out.write("\t{\r\n");
      out.write("\t\tdocument.form3.changeID.value=iid; \r\n");
      out.write("\t\tdocument.form3.delflag.value=true; \r\n");
      out.write("\t\tdocument.form3.submit();\r\n");
      out.write("\t}\r\n");
      out.write("}\r\n");
      out.write("function update_inv(iid)\r\n");
      out.write("{\r\n");
      out.write("\tdocument.form3.changeID.value=iid; \r\n");
      out.write("\tdocument.form3.submit();\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(path);
      out.write("/style.css\" type=\"text/css\">\r\n");
      out.write("<title>WISE Administration Tools - Load Invitee</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body text=\"#333333\" bgcolor=\"#FFFFCC\">\r\n");
      out.write("<center>\r\n");
      out.write("<table cellpadding=2 cellspacing=\"0\" border=0>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td width=\"160\" align=center><img src=\"admin_images/somlogo.gif\"\r\n");
      out.write("\t\t\tborder=\"0\"></td>\r\n");
      out.write("\t\t<td width=\"400\" align=\"center\"><img src=\"admin_images/title.jpg\"\r\n");
      out.write("\t\t\tborder=\"0\"></td>\r\n");
      out.write("\t\t<td width=\"160\" align=center>&nbsp;</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<table cellpadding=2 cellspacing=\"0\" border=0>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td align=center>\r\n");
      out.write("\t\t<form name=\"form2\" method=\"post\" action=\"load_invitee.jsp\">\r\n");
      out.write("\t\t<table class=tth border=1 cellpadding=\"6\" cellspacing=\"0\"\r\n");
      out.write("\t\t\tbgcolor=#FFFFF5>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td width=400 bgcolor=\"#CC6666\" align=center><font color=white><b>Load\r\n");
      out.write("\t\t\t\tSingle Invitee</b></font></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t");

				session = request.getSession(true);
				//if the session is expired, go back to the logon page
				if (session.isNew()) {
					response.sendRedirect(path + "/index.html");
					return;
				}
				//get the admin info object
				AdminInfo admin_info = (AdminInfo) session
						.getAttribute("ADMIN_INFO");
				//if the session is invalid, display the error
				if (admin_info == null) {
					response.sendRedirect(path + "/" + WiseConstants.ADMIN_APP
							+ "/error.htm");
					return;
				}
				admin_info.update_invitees(request);
				out.println(admin_info.handle_addInvitees(request));
			
      out.write("\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</form>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td width=400 align=center>\r\n");
      out.write("\t\t<FORM name=\"form1\" action=\"load_data\" method=\"post\"\r\n");
      out.write("\t\t\tencType=\"multipart/form-data\">\r\n");
      out.write("\t\t<table class=tth border=1 cellpadding=\"6\" cellspacing=\"0\"\r\n");
      out.write("\t\t\tbgcolor=#FFFFF5>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td width=400 bgcolor=\"#CC6666\" align=center><font color=white><b>Load\r\n");
      out.write("\t\t\t\tInvitee File for Multiple Invitees</b></font></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td align=center>Select a invitee file (csv) to upload:<br>\r\n");
      out.write("\t\t\t\t&nbsp;<br>\r\n");
      out.write("\t\t\t\t<INPUT type=file name=file> &nbsp; <input type=\"image\"\r\n");
      out.write("\t\t\t\t\talt=\"submit\" src=\"admin_images/upload.gif\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>Please note: The first line of the csv file must match the\r\n");
      out.write("\t\t\t\tcolumn names of your invitee data. Please <a\r\n");
      out.write("\t\t\t\t\thref=\"change_invitee.jsp\">Edit the Invitee Table</a> to add all\r\n");
      out.write("\t\t\t\tcolumns you needed.</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</FORM>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td align=center><a href=\"download_file.jsp?fileName=invitee.csv\">\r\n");
      out.write("\t\tClick here to download the current invitees (csv file)</a></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td width=400 align=center>\r\n");
      out.write("\t\t<FORM name=\"form3\" action=\"load_invitee.jsp\" method=\"post\">\r\n");
      out.write("\t\t<table class=tth border=1 cellpadding=\"6\" cellspacing=\"0\"\r\n");
      out.write("\t\t\tbgcolor=#FFFFF5>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td bgcolor=\"#CC6666\" align=center><font color=white><b>Edit\r\n");
      out.write("\t\t\t\tInvitee Information</b><br>\r\n");
      out.write("\t\t\t\t(Note: those already invited to participate aren't editable.)</font> <input\r\n");
      out.write("\t\t\t\t\ttype=\"hidden\" name=\"delflag\" value=\"false\"> <input\r\n");
      out.write("\t\t\t\t\ttype=\"hidden\" name=\"changeID\"></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td>");
      out.print(admin_info.print_initial_invitee_editable("Enrollmt"));
      out.write("\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t</table>\r\n");
      out.write("\t\t</FORM>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td align=center>\r\n");
      out.write("\t\t<p><a href=\"tool.jsp\">Return to Administration Tools</a>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
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
