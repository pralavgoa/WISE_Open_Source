package edu.ucla.wise.commons;

import org.apache.log4j.Logger;

public class WISELogger {

	static Logger log = Logger.getLogger(WISELogger.class);

	private WISELogger() {

	}
	public static void logError(String body, Exception e) {

		log.error(body, e);
	}

	public static void logInfo(String body) {
		log.info(body);
	}

	public static void logDebug(String body) {
		log.debug(body);
	}
}
