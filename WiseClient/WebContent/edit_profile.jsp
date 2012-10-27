<%@page import="edu.ucla.wise.client.interview.InterviewManager"%>
<%@ page contentType="text/html;charset=windows-1252"%><%@ page
	language="java"%><%@ page
	import="edu.ucla.wise.commons.*,java.sql.*,java.util.Date,java.util.*,java.net.*,java.io.*,org.xml.sax.*,org.w3c.dom.*,javax.xml.parsers.*,java.lang.*,javax.xml.transform.*,javax.xml.transform.dom.*,javax.xml.transform.stream.*,com.oreilly.servlet.MultipartRequest"%><html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252">
<%
	//get the path
	String path = request.getContextPath();
%>
<link rel="stylesheet" href="<%=path%>/style.css" type="text/css">
<title>Edit Interviewer Profile</title>
</head>
<body text="#333333" bgcolor="#FFFFCC">
<center>
<table cellpadding=2 cellpadding="0" cellspacing="0" border=0>
	<tr>
		<td width="160" align=center><img src="admin_images/somlogo.gif"
			border="0"></td>
		<td width="400" align="center"><img src="admin_images/title.jpg"
			border="0"><br>
		<br>
		<font color="#CC6666" face="Times New Roman" size="4"><b>Saving
		WATI Assignments</b></font></td>
		<td width="160" align=center>&nbsp;</td>
	</tr>
