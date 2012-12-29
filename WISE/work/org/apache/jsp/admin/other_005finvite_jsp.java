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

public final class other_005finvite_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\tcontent=\"text/html; charset=windows-1252\" />\r\n");
      out.write("<script language=\"javascript\">\r\n");
      out.write("  var vid;\r\n");
      out.write("  var list_id;\r\n");
      out.write("  //vid - invited user type: all users, users with specific irb number\r\n");
      out.write("  //list_id - user ID list\r\n");
      out.write("  //check the option of the selected user\r\n");
      out.write("  function check_invite_list()\r\n");
      out.write("  {\r\n");
      out.write("    if(vid==\"alluser\")\r\n");
      out.write("    {\r\n");
      out.write("      for(i=0; i < document.form1.length; i++)\r\n");
      out.write("      {\r\n");
      out.write("        if(document.form1.elements[i].type==\"checkbox\" && document.form1.elements[i].name ==\"user\")\r\n");
      out.write("          document.form1.elements[i].checked=true;\r\n");
      out.write("      }\r\n");
      out.write("    }\r\n");
      out.write("    if(vid==\"irb\")\r\n");
      out.write("    {\r\n");
      out.write("       if(document.form1.user.length)\r\n");
      out.write("       {\r\n");
      out.write("         for(i=0; i< document.form1.user.length; i++)\r\n");
      out.write("         {\r\n");
      out.write("            if(list_id.indexOf(\" \"+document.form1.user[i].value+\" \")!=-1)\r\n");
      out.write("              document.form1.user[i].checked=true;\r\n");
      out.write("            else\r\n");
      out.write("              document.form1.user[i].checked=false;\r\n");
      out.write("         }\r\n");
      out.write("      }\r\n");
      out.write("      else\r\n");
      out.write("      {\r\n");
      out.write("         if(list_id.indexOf(\" \"+document.form1.user.value+\" \")!=-1)\r\n");
      out.write("            document.form1.user.checked=true;\r\n");
      out.write("         else\r\n");
      out.write("            document.form1.user.checked=false;\r\n");
      out.write("      }\r\n");
      out.write("        \r\n");
      out.write("    }\r\n");
      out.write("  }\r\n");
      out.write("</script>\r\n");

        //get the server path
        String path=request.getContextPath();

      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(path);
      out.write("/style.css\" type=\"text/css\">\r\n");
      out.write("<title>WISE Administration Tools - Send Initial Invitation</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body text=\"#333333\" bgcolor=\"#FFFFCC\">\r\n");
      out.write("<table width=\"90%\" border=\"0\" align=\"center\" cellpadding=\"8\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td width=\"160\" align=center><img src=\"admin_images/somlogo.gif\"\r\n");
      out.write("\t\t\tborder=\"0\"></td>\r\n");
      out.write("\t\t<td width=\"400\" align=\"center\"><img src=\"admin_images/title.jpg\"\r\n");
      out.write("\t\t\tborder=\"0\"><br>\r\n");
      out.write("\t\t<br>\r\n");
      out.write("\t\t<font color=\"#CC6666\" face=\"Times New Roman\" size=\"4\"><b>Send\r\n");
      out.write("\t\tInitial Invitation</b></font></td>\r\n");
      out.write("\t\t<td width=\"160\" align=center><a href=\"javascript: history.go(-1)\"><img\r\n");
      out.write("\t\t\tsrc=\"admin_images/back.gif\" border=\"0\"></a></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");

        session = request.getSession(true);
        //if the session is expired, go back to the logon page
        if (session.isNew())
        {
            response.sendRedirect(path+"/index.html");
            return;
        }

        //get the admin info object from the session
        AdminInfo admin_info = (AdminInfo) session.getAttribute("ADMIN_INFO");
        //get the survey ID from the request
        String s_id = request.getParameter("s");
        if(admin_info == null || s_id == null )
        {
            response.sendRedirect(path + "/error.htm");
            return;
        }
//get the IRB groups -- no longer organized this way
//        Hashtable irbgroup = new Hashtable();
//        irbgroup = admin_info.get_irb_groups();
//        if(irbgroup == null)
//        {
//            response.sendRedirect(path + "/error.htm");
//            return;
//        }

      out.write("\r\n");
      out.write("<table width=\"90%\" border=\"0\" align=\"center\" cellpadding=\"8\">\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td>");
      out.print(admin_info.render_invite_table(s_id));
      out.write("</td>\r\n");
      out.write("\t\t<td width=\"250\">\r\n");
      out.write("\t\t<p>&nbsp;</p>\r\n");
      out.write("\t\t<p>&nbsp;</p>\r\n");
      out.write("\t\t<p><em><strong><a href='#'\r\n");
      out.write("\t\t\tonClick='javascript: vid=\"alluser\"; check_invite_list();'>Select\r\n");
      out.write("\t\tAll Invitees</a> </strong></em></p>\r\n");
      out.write("\t\t<p><a href='#' onClick=\"document.form1.reset()\"><em><strong>Clear\r\n");
      out.write("\t\tSelection</strong></em></a></p>\r\n");
      out.write("\t\t<p>To further limit your selection, you can also enter a logic\r\n");
      out.write("\t\tstatement below using SQL syntax. For example, to select only invitees\r\n");
      out.write("\t\twith ID &gt; 200 AND emails ending in \".edu\", you can click \"Select\r\n");
      out.write("\t\tAll\" and enter \"invitee.id > 200 and email like \"%.edu\" below.</p>\r\n");
      out.write("\t\t<p align=center><input type='text' name='whereclause' size='40'\r\n");
      out.write("\t\t\tmaxlength='100'></p>\r\n");
      out.write("\t\t</td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("</form>\r\n");
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
