package edu.ucla.wise.studyspacewizard;

import javax.servlet.ServletContext;

import edu.ucla.wise.studyspacewizard.database.DatabaseConnector;

public class StudySpaceCreator {

	public static boolean createStudySpace(String studySpaceName, String password, String sqlFilePath){

		//first create a study space database
		if(! DatabaseConnector.createDatabase(studySpaceName)){
			return false;
		}

		//create all the tables in the study space

		if(! DatabaseConnector.executeSqlScript(sqlFilePath,studySpaceName)){
			return false;
		}
		return true;

	}
}
