package se.mauritzz.kth.inet.util;

public class Logger {
	private java.util.logging.Logger impl;

	public static Logger get(String name) {
		Logger logger = new Logger();
		logger.impl = java.util.logging.Logger.getLogger(name);
		return logger;
	}

	public Logger info(Object message) {
		impl.info(message.toString());
		return this;
	}

	public Logger warning(Object message) {
		impl.warning(message.toString());
		return this;
	}

	public Logger severe(Object message) {
		impl.severe(message.toString());
		return this;
	}
}
