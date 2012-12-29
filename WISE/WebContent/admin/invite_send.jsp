<%@ page contentType="text/html;charset=windows-1252"%><%@ page
	language="java"%><%@ page
	import="edu.ucla.wise.commons.*, 
java.sql.*, java.util.Date, java.util.*, java.net.*, java.io.*,
org.xml.sax.*, org.w3c.dom.*, javax.xml.parsers.*,  java.lang.*,
javax.xml.transform.*, javax.xml.transform.dom.*, javax.servlet.jsp.JspWriter,
javax.xml.transform.stream.*, com.oreilly.servlet.MultipartRequest"%><html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252">
<%
        //get the path
        String path=request.getContextPath();
%>
<link rel="stylesheet" href="<%=path%>/style.css" type="text/css">
<title>WISE Administration Tools - Results of Sending Other
Invitation</title>
</head>
<body text="#333333" bgcolor="#FFFFCC">
<center>
<%
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
        String msg_id = request.getParameter("message");
        if (seq_id == null || svy_id == null || msg_id == null )       
        {
%>
<p>Error: Message sequence or survey identity missing</p>
<%
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
		   int userLen = user.length - 1;
           for (int i = 0; i < userLen; i++)
              whereStr += user[i] + ",";
           whereStr += user[userLen] + ")";
        }
        
%>
<table cellpadding=2 cellspacing="0" border=0>
	<tr>
		<td width="160" align=center><img src="admin_images/somlogo.gif"
			border="0"></td>
		<td width="400" align="center"><img src="admin_images/title.jpg"
			border="0"><br>
		<br>
		<font color="#CC6666" face="Times New Roman" size="4"><b>Invitation
		attempt - Results</b></font></td>
		<td width="160" align=center><a href='tool.jsp'><img
			src="admin_images/back.gif" border="0"></a></td>
	</tr>
</table>
<table cellpadding=2 cellspacing="0" width=400 border=0>
	<tr>
		<td><%=admin_info.send_messages(msg_id, seq_id, svy_id, whereStr, false)%></td>
	</tr>
</table>
</center>
</body>
</html>
