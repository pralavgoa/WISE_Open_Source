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
		<h3>WISE Study Space Creation Wizard</h3>
	</div>
	<form class="form-horizontal" action="submitStudySpaceParams"
		method="get">
		<div class="control-group">
			<label class="control-label" for="studySpaceName">Study Space
				Name:</label>
			<div class="controls">
				<input type="text" name="studySpaceName">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="serverURL">Server URL:</label>
			<div class="controls">
				<input type="text" name="serverURL">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="serverAppName">Server app
				name:</label>
			<div class="controls">
				<input type="text" name="serverAppName">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="sharedFiles_linkName">Shared files
				link name:</label>
			<div class="controls">
				<input type="text" name="sharedFiles_linkName">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="dirName">Directory name:</label>
			<div class="controls">
				<input type="text" name="dirName">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="dbuser">DB username:</label>
			<div class="controls">
				<input type="text" name="dbuser">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="dbpass">DB password:</label>
			<div class="controls">
				<input type="text" name="dbpass">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="dbname">DB name:</label>
			<div class="controls">
				<input type="text" name="dbname">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="projectTitle">Project Title:</label>
			<div class="controls">
				<input type="text" name="projectTitle">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="dbCryptKey">Database
				encryption key:</label>
			<div class="controls">
				<input type="text" name="dbCryptKey"> 	
				<button type="submit" class="btn">Submit</button>	
			</div>
		</div>
		
	</form>
</body>
</html>