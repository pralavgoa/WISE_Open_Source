package org.apache.jsp.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import edu.ucla.wise.commons.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public final class download_005ffile_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write('\r');
      out.write('\n');

	//get the server path
	String path = request.getContextPath();
	String fileExt = null;
	String filepath = null;
	String outputStr = null;

	//get the admin info object from session
	AdminInfo admin_info = (AdminInfo) session
			.getAttribute("ADMIN_INFO");
	//get the download file name from the request
	String filename = request.getParameter("fileName");
	//if the session is invalid, display the error
	if (admin_info == null || filename == null) {
		response.sendRedirect(path + "/" + WiseConstants.ADMIN_APP
				+ "/error.htm");
		return;
	}

	//if the file is the stylesheet
	if (filename.indexOf(".css") != -1) {
		if (filename.equalsIgnoreCase("print.css"))
			filepath = admin_info.study_css_path; //print.css
		else
			filepath = admin_info.study_css_path; //style.css
		fileExt = FileExtensions.css.name();
		outputStr = admin_info.buildXmlCssSql(filepath, filename);
	} else if (filename.indexOf(".sql") != -1) {
		//if the file is the database backup
		filepath = admin_info.db_backup_path; //dbase mysqldump file
		fileExt = FileExtensions.sql.name();
		outputStr = admin_info.buildXmlCssSql(filepath, filename);
	} else if (filename.indexOf(".csv") != -1) {
		//if the file is the csv file (MS Excel)
		//no more creating the csv file (could be either the survey data or the invitee list)
		outputStr = admin_info.buildCsvString(filename);
		fileExt = FileExtensions.csv.name();
	} else {
		// the file should be the xml file (survey, message, preface etc.)
		filepath = admin_info.study_xml_path; //xml file
		fileExt = FileExtensions.xml.name();
		outputStr = admin_info.buildXmlCssSql(filepath, filename);
	}

	//response.setContentType("APPLICATION/OCTET-STREAM");
	response.setContentType("text/" + fileExt);
	response.setHeader("Content-Disposition", "attachment; filename=\""
			+ filename + "\"");
	out.clearBuffer();
	out.write(outputStr);
	//out.close(); Should not be closing because it is the JSP's outputStream object!

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
