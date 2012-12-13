package edu.ucla.wise.admin.studyspaceparameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucla.wise.studyspace.parameters.StudySpaceParameters;
import edu.ucla.wise.studyspace.parameters.StudySpaceParametersProvider;
import edu.ucla.wise.studyspacewizard.database.DatabaseConnector;

//Singleton class to be shared across the application
public class StudySpaceParametersHandler {

	private static StudySpaceParametersHandler sSPH;
	private static Map<String, StudySpaceParameters> mapWithStudySpacesParameters;

	private StudySpaceParametersHandler() throws IllegalArgumentException {

		mapWithStudySpacesParameters = getAllStudySpaceParametersFromDatabase();

	}

	public static boolean initialize() {
		try {
			if (sSPH == null) {
				sSPH = new StudySpaceParametersHandler();
			}

		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
	
	public static StudySpaceParametersHandler getInstance() {
		return sSPH;
	}

	// Study space related parameters or null
	public StudySpaceParameters getStudySpaceParameters(String studySpaceName) {

		if (mapWithStudySpacesParameters.containsKey(studySpaceName)) {
			return mapWithStudySpacesParameters.get(studySpaceName);
		} else {
			return null;
		}

	}

	public void reloadAllStudySpaceParametersMap() {
		mapWithStudySpacesParameters = getAllStudySpaceParametersFromDatabase();
	}

	public Map<String, StudySpaceParameters> getAllStudySpaceParametersFromDatabase()
			throws IllegalArgumentException {

		Map<String, StudySpaceParameters> studySpacesParametersMap = new HashMap<String, StudySpaceParameters>();

		List<Map<String, String>> studySpaceParametersList = DatabaseConnector
				.getAllStudySpaceParameters();
		
		if(studySpaceParametersList.isEmpty()){
			throw new IllegalArgumentException();
		}
		for(int i=0;i<studySpaceParametersList.size();i++){
			Map<String, String> sspMap = studySpaceParametersList
					.get(i);
			
			StudySpaceParameters studySpaceParameters = new StudySpaceParameters(
					sspMap.get(StudySpaceParametersProvider.STUDY_SPACE_NAME),
					sspMap.get(StudySpaceParametersProvider.STUDY_SPACE_ID),
					sspMap.get(StudySpaceParametersProvider.SERVER_URL),
					sspMap.get(StudySpaceParametersProvider.SERVER_APPLICATION),
					sspMap.get(StudySpaceParametersProvider.SHARED_FILES_LINK_NAME),
					sspMap.get(StudySpaceParametersProvider.DIRECTORY_NAME),
					sspMap.get(StudySpaceParametersProvider.DATABASE_USER),
					sspMap.get(StudySpaceParametersProvider.DATABASE_PASSWORD),
					sspMap.get(StudySpaceParametersProvider.DATABASE_NAME),
					sspMap.get(StudySpaceParametersProvider.PROJECT_TITLE),
					sspMap.get(StudySpaceParametersProvider.DATABASE_CRYPTIC_KEY));

			studySpacesParametersMap.put(
					sspMap.get(StudySpaceParametersProvider.STUDY_SPACE_NAME),
					studySpaceParameters);
		}
		
		return studySpacesParametersMap;
	}

}
