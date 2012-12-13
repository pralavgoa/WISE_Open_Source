package edu.ucla.wise.admin.studyspaceparameters.test;

import java.util.Iterator;
import java.util.Map;

import edu.ucla.wise.admin.studyspaceparameters.StudySpaceParametersHandler;
import edu.ucla.wise.studyspace.parameters.StudySpaceParameters;

public class StudySpaceParametersHandlerTest {
	public static void main(String[] args) {
		StudySpaceParametersHandler.initialize();
		StudySpaceParametersHandler ssph = StudySpaceParametersHandler
				.getInstance();

		Map<String, StudySpaceParameters> sspMap = ssph
				.getAllStudySpaceParametersFromDatabase();

		Iterator<Map.Entry<String, StudySpaceParameters>> sspMapIterator = sspMap
				.entrySet().iterator();
		while (sspMapIterator.hasNext()) {

			Map.Entry<String, StudySpaceParameters> entry = sspMapIterator
					.next();

			System.out.println("Study space: " + entry.getKey() + "=>"
					+ entry.getValue());

		}

		System.out.println("Change of test case");
		System.out.println("Study space: "
				+ ssph.getStudySpaceParameters("test"));

	}
}
