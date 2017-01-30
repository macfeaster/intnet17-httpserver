package se.mauritzz.kth.inet.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest extends GenericHttpPayload {

	public HttpRequest(RequestType requestType) {
		super(requestType);
	}

	public static HttpRequest capture(InputStream in) throws IOException {
		String line;
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		while ((line = reader.readLine()) != null)
			buffer.append(line);

		return parseRequest(buffer.toString());
	}

	private static HttpRequest parseRequest(String raw) throws IOException {
		// Check first line
		String[] request = raw.split("\r\n|\r|\n", 2);
		String first = request[0];
		Pattern firstPattern = Pattern.compile("^(GET|HEAD|POST|PUT|DELETE) (.*) HTTP\\/1.1$");
		Matcher matcher = firstPattern.matcher(first);

		if (!matcher.matches())
			throw new IOException("Invalid HTTP header line.");

		RequestType type = RequestType.fromString(matcher.group(1));
		String path = matcher.group(2);

		// Find and parse HTTP headers

	}
}
