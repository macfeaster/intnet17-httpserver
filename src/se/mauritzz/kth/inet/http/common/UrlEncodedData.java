package se.mauritzz.kth.inet.http.common;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UrlEncodedData {

	private Map<String, String> data = new HashMap<>();

	/**
	 * Fills a given Map with key-value pairs from a urlencoded String. Assumes the string contains nothing
	 * but urlencoded arguments (i.e. does not pre-process input data). Does not error on malformed input,
	 * simply ignores it. Keys can contain \w-matching characters, values anything but the &amp;-sign.
	 *
	 * @param raw           Urlencoded string, without any characters (e.g. "?") in front
	 */
	public static UrlEncodedData deserialize(String raw) {
		Pattern pattern = Pattern.compile("(\\w+)=([^&]*)");
		Matcher matcher = pattern.matcher(raw);
		UrlEncodedData data = new UrlEncodedData();

		while (matcher.find())
			data.put(matcher.group(1), matcher.group(2));

		return data;
	}

	/**
	 * Serialize all parameters to a single string. Does not encode or modify parameter keys or values.
	 *
	 * @return              String formatted as URL scheme, e.g. param1=value1&param2=value2
	 */
	public String serialize() {
		return data.entrySet()
				.stream()
				.map(e -> e.getKey() + "=" + e.getValue())
				.collect(Collectors.joining("&"));
	}

	/**
	 * @see Map#get(Object)
	 */
	public String get(String key) {
		return data.get(key);
	}

	/**
	 * @see Map#put(Object, Object)
	 */
	public String put(String key, String value) {
		return data.put(key, value);
	}

	@Override
	public String toString() {
		return data.toString();
	}
}
