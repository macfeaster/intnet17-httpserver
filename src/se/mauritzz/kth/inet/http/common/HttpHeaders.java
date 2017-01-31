package se.mauritzz.kth.inet.http.common;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HttpHeaders {

	private Map<String, String> headers = new HashMap<>();

	/**
	 * Parses headers from the HTTP text and stores them in the HttpPayload's header map.
	 * Supports headers of syntax Key: Value, where Key may contain letters and hyphens, and value
	 * is a sequence of any characters, where trailing whitespace is ignored.
	 * Does not error on invalid headers, but simply ignores them.
	 *
	 * @param raw       An HttpHeaders object containing all the found and parsed key-value pairs
	 */
	public static HttpHeaders deserialize(String raw) {
		// Find and parse HTTP headers
		Pattern pattern = Pattern.compile("([a-zA-Z-]+): (.+)");
		Matcher matcher = pattern.matcher(raw);
		HttpHeaders headers = new HttpHeaders();

		// Populate our header table - parse cookies when encountered
		while (matcher.find())
			headers.headers.put(matcher.group(1), matcher.group(2).trim());

		return headers;
	}

	/**
	 * Serialize headers into HTTP text form, namely the Key: Value syntax, separated by newlines.
	 * Does not include a trailing newline.
	 *
	 * @return          String representation of HTTP headers, as Key: Value
	 */
	public String serialize() {
		return headers.entrySet()
				.stream()
				.map(e -> e.getKey() + ": " + e.getValue())
				.collect(Collectors.joining("\n"));
	}

	/**
	 * @see Map#get(Object)
	 */
	public String get(String key) {
		return headers.get(key);
	}

	/**
	 * @see Map#put(Object, Object)
	 */
	public String put(String key, String value) {
		return headers.put(key, value);
	}

	@Override
	public String toString() {
		return headers.toString();
	}
}
