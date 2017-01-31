package se.mauritzz.kth.inet.http.request;

import se.mauritzz.kth.inet.http.GenericHttpPayload;
import se.mauritzz.kth.inet.http.body.BodyType;
import se.mauritzz.kth.inet.http.body.FormRequestBody;
import se.mauritzz.kth.inet.http.body.HttpBody;
import se.mauritzz.kth.inet.http.common.HttpCookies;
import se.mauritzz.kth.inet.http.common.HttpHeaders;
import se.mauritzz.kth.inet.http.common.UrlEncodedData;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest extends GenericHttpPayload {

	private RequestType requestType;
	private String path;
	private UrlEncodedData queryString;
	private HttpBody body;

	private HttpRequest(RequestType requestType) {
		super();
		this.requestType = requestType;
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
		Pattern pattern = Pattern.compile("^(GET|HEAD|POST|PUT|DELETE) (.*) HTTP\\/1.1$");
		Matcher m = pattern.matcher(headerLine);

		// Validate HTTP header line
		if (!m.matches())
			throw new IOException("Invalid HTTP header line.");

		// Build request object from HTTP text data
		String[] url = m.group(2).split("[?]", 2);
		HttpRequest req = new HttpRequest(RequestType.fromString(m.group(1)));
		req.headers = HttpHeaders.deserialize(request[1]);
		req.path = url[0];

		// Parse query string
		if (url.length == 2)
			req.queryString = UrlEncodedData.deserialize(url[1]);

		// Parse request cookies
		String cookies = req.getHeaders().get("Cookie");
		if (cookies != null && cookies.length() > 0)
			req.cookies = HttpCookies.deserialize(cookies);

		return req;
	}

	/**
	 * Parse a given POST request body and add its content to this request object.
	 *
	 * @param raw               POST body data in text format
	 * @throws IOException      On disallowed body type, or if request is not POST
	 */
	public void parseBody(String raw) throws IOException {
		// Check request type
		if (requestType != RequestType.POST)
			throw new IOException("Only POST requests can have request bodies.");

		// Check content type
		BodyType bodyType = BodyType.fromString(getHeaders().get("Content-Type"));
		if (bodyType != BodyType.FORM_URLENCODED)
			throw new IOException("Only " + BodyType.FORM_URLENCODED.toString() + " body type allowed.");

		// Parse body text
		body = new FormRequestBody(raw);
	}

	public String getPath() { return path; }
	public RequestType getRequestType() { return requestType; }
	public UrlEncodedData getQueryString() { return queryString; }
	public HttpCookies getCookies() { return cookies; }
	public HttpBody getBody() { return body; }

	@Override
	public String toString() {
		return "HttpRequest{" +
				"requestType=" + requestType +
				", path='" + path + '\'' +
				", headers=" + getHeaders() +
				", queryString=" + queryString +
				", body=" + body +
				'}';
	}
}
