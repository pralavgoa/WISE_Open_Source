/**
 * 
 */
package edu.ucla.wise.emailscheduler;

import java.io.IOException;

import org.apache.log4j.Logger;

import edu.ucla.wise.commons.AdminInfo;
import edu.ucla.wise.commons.DataBank;
import edu.ucla.wise.commons.StudySpace;

/**
 * This email thread will spawn action of sending reminders.
 * 
 */
public class EmailScheduler {

	static Logger LOG = Logger.getLogger(EmailScheduler.class);

    public static void sendEmails(String appName) {

	// start the email sending procedure
	java.util.Date today = new java.util.Date();

		LOG.info("Launching Email Manager on " + today.toString()
		+ " for studies assigned to " + appName + " on this server.");

	try {
	    AdminInfo.check_init(appName);
	} catch (IOException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}

	StudySpace[] allSpaces = new StudySpace[0];
	try {
	    allSpaces = StudySpace.get_all();
	} catch (Exception e) {
			LOG.info(" --> Emailer err - Can't get study_spaces: "
		    + e.toString());
	    e.printStackTrace(System.out);
	}
	// iterate over all Study_Spaces that this server manages
	for (int i = 0; i < allSpaces.length; i++) {
	    StudySpace ss = allSpaces[i];
	    DataBank db = ss.db;
			LOG.info("\nStudy_Space " + ss.study_name
		    + " CONNECTING to database: " + db.dbdata);
			LOG.info(db.send_reminders());
	}
    }

}
