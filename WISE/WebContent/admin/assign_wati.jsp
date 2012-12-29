<%@ page contentType="text/html;charset=windows-1252"%><%@ page
	language="java"%><%@ page
	import="edu.ucla.wise.commons.*,
			java.sql.*,java.util.Date,java.util.*,java.net.*,java.io.*,org.xml.sax.*,org.w3c.dom.*,
			javax.xml.parsers.*,java.lang.*,javax.xml.transform.*,javax.xml.transform.dom.*,javax.servlet.jsp.JspWriter,javax.xml.transform.stream.*,com.oreilly.servlet.MultipartRequest"%><html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252">
<%
	//get the path
	String path = request.getContextPath();
%>
<link rel="stylesheet" href="<%=path%>/style.css" type="text/css">
<title>WISE Administration Tools - Assign Wati</title>
</head>
<body text="#333333" bgcolor="#FFFFCC">
<%
	session = request.getSession(true);
	if (session.isNew()) {
		response.sendRedirect(path + "/index.html");
		return;
	}

	//get the admin info obj
	AdminInfo admin_info = (AdminInfo) session
			.getAttribute("ADMIN_INFO");
	String s_id = request.getParameter("s");
	if (admin_info == null || s_id == null) {
		response.sendRedirect(path + "/error.htm");
		return;
	}
%>
<center>
<table cellpadding=2 cellpadding="0" cellspacing="0" border=0>
	<tr>
		<td width="160" align=center><img src="admin_images/somlogo.gif"
			border="0"></td>
		<td width="400" align="center"><img src="admin_images/title.jpg"
			border="0"><br>
		<br>
		<font color="#CC6666" face="Times New Roman" size="4"><b>Assign
		WATI</b></font></td>
		<td width="160" align=center><a href="javascript: history.go(-1)"><img
			src="admin_images/back.gif" border="0"></a></td>
	</tr>
</table>
<p>
<p>
<p>
<form method='post' action='<%=path%>/save_wati.jsp'><input
	type='hidden' name='survey' value='<%=s_id%>'>
<hr>
<%=admin_info.print_interviewer()%>
<hr>
<table class=tth border=1 cellpadding="2" cellspacing="0"
	bgcolor=#FFFFF5>
	<tr>
		<td>Enter WHERE clause for invitees <input type='text'
			name='whereclause' width=60></td>
	</tr>
	<tr>
		<td>OR <input type='checkbox' name='alluser' value='ALL'>
		Select all invitees</td>
	</tr>
	<tr>
		<td>OR <input type='checkbox' name='nonrespuser' value='ALL'>
		Select all non-responders</td>
	</tr>
	<tr>
		<td>OR select invitees for the assignments:</td>
	</tr>
</table>
<%=admin_info.print_invite()%>
<hr>
<center><input type="image" alt="submit"
	src="admin_images/assign.gif">
</form>
</center>
</body>
</html>
