package edu.ucla.wise.studyspace.parameters;

//Class to encapsulate study space parameters
public class StudySpaceParameters {

	private final String name;
	private final String id;
	private final String serverUrl;
	private final String serverApplication;
	private final String sharedFiles_linkName;
	private final String folderName;
	private final String databaseUsername;
	private final String databasePassword;
	private final String projectTitle;
	private final String databaseEncryptionKey;

	public StudySpaceParameters(String name, String id, String serverUrl,
			String serverApplication, String sharedFiles_linkName,
			String folderName, String databaseUsername,
			String databasePassword, String databaseName, String projectTitle,
			String databaseEncryptionKey) {

		this.name = name;
		this.id = id;
		this.serverUrl = serverUrl;
		this.serverApplication = serverApplication;
		this.sharedFiles_linkName = sharedFiles_linkName;
		this.folderName = folderName;
		this.databaseUsername = databaseUsername;
		this.databasePassword = databasePassword;
		this.projectTitle = projectTitle;
		this.databaseEncryptionKey = databaseEncryptionKey;

	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the serverUrl
	 */
	public String getServerUrl() {
		return serverUrl;
	}

	/**
	 * @return the serverApplication
	 */
	public String getServerApplication() {
		return serverApplication;
	}

	/**
	 * @return the sharedFiles_linkName
	 */
	public String getSharedFiles_linkName() {
		return sharedFiles_linkName;
	}

	/**
	 * @return the folderName
	 */
	public String getFolderName() {
		return folderName;
	}

	/**
	 * @return the databaseUsername
	 */
	public String getDatabaseUsername() {
		return databaseUsername;
	}

	/**
	 * @return the databasePassword
	 */
	public String getDatabasePassword() {
		return databasePassword;
	}

	/**
	 * @return the projectTitle
	 */
	public String getProjectTitle() {
		return projectTitle;
	}

	/**
	 * @return the databaseEncryptionKey
	 */
	public String getDatabaseEncryptionKey() {
		return databaseEncryptionKey;
	}

	@Override
	public String toString() {
		StringBuilder parameters = new StringBuilder();

		parameters.append(name).append('|');
		parameters.append(id).append('|');
		parameters.append(serverUrl).append('|');
		parameters.append(serverApplication).append('|');
		parameters.append(sharedFiles_linkName).append('|');
		parameters.append(folderName).append('|');
		parameters.append(databaseUsername).append('|');
		parameters.append(databasePassword).append('|');
		parameters.append(projectTitle).append('|');
		parameters.append(databaseEncryptionKey).append("||");

		return parameters.toString();

	}

}
