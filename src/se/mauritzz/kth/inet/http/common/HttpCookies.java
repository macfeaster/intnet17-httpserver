package se.mauritzz.kth.inet.http.common;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpCookies {

	private Map<String, String> cookies = new HashMap<>();

	/**
	 * Parses cookies from the HTTP text and stores them in the cookie map.
	 * Supports cookies of syntax key=value, where Key may contain alphanumeric characters, hyphens and dots,
	 * and value is a sequence of any characters ending with the ";" character or end of string.
	 * Does not error on invalid cookies, but simply ignores them.
	 *
	 * @param raw       String representation of HTTP cookies, as Key: value
	 */
	public static HttpCookies deserialize(String raw) {
		// Find and match cookies according to HTTP spec
		Pattern cookiePattern = Pattern.compile("([a-zA-Z0-9-_.]+)=([!-+--:<-~]+)(?:; )?");
		Matcher matcher = cookiePattern.matcher(raw);
		HttpCookies cookies = new HttpCookies();

		// Modify the request object cookie map by reference
		while (matcher.matches())
			cookies.put(matcher.group(1), matcher.group(2).trim());

		return cookies;
	}

	/**
	 * @see Map#get(Object)
	 */
	public String get(String key) {
		return cookies.get(key);
	}

	/**
	 * @see Map#put(Object, Object)
	 */
	public String put(String key, String value) {
		return cookies.put(key, value);
	}

}
