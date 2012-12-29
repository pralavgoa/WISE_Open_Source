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

public final class initial_005finvite_005fsend_jsp extends org.apache.jasper.runtime.HttpJspBase
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

        //get the path
        String path=request.getContextPath();

      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(path);
      out.write("/style.css\" type=\"text/css\">\r\n");
      out.write("<title>WISE Administration Tools - Results of Sending Initial\r\n");
      out.write("Invitation</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body text=\"#333333\" bgcolor=\"#FFFFCC\">\r\n");
      out.write("<center>\r\n");

        session = request.getSession(true);
        if (session.isNew())
        {
            response.sendRedirect(path+"/index.html");
            return;
        }

        //get the admin info obj
        AdminInfo admin_info = (AdminInfo) session.getAttribute("ADMIN_INFO");
        if(admin_info == null)
        {
            response.sendRedirect(path + "/error.htm");
            return;
        }
        
        String seq_id = request.getParameter("seq");
        String svy_id = request.getParameter("svy");
        if (seq_id == null || svy_id == null )       
        {

      out.write("\r\n");
      out.write("<p>Error: Message sequence or survey identity missing</p>\r\n");

          return;
        }
        
        String user[] = request.getParameterValues("user");
        String whereStr = request.getParameter("whereclause");
        
        if(user==null)
        {
           if (whereStr == null || whereStr.equals(""))
           {
              out.println("<p>Error: You must select at least one invitee.</p>");
              return;
           }           
        }
        else
        {
           if (whereStr != null && !whereStr.equals(""))
            whereStr += " and invitee.id in (";
           else
            whereStr = "invitee.id in (";
           for (int i = 0; i < user.length; i++)
              whereStr += user[i] + ",";
           whereStr = whereStr.substring(0, whereStr.lastIndexOf(',')) + ")";
        }
        

      out.write("\r\n");
      out.write("<table cellpadding=2 cellspacing=\"0\" border=0>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td width=\"160\" align=center><img src=\"admin_images/somlogo.gif\"\r\n");
      out.write("\t\t\tborder=\"0\"></td>\r\n");
      out.write("\t\t<td width=\"400\" align=\"center\"><img src=\"admin_images/title.jpg\"\r\n");
      out.write("\t\t\tborder=\"0\"><br>\r\n");
      out.write("\t\t<br>\r\n");
      out.write("\t\t<font color=\"#CC6666\" face=\"Times New Roman\" size=\"4\"><b>Invitation\r\n");
      out.write("\t\tattempt - Results</b></font></td>\r\n");
      out.write("\t\t<td width=\"160\" align=center><a href='tool.jsp'><img\r\n");
      out.write("\t\t\tsrc=\"admin_images/back.gif\" border=\"0\"></a></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("<table cellpadding=2 cellspacing=\"0\" width=400 border=0>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td>");
      out.print(admin_info.send_messages("invite", seq_id, svy_id, whereStr));
      out.write("</td>\r\n");
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
