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

public final class drop_005fsurvey_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<title>WISE DROP SURVEY DATA</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body text=\"#333333\" bgcolor=\"#FFFFCC\">\r\n");
      out.write("<center>\r\n");
      out.write("<table cellpadding=2 cellspacing=\"0\" border=0>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td width=\"160\" align=center><img src=\"admin_images/somlogo.gif\"\r\n");
      out.write("\t\t\tborder=\"0\"></td>\r\n");
      out.write("\t\t<td width=\"400\" align=\"center\"><img src=\"admin_images/title.jpg\"\r\n");
      out.write("\t\t\tborder=\"0\"></td>\r\n");
      out.write("\t</tr>\r\n");
      out.write("</table>\r\n");
      out.write("<table cellpadding=2 cellspacing=\"0\" border=0>\r\n");
      out.write("\t<tr>\r\n");
      out.write("\t\t<td align=center>\r\n");
      out.write("\t\t");


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
        
        String survey_id = request.getParameter("s");
        String survey_status = request.getParameter("t");
		
		//==> run the updates on the database
		String resultStr = admin_info.clearSurvey(survey_id, survey_status);
		out.println( resultStr );
        
        //==> send URL request to update survey in remote server
		if (resultStr.indexOf("ERROR") == -1)
		{
        URL url = new URL(admin_info.getStudyServerPath()+"admin/survey_update?SID="+admin_info.study_id+"&SurveyID="+survey_id+"&SurveyStatus="+survey_status);
		// ==>
		
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        String upload_result="";
        while ((line = in.readLine()) != null)
        {
              upload_result += line;
        }

        //AdminInfo.email_alert("SURVEY UPDATE RESULT IS " + upload_result);
        in.close();
        out.println(upload_result);
		}

      out.write("\r\n");
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
