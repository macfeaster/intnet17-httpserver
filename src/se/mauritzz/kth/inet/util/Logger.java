package se.mauritzz.kth.inet.util;

public class Logger {
	private static Logger instance;
	private static java.util.logging.Logger logger;

	public static Logger getInstance() {
		if (instance == null) {
			instance = new Logger();
			logger = java.util.logging.Logger.getAnonymousLogger();
		}

		return instance;
	}

	public Logger info(Object message) {
		logger.info(message.toString());
		return instance;
	}

	public Logger warning(Object message) {
		logger.warning(message.toString());
		return instance;
	}

	public Logger severe(Object message) {
		logger.severe(message.toString());
		return instance;
	}
}
