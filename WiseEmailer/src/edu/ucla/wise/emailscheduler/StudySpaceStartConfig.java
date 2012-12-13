package edu.ucla.wise.emailscheduler;

public class StudySpaceStartConfig {

	private final String studySpaceName;
	private final String studySpaceEmailHour;

	public StudySpaceStartConfig(String studySpaceName,
			String studySpaceEmailHour) {
		this.studySpaceName = studySpaceName;
		this.studySpaceEmailHour = studySpaceEmailHour;
	}

	/**
	 * @return the studySpaceName
	 */
	public String getStudySpaceName() {
		return studySpaceName;
	}

	/**
	 * @return the studySpaceEmailHour
	 */
	public String getStudySpaceEmailHour() {
		return studySpaceEmailHour;
	}

}
