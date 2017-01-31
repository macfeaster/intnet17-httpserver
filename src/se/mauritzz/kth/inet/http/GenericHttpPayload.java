package se.mauritzz.kth.inet.http;

import se.mauritzz.kth.inet.http.common.HttpCookies;
import se.mauritzz.kth.inet.http.common.HttpHeaders;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an HTTP request or response object, depending on subclass. Contains common functionality,
 * namely data structures and methods for handling mHeaders, as well as urlencoded data.
 */
public abstract class GenericHttpPayload {

	protected HttpHeaders headers;
	protected HttpCookies cookies;

	/**
	 * Fills a given Map with key-value pairs from a urlencoded String. Assumes the string contains nothing
	 * but urlencoded arguments (i.e. does not pre-process input data). Does not error on malformed input,
	 * simply ignores it. Keys can contain \w-matching characters, values anything but the &amp;-sign.
	 *
	 * @param keyMap        Map&lt;String, String&gt; to populate
	 * @param raw           Urlencoded string, without any characters (e.g. "?") in front
	 */
	protected void deserializeUrlEncoded(Map<String, String> keyMap, String raw) {
		Pattern pattern = Pattern.compile("(\\w+)=([^&]*)");
		Matcher matcher = pattern.matcher(raw);

		while (matcher.matches())
			keyMap.put(matcher.group(1), matcher.group(2));
	}

	public HttpHeaders getHeaders() { return headers; }
	public HttpCookies getCookies() { return cookies; }
}
