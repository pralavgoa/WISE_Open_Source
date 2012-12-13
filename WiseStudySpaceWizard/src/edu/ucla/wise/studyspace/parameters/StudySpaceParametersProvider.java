/**
 * 
 */
package edu.ucla.wise.studyspace.parameters;

import edu.ucla.wise.studyspacewizard.database.DatabaseConnector;

/**
 * @author Pralav
 *
 *This class will be responsible to provide parameters to various study spaces when they contact it.
 * *
 */
public class StudySpaceParametersProvider {

	public static final String STUDY_SPACE_ID = "study_id";
	public static final String SERVER_URL = "server_url";
	public static final String SERVER_APPLICATION = "serverApp";
	public static final String SHARED_FILES_LINK_NAME = "sharedFiles_linkName";
	public static final String DIRECTORY_NAME = "dirName";
	public static final String DATABASE_USER = "dbuser";
	public static final String DATABASE_PASSWORD = "dbpass";
	public static final String DATABASE_NAME = "dbname";
	public static final String PROJECT_TITLE = "proj_title";
	public static final String DATABASE_CRYPTIC_KEY = "db_crypt_key";
	public static final String STUDY_SPACE_NAME = "studySpaceName";
	
	
	public String getParametersForStudySpace(String studySpaceName){
		
		//Construct a json like string with study space parameters
		
		//Select statement for the database;
		
		
		return DatabaseConnector.getStudySpaceParamsAsJSON(studySpaceName);	
		
	}
	
}
