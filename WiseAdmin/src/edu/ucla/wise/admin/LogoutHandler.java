package edu.ucla.wise.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ucla.wise.commons.WiseConstants;

/*
 create web page to select who to invite to what
 */

public class LogoutHandler extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		HttpSession session = req.getSession(true);

		res.setContentType("text/html");
		res.getWriter();

		session.invalidate();
		res.sendRedirect(req.getContextPath() + "/" + WiseConstants.ADMIN_APP
				+ "/index.html");
	}
}
