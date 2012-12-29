/**
 * 
 */
package edu.ucla.wise.emailscheduler;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import edu.ucla.wise.commons.Data_Bank;
import edu.ucla.wise.commons.Study_Space;

/**
 * This email thread will spawn action of sending reminders.
 * 
 */
public class EmailScheduler {
	public static final String APPLICATION_NAME = "WISE";

	public static final long MILLISECONDS_IN_A_DAY = 86400000;
	public static final long MILLISECONDS_IN_AN_HOUR = 3600000;

	private static ScheduledExecutorService executor;


	static Logger LOG = Logger.getLogger(EmailScheduler.class);

	public static void startEmailSendingThreads() {
		List<Study_Space> studySpaceList = StudySpaceFetcher
				.getStudySpaces(APPLICATION_NAME);

		executor = Executors.newSingleThreadScheduledExecutor();

		for (final Study_Space studySpace : studySpaceList) {

			long emailStartHour = 0;
			try {
				emailStartHour = Long.parseLong(studySpace.emailSendingTime);

				if (emailStartHour < 0 && emailStartHour > 23) {
					throw new IllegalArgumentException();
				}
			} catch (IllegalArgumentException e) {
				System.out.println("Error in start time for "
						+ studySpace.study_name);
			}

			long initialWaitPeriodInMillis = calculateInitialWaitPeriodInMillis(emailStartHour);

			executor.scheduleAtFixedRate(new Runnable() {

				@Override
				public void run() {
					EmailScheduler.sendEmailsInStudySpace(studySpace);

				}

			}, initialWaitPeriodInMillis, 1, TimeUnit.DAYS);
		}
	}

	public static long calculateInitialWaitPeriodInMillis(long emailStartTime) {
		Calendar currentTime = Calendar.getInstance();

		Calendar calendarMidnight = Calendar.getInstance();

		calendarMidnight.set(Calendar.HOUR_OF_DAY, 0);
		calendarMidnight.set(Calendar.MINUTE, 0);
		calendarMidnight.set(Calendar.SECOND, 0);
		calendarMidnight.set(Calendar.MILLISECOND, 0);

		long millisAtMidnight = calendarMidnight.getTimeInMillis();

		System.out.println("Milliseconds at midnight: " + millisAtMidnight);

		long emailStartTimeMillis = emailStartTime * MILLISECONDS_IN_AN_HOUR;

		System.out.println("Email startTime in millis " + emailStartTimeMillis);

		long currentTimeMillis = currentTime.getTimeInMillis();

		System.out
				.println("Current time in milliseconds: " + currentTimeMillis);
		// 2. add todays milliseconds to it

		if (emailStartTimeMillis + millisAtMidnight > currentTimeMillis) {
			return emailStartTimeMillis + millisAtMidnight - currentTimeMillis;
		} else {
			return MILLISECONDS_IN_A_DAY
					- (currentTimeMillis - emailStartTimeMillis - millisAtMidnight);
		}
	}
	public static boolean sendEmailsInStudySpace(Study_Space ss){
		Data_Bank db = ss.db;
		LOG.info("\nStudy_Space " + ss.study_name
	    + " CONNECTING to database: " + db.dbdata);
		LOG.info(db.send_reminders());
		return true;
    }

	public static void destroyScheduler() {
		if (executor != null) {
			executor.shutdown();
		}
	}

}
