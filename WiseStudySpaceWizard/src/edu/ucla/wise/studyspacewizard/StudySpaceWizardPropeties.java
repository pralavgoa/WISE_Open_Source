package edu.ucla.wise.studyspacewizard;

import java.util.Locale;
import java.util.ResourceBundle;

public class StudySpaceWizardPropeties {

	private static final ResourceBundle properties = ResourceBundle
.getBundle(
			"properties/studyspacewizard", Locale.getDefault());

	public static String getDatabaseRootUsername() {
		return properties.getString("database.root.username");
	}

	public static String getDatabaseRootPassword() {
		return properties.getString("database.root.password");
	}

	public static String getDatabaseServerHost() {
		return properties.getString("database.server.host");
	}

}
