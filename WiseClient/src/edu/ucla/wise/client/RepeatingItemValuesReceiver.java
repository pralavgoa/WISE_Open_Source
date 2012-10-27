package edu.ucla.wise.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ucla.wise.commons.SurveyorApplication;
import edu.ucla.wise.commons.User;
import edu.ucla.wise.commons.UserDBConnection;
import edu.ucla.wise.commons.WISEApplication;

public class RepeatingItemValuesReceiver extends HttpServlet {
    static final long serialVersionUID = 1000;

    public void service(HttpServletRequest req, HttpServletResponse res)
	    throws ServletException, IOException {

	// prepare for writing
	PrintWriter out;
	res.setContentType("text/html");
	out = res.getWriter();
	String initErr = SurveyorApplication.check_init(req.getContextPath());
	HttpSession session = req.getSession(true);

	if (initErr != null) {
	    out.println("<HTML><HEAD><TITLE>WISE survey system -- Can't identify you</TITLE>"
		    + "<LINK href='"
		    + SurveyorApplication.shared_file_url
		    + "style.css' type=text/css rel=stylesheet>"
		    + "<body><center><table>"
		    // + "<body text=#000000 bgColor=#ffffcc><center><table>"
		    + "<tr><td>Sorry, the WISE Surveyor application failed to initialize. "
		    + "Please contact the system administrator with the following information."
		    + "<P>"
		    + initErr
		    + "</td></tr>"
		    + "</table></center></body></html>");
	    WISEApplication.log_error("WISE Surveyor Init Error: " + initErr,
		    null);// should write to file if no email
	    return;
	}

	// if session is new, then it must have expired since begin; show the
	// session expired info
	if (session.isNew()) {
	    res.sendRedirect(SurveyorApplication.shared_file_url + "error"
		    + SurveyorApplication.html_ext);
	    return;
	}
	// get the user from session
	User theUser = (User) session.getAttribute("USER");
	if (theUser == null || theUser.id == null) // latter signals an
						   // improperly-initialized
						   // User
	{
	    out.println("<p>Error: Can't find the user info.</p>");
	    return;
	}

	String repeat_table_name = req.getParameter("repeat_table_name");
	String repeat_table_row = req.getParameter("repeat_table_row");
	String repeat_table_row_name = req
		.getParameter("repeat_table_row_name");
	System.out.println("Name of repeat table: " + repeat_table_name);
	System.out.println("Repeat table row is: " + repeat_table_row);
	// get all the fields values from the request and save them in the hash
	// table
	Hashtable<String, String> params = new Hashtable<String, String>();
	Hashtable<String, String> types = new Hashtable<String, String>();

	String name, value;
	Enumeration e = req.getParameterNames();
	while (e.hasMoreElements()) {
	    name = (String) e.nextElement();
	    value = req.getParameter(name);
	    System.out.println(name);
	    System.out.println(value);
	    if (!name.contains("repeat_table_name")
		    && !name.contains("repeat_table_row")
		    && !name.contains("repeat_table_row_name")) {
		// Parse out the proper name here

		// here split the value into its constituents
		String[] type_and_value = value.split(":::");

		if (type_and_value.length == 2) {
		    params.put(name, type_and_value[1]);
		    types.put(name, type_and_value[0]);
		} else {
		    // do nothing
		}
	    } else {
		;// do nothing
	    }
	}

	put_values_in_database(repeat_table_name, repeat_table_row,
		repeat_table_row_name, theUser, params, types);

	return;

    }

    private void put_values_in_database(String table_name, String row_id,
	    String row_name, User theUser, Hashtable<String, String> params,
	    Hashtable<String, String> param_types) {
	// get database connection
	UserDBConnection user_db_connection = theUser.getMyDataBank();

	System.out.println(params);
	System.out.println(param_types);
	// send the table name and values to the database
	user_db_connection.insert_update_row_repeating_table(table_name,
		row_id, row_name, params, param_types);
    }
}