</table>
</center>
<br>
<center>
<%
	session = request.getSession(true);

	if (session.isNew()) {
		response.sendRedirect(path + "/index.html");
		return;
	}

	//get the admin info obj
	AdminInfo admin_info = (AdminInfo) session
			.getAttribute("ADMIN_INFO");
	String edit_type = request.getParameter("edit_type");
	if (admin_info == null) {
		response.sendRedirect(path + "/error.htm");
		return;
	}

	//check the edit type
	if (edit_type != null && edit_type.equalsIgnoreCase("1")) {
%>
<form method="post" action="remove_profile.jsp">
<table class=tth width=500 border=1 cellpadding="2" cellspacing="2"
	bgcolor=#FFFFE1>
	<tr bgcolor=#003366>
		<td align=center colspan=2><font color=white>R E M O V
		E&nbsp;&nbsp;P R O F I L E</font></td>
	</tr>
	<%
		} else {
	%>
	<form method="post" action="save_profile.jsp">
	<table class=tth width=500 border=1 cellpadding="2" cellspacing="2"
		bgcolor=#FFFFE1>
		<tr bgcolor=#003366>
			<td align=center colspan=2><font color=white>I N T E R V
			I E W E R&nbsp;&nbsp;P R O F I L E</font></td>
		</tr>
		<%
			}

			//check if it is for editing
			boolean isPostMethod = request.getMethod().equals("POST");
			if (isPostMethod) {
				//get the interviewers id and create the interviewer obj
				if (request.getParameterValues("interviewer") == null) {
					out.println("<tr><td align=center colspan=2>Error: You have to choose at least one interviewer to edit</td>");
					out.println("</tr></table></form></center></body></html>");
					return;
				}

				String interviewer[] = request
						.getParameterValues("interviewer");

				//check the edit type
				if (edit_type.equalsIgnoreCase("1")) {
					//if it is for the operation of remove
					out.println("<tr><td align=center colspan=2>You are going to remove the interviewer you selected.<br>");
					out.println("The interviewer(s) with ID of ");
					for (int i = 0; i < interviewer.length; i++) {
						out.println(interviewer[i]);
						if (i < interviewer.length - 1)
							out.println(", ");
					}
					out.println(" will be permanately deleted from database.<br>");
					out.println("Click UPDATE button to proceed, otherwises choose the CANCEL button.</td></tr>");
					session.setAttribute("INVLIST", interviewer);
				} else //to edit the interviewer
				{
					Interviewer[] inv = new Interviewer[interviewer.length];
					for (int i = 0; i < interviewer.length; i++) {
						//inv[i] = new Interviewer(admin_info);
						inv[i] = InterviewManager.getInstance().getInterviewer(
								admin_info.myStudySpace, interviewer[i]);
						if (inv[i] == null) {
							// TODO
							continue;
						}
		%>
		<tr>
			<td class=spt align=center>User Name</td>
			<td><input type="text" name="username_<%=inv[i].id%>"
				value="<%=inv[i].user_name%>" size="20" maxlength="30"></td>
		</tr>
		<tr>
			<td class=spt align=center>User Pass</td>
			<td><%=inv[i].id%></td>
		</tr>
		<tr>
			<td class=spt align=center>Salutation</td>
			<td><input type="text" name="salutation_<%=inv[i].id%>"
				value="<%=inv[i].salutation.toUpperCase()%>" size="20"
				maxlength="30"></td>
		</tr>
		<tr>
			<td class=spt align=center>First Name</td>
			<td><input type="text" name="firstname_<%=inv[i].id%>"
				value="<%=inv[i].first_name%>" size="20" maxlength="30"></td>
		</tr>
		<tr>
			<td class=spt align=center>Last Name</td>
			<td><input type="text" name="lastname_<%=inv[i].id%>"
				value="<%=inv[i].last_name%>" size="20" maxlength="30"></td>
		</tr>
		<td class=spt align=center>Email</td>
		<td><input type="text" name="email_<%=inv[i].id%>"
			value="<%=inv[i].email%>" size="40" maxlength="60"></td>
		</tr>
		<tr>
			<td colspan=2>&nbsp;</td>
		</tr>
		<%
			}
					//save the interviewer array into session
					session.setAttribute("INTERVIEWER", inv);
				}
			} else //for adding a new interviewer
			{
				Interviewer[] new_inv = new Interviewer[1];

				new_inv[0] = new Interviewer(admin_info.myStudySpace);
				new_inv[0].id = InterviewManager.getInstance().get_newid(
						new_inv[0].study_space);
				new_inv[0].first_name = "";
				new_inv[0].last_name = "";
				new_inv[0].user_name = "";
				new_inv[0].email = "";
				new_inv[0].salutation = "Dr.";
		%>
		<tr>
			<td class=spt align=center>User Name</td>
			<td><input type="text" name="username_<%=new_inv[0].id%>"
				value="<%=new_inv[0].user_name%>" size="20" maxlength="30">
			</td>
		</tr>
		<tr>
			<td class=spt align=center>User Pass</td>
			<td><%=new_inv[0].id%></td>
		</tr>
		<tr>
			<td class=spt align=center>Salutation</td>
			<td><input type="text" name="salutation_<%=new_inv[0].id%>"
				value="<%=new_inv[0].salutation.toUpperCase()%>" size="20"
				maxlength="30"></td>
		</tr>
		<tr>
			<td class=spt align=center>First Name</td>
			<td><input type="text" name="firstname_<%=new_inv[0].id%>"
				value="<%=new_inv[0].first_name%>" size="20" maxlength="30">
			</td>
		</tr>
		<tr>
			<td class=spt align=center>Last Name</td>
			<td><input type="text" name="lastname_<%=new_inv[0].id%>"
				value="<%=new_inv[0].last_name%>" size="20" maxlength="30">
			</td>
		</tr>
		<td class=spt align=center>Email</td>
		<td><input type="text" name="email_<%=new_inv[0].id%>"
			value="<%=new_inv[0].email%>" size="40" maxlength="60"></td>
		</tr>
		<tr>
			<td colspan=2>&nbsp;</td>
		</tr>
		<%
			session.setAttribute("EditType", "add");
				session.setAttribute("INTERVIEWER", new_inv);
			}
		%>

	</table>
	<p>
	<p>
	<p><input type="image" alt="submit" src="admin_images/update.gif">
	<a href="list_interviewer.jsp"> <img src="admin_images/cancel.gif"
		border="0"></a>
	</form>
	</center>
</body>
</html>
