<%@page import="edu.ucla.wise.studyspacewizard.web.StudySpaceParametersAcceptor"%>
<%@page import="edu.ucla.wise.studyspacewizard.database.DatabaseConnector,java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Study Space</title>
<!-- Add twitter bootstrap libraries -->
<link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<!-- Make input boxes for entering all parameters -->
	<div class="hero-unit">
		<h3>WISE Current Study Spaces</h3>
	</div>
	<div class='container-fluid'>
	<table class = 'table table-striped'>
	<%
	for( Map<String,String> studySpaceParams : DatabaseConnector.getAllStudySpaceParameters()){

		out.write("<tr><td><h3>");
		out.write(studySpaceParams.get(StudySpaceParametersAcceptor.STUDY_SPACE_NAME));
		out.write("</h3><td>");
		out.write("<td>"+studySpaceParams+"</td>");
		out.write("</tr>");

	}
	%>
	</table>
	</div>
</body>
</html>