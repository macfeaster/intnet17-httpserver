package se.mauritzz.kth.inet.http.request;

import se.mauritzz.kth.inet.http.GenericHttpPayload;
import se.mauritzz.kth.inet.http.body.BodyType;
import se.mauritzz.kth.inet.http.body.HttpBody;

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
	private HttpBody body;

	private HttpRequest(RequestType requestType, String headerLine) {
		super();
		this.requestType = requestType;
		this.headerLine = headerLine;
		this.queryString = new HashMap<>();
		this.cookies = new HashMap<>();
	}

	/**
	 * Deserialize an HTTP text input string to an HttpRequest object.
	 * Checks HTTP header and only parses valid header and cookie data.
	 * Ignores all other input, regardless of its validity.
	 *
	 * @param raw           HTTP request text
	 * @return              HttpRequest object, de
	 * @throws IOException  On malformed HTTP input data
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

	/**
	 * {@inheritDoc}
	 *
	 * For HTTP client requests, incoming cookies are also parsed and stored.
	 */
	@Override
	protected void parseHeaders(String raw) {
		super.parseHeaders(raw);

		String cookies = getHeaders().get("Cookie");
		if (cookies.length() > 0)
			parseCookies(cookies);
	}

	private void parseCookies(String raw) {
		// Find and match cookies according to HTTP spec
		Pattern cookiePattern = Pattern.compile("([a-zA-Z0-9-_.]+)=([!-+--:<-~]+)(?:; )?");
		Matcher matcher = cookiePattern.matcher(raw);

		// Modify the request object cookie map by reference
		while (matcher.matches())
			cookies.put(matcher.group(1), matcher.group(2));
	}

	/*
	 * POST-specific logic
	 */

	public void parseBody() throws IOException {
		// Check request type
		if (requestType != RequestType.POST)
			throw new IOException("Only POST requests can have request bodies.");

		// Check content type
		BodyType bodyType = BodyType.fromString(getHeaders().get("Content-Type"));
		if (bodyType != BodyType.FORM_URLENCODED)
			throw new IOException("Only " + BodyType.FORM_URLENCODED.toString() + " body type allowed.");

		
	}

	public String getPath() { return path; }
	public RequestType getRequestType() { return requestType; }
	public Map<String, String> getQueryString() { return queryString; }
	public Map<String, String> getCookies() { return cookies; }

	@Override
	public String toString() {
		return "HttpRequest{" +
				"requestType=" + requestType +
				", headerLine='" + headerLine + '\'' +
				", path='" + path + '\'' +
				", queryString=" + queryString +
				", cookies=" + cookies +
				'}';
	}
}
