package edu.ucla.wise.emailscheduler.test;

import edu.ucla.wise.emailscheduler.EmailSchedulerInitializer;

public class EmailSchedulerTest {
	public static void main(String[] args) {
		EmailSchedulerInitializer emailIni = new EmailSchedulerInitializer();

		for (int hour = 1; hour <= 23; hour++) {
			long emailWaitTime = emailIni
					.calculateInitialWaitPeriodInMillis(hour);

			long emailWaitTimeInHours = emailWaitTime
					/ EmailSchedulerInitializer.MILLISECONDS_IN_AN_HOUR;

			System.out.println(emailWaitTimeInHours);
		}

	}
}
