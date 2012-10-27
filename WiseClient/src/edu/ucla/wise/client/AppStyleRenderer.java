package edu.ucla.wise.client;

//package ucla.merg.LOFTS;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import edu.ucla.wise.commons.CommonUtils;
import edu.ucla.wise.commons.WISEApplication;

/**
 * processes the results of a question and determines where to go next
 */

public class AppStyleRenderer extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(AppStyleRenderer.class);

    /** Process the quiz request */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	String css_name = request.getParameter("css");
	String app_name = request.getParameter("app");

	if (!CommonUtils.isEmpty(app_name)) {
	    css_name = app_name + System.getProperty("file.separator")
		    + css_name;
	}

	// Upper limit for the size of CSS file == 64k for now
	int len = 65535;
	byte[] rb = new byte[len];

	String stylesPath = WISEApplication.styles_path;
	String studySpaceStylePath = stylesPath
		+ System.getProperty("file.separator") + css_name;
	InputStream cssStream = null;
	try {
	    cssStream = CommonUtils.loadResource(studySpaceStylePath);
	    if (cssStream == null) {
		// trying to load the file, will 100% fail!
		cssStream = new FileInputStream(studySpaceStylePath);
	    }
	    cssStream.read(rb, 0, len);
	    response.reset();
	    response.setContentType("text/css");
	    response.getOutputStream().write(rb, 0, len);
	    response.getOutputStream().flush();
	} catch (IOException e) {
	    log.error("File not found", e);
	} finally {
	    if (cssStream != null) {
		cssStream.close();
	    }
	}

    }

}
