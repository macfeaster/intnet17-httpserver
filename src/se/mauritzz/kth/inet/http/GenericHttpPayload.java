package se.mauritzz.kth.inet.http;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class GenericHttpPayload {
	private Map<String, String> headers;

	public GenericHttpPayload() {
		this.headers = new HashMap<>();
	}

	/**
	 * Parses headers from the HTTP text and stores them in the HttpPayload's header map.
	 * Supports headers of syntax Key: Value, where Key may contain letters and hyphens, and value
	 * is a sequence of any characters, where trailing whitespace is ignored.
	 * Does not error on invalid headers, but simply ignores them.
	 *
	 * @param raw       String representation of HTTP headers, as Key: value
	 */
	protected void parseHeaders(String raw) {
		// Find and parse HTTP headers
		Pattern headerPattern = Pattern.compile("^([a-zA-Z-]+): (.+)$");
		Matcher headers = headerPattern.matcher(raw);
		Map<String, String> httpHeaders = this.getHeaders();

		// Populate our header table - parse cookies when encountered
		while (headers.matches()) {
			httpHeaders.put(headers.group(1), headers.group(2).trim());
		}
	}

	public Map<String, String> getHeaders() {
		return headers;
	}
}
