package se.mauritzz.kth.inet.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest extends GenericHttpPayload {

	private RequestType requestType;
	private String headerLine;
	private String path;
	private Map<String, String> queryString;
	private Map<String, String> cookies;

	private HttpRequest(RequestType requestType, String headerLine) {
		super();
		this.requestType = requestType;
		this.headerLine = headerLine;
		this.queryString = new HashMap<>();
		this.cookies = new HashMap<>();
	}

	/**
	 * Build
	 * @param raw
	 * @return
	 * @throws IOException
	 */
	public static HttpRequest parseRequest(String raw) throws IOException {
		// Check first line
		String[] request = raw.split("\r\n|\r|\n", 2);
		String headerLine = request[0];
		Pattern headerLinePattern = Pattern.compile("^(GET|HEAD|POST|PUT|DELETE) (.*) HTTP\\/1.1$");
		Matcher matcher = headerLinePattern.matcher(headerLine);

		if (!matcher.matches())
			throw new IOException("Invalid HTTP header line.");

		// Build request object from HTTP text data
		RequestType type = RequestType.fromString(matcher.group(1));
		String path = matcher.group(2);
		HttpRequest req = new HttpRequest(type, headerLine);
		req.path = path;
		req.parseHeaders(request[1]);

		return req;
	}

	private void parseHeaders(String raw) {
		// Find and parse HTTP headers
		Pattern headerPattern = Pattern.compile("^([a-zA-Z-]+): (.+)$");
		Matcher headers = headerPattern.matcher(raw);
		Map<String, String> httpHeaders = this.getHeaders();

		// Populate our header table - parse cookies when encountered
		while (headers.matches()) {
			if (headers.group(1).equals("Cookie"))
				this.parseCookies(headers.group(2));
			else
				httpHeaders.put(headers.group(1), headers.group(2));
		}
	}

	private void parseCookies(String raw) {
		// Find and match cookies according to HTTP spec
		Pattern cookiePattern = Pattern.compile("([a-zA-Z0-9-_]+)=([!-+--:<-~]+)(?:; )?");
		Matcher matcher = cookiePattern.matcher(raw);

		// Modify the request object cookie map by reference
		while (matcher.matches())
			cookies.put(matcher.group(1), matcher.group(2));
	}

	public String getPath() { return path; }
	public RequestType getRequestType() { return requestType; }
	public Map<String, String> getQueryString() { return queryString; }
	public Map<String, String> getCookies() { return cookies; }

	@Override
	public String getHeaderLine() { return headerLine; }
}
