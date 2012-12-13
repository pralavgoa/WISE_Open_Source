package edu.ucla.wise.emailscheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class EmailSchedulerInitializer implements ServletContextListener{

	public static final String APPLICATION_NAME = "WISE";

	public static final long MILLISECONDS_IN_A_DAY = 86400000;
	public static final long MILLISECONDS_IN_AN_HOUR = 3600000;

	private ScheduledExecutorService executor;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if (executor != null) {
			executor.shutdown();
		}

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		System.out.println("The context path is: "
				+ servletContextEvent.getServletContext().getContextPath());

		startEmailSendingThreads();

	}

	public void startEmailSendingThreads() {
		ArrayList<StudySpaceStartConfig> studySpaceStartConfig = new ArrayList<StudySpaceStartConfig>(
				StartConfigFetcher.getStartConfigurations());

		executor = Executors
				.newSingleThreadScheduledExecutor();

		for (int index = 0; index < studySpaceStartConfig.size(); index++) {

			long emailStartHour = 0;
			try {
				emailStartHour = Long.parseLong(studySpaceStartConfig
						.get(
					index).getStudySpaceEmailHour());
				
				if (emailStartHour < 0 && emailStartHour > 23) {
					throw new IllegalArgumentException();
				}
			} catch (IllegalArgumentException e) {
				System.out.println("Error in start time for "
						+ studySpaceStartConfig.get(index).getStudySpaceName());
			}

			long initialWaitPeriodInMillis = calculateInitialWaitPeriodInMillis(emailStartHour);

			executor.scheduleAtFixedRate(new Runnable() {

				@Override
				public void run() {
					EmailScheduler.sendEmails(APPLICATION_NAME);

				}

			}, initialWaitPeriodInMillis, 1, TimeUnit.DAYS);
		}
	}

	public long calculateInitialWaitPeriodInMillis(long emailStartTime) {
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
			return emailStartTimeMillis + millisAtMidnight
					- currentTimeMillis;
		} else {
			return MILLISECONDS_IN_A_DAY
					- (currentTimeMillis - emailStartTimeMillis - millisAtMidnight);
		}
	}


}
