<%@ page contentType="text/html;charset=windows-1252"%><%@ page
	language="java"%><%@ page
	import="edu.ucla.wise.commons.*,java.sql.*,java.util.Date,java.util.*,java.net.*,java.io.*,org.xml.sax.*,org.w3c.dom.*,javax.xml.parsers.*,java.lang.*,javax.xml.transform.*,javax.xml.transform.dom.*,javax.xml.transform.stream.*,com.oreilly.servlet.MultipartRequest"%><html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252">
<%
	//get the server path
	String path = request.getContextPath();
%>
<script language="javascript">
	function delete_inv(iid) {
		if (confirm("Are you sure you want to delete invitee " + iid + "?")) {
			document.form3.changeID.value = iid;
			document.form3.delflag.value = true;
			document.form3.submit();
		}
	}
	function update_inv(iid) {
		document.form3.changeID.value = iid;
		document.form3.submit();
	}
</script>

<link rel="stylesheet" href="<%=path%>/style.css" type="text/css">
<title>WISE Administration Tools - Load Invitee</title>
</head>
<body text="#333333" bgcolor="#FFFFCC">
	<center>
		<table cellpadding=2 cellspacing="0" border=0>
			<tr>
				<td width="160" align=center><img
					src="admin_images/somlogo.gif" border="0"></td>
				<td width="400" align="center"><img
					src="admin_images/title.jpg" border="0"></td>
				<td width="160" align=center>&nbsp;</td>
			</tr>
		</table>


		<table cellpadding=2 cellspacing="0" border=0>
			<tr>
				<td align=center>
					<form name="form2" method="post" action="load_invitee.jsp">
						<table class=tth border=1 cellpadding="6" cellspacing="0"
							bgcolor=#FFFFF5>
							<tr>
								<td width=400 bgcolor="#CC6666" align=center><font
									color=white><b>Load Single Invitee</b></font></td>
							</tr>
							<%
								session = request.getSession(true);
								//if the session is expired, go back to the logon page
								if (session.isNew()) {
									response.sendRedirect(path + "/index.html");
									return;
								}
								//get the admin info object
								AdminInfo admin_info = (AdminInfo) session
										.getAttribute("ADMIN_INFO");
								//if the session is invalid, display the error
								if (admin_info == null) {
									response.sendRedirect(path + "/" + WiseConstants.ADMIN_APP
											+ "/error.htm");
									return;
								}
								admin_info.update_invitees(request.getParameterMap());
								out.println(admin_info.myStudySpace.db.addInviteeAndDisplayPage(request.getParameterMap()));
							%>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td width=400 align=center>
					<FORM name="form1" action="load_data" method="post"
						encType="multipart/form-data">
						<table class=tth border=1 cellpadding="6" cellspacing="0"
							bgcolor=#FFFFF5>
							<tr>
								<td width=400 bgcolor="#CC6666" align=center><font
									color=white><b>Load Invitee File for Multiple
											Invitees</b></font></td>
							</tr>
							<tr>
								<td align=center>Select a invitee file (csv) to upload:<br>
									&nbsp;<br> <INPUT type=file name=file> &nbsp; <input
									type="image" alt="submit" src="admin_images/upload.gif"></td>
							</tr>
							<tr>
								<td>Please note: The first line of the csv file must match
									the column names of your invitee data. Please <a
									href="change_invitee.jsp">Edit the Invitee Table</a> to add all
									columns you needed.
								</td>
							</tr>
						</table>
					</FORM>
				</td>
			</tr>
			<tr>
				<td align=center><a
					href="download_file.jsp?fileName=invitee.csv"> Click here to
						download the current invitees (csv file)</a></td>
			</tr>
			<tr>
				<td width=400 align=center>
					<FORM name="form3" action="load_invitee.jsp" method="post">
						<table class=tth border=1 cellpadding="6" cellspacing="0"
							bgcolor=#FFFFF5>
							<tr>
								<td bgcolor="#CC6666" align=center><font color=white><b>Edit
											Invitee Information</b><br> (Note: those already invited to
										participate aren't editable.)</font> <input type="hidden"
									name="delflag" value="false"> <input type="hidden"
									name="changeID"></td>
							</tr>
							<tr>
								<td><%=admin_info.print_initial_invitee_editable("Enrollmt")%>
								</td>
							</tr>
						</table>
					</FORM>
				</td>
			</tr>
			<tr>
				<td align=center>
					<p>
						<a href="tool.jsp">Return to Administration Tools</a>
				</td>
			</tr>
		</table>
	</center>
</body>
</html>
