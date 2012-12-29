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

public final class show_005fpeople_jsp extends org.apache.jasper.runtime.HttpJspBase
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
        String path=request.getContextPath();

      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(path);
      out.write("/style.css\" type=\"text/css\">\r\n");
      out.write("<title>WISE Administration Tools - Group Invitees</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body text=\"#333333\" bgcolor=\"#FFFFCC\">\r\n");
      out.write("<center>\r\n");
      out.write("<table cellpadding=\"0\" cellspacing=\"0\" border=0>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td width=\"160\" align=center><img src=\"admin_images/somlogo.gif\"\r\n");
      out.write("\t\t\tborder=\"0\"></td>\r\n");
      out.write("\t\t<td width=\"400\" align=\"center\"><img src=\"admin_images/title.jpg\"\r\n");
      out.write("\t\t\tborder=\"0\"></td>\r\n");
      out.write("\t\t<td width=\"160\" align=center><a href=\"javascript: history.go(-1)\"><img\r\n");
      out.write("\t\t\tsrc=\"admin_images/back.gif\" border=\"0\"></a></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("<p>\r\n");
      out.write("<p>\r\n");
      out.write("<p>\r\n");


        session = request.getSession(true);
        //if the session is expired, go back to the logon page
        if (session.isNew())
        {
            response.sendRedirect(path+"/index.html");
            return;
        }

        //get the admin info object from the session
        AdminInfo admin_info = (AdminInfo) session.getAttribute("ADMIN_INFO");
        String survey_id = request.getParameter("s");
        
        //if the session is invalid, display the error
        if(admin_info == null || survey_id == null) {
            response.sendRedirect(path + "/error.htm");
            return;
        }

        String state_id = request.getParameter("st");
		String state_title="";
		if (state_id.equals("not_invited"))
			state_title = "Potential users who have not been invited";
		else if (state_id.equals("invited"))
			state_title = "Invitees who have received initial invites but have not responded";
		else if (state_id.equals("started"))
			state_title = "Invitees who seem to be taking the survey now (no interrupt detected)";
		else if (state_id.equals("declined"))
			state_title = "Invitees who have explicitly declined";
		else if (state_id.equals("interrupted"))
			state_title = "Incompletes (interrupt detected) awaiting first completion reminder";
		else if (state_id.equals("start_reminder"))
			state_title = "Non-responders who have received one or more start reminders";
		else if (state_id.equals("non_responder"))
			state_title = "Final non-responders, after all start reminders";
		else if (state_id.equals("completion_reminder"))
			state_title = "Incompletes who have received one or more completion reminders";
		else if (state_id.equals("incompleter"))
			state_title = "Final incompletes, after all completion reminders";
		else if (state_id.equals("completed"))
			state_title = "Invitees who completed the survey";

        //print out the user groups identified by their state
 
      out.write("\r\n");
      out.write("\r\n");
      out.write("<table cellpadding=2 cellspacing=\"0\" border=1 bgcolor=#FFFFF5>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td height=30 bgcolor=\"#6666CC\" align=center colspan=7><font\r\n");
      out.write("\t\t\tcolor=white><b>");
      out.print(state_title);
      out.write("</b></font></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("\t");
      out.print(admin_info.print_user_state(state_id, survey_id));
      out.write("\r\n");
      out.write("</table>\r\n");
      out.write("<p>\r\n");
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
