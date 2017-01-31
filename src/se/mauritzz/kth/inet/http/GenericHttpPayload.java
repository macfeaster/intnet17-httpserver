package se.mauritzz.kth.inet.http;

import se.mauritzz.kth.inet.http.body.HttpBody;
import se.mauritzz.kth.inet.http.common.HttpCookies;
import se.mauritzz.kth.inet.http.common.HttpHeaders;

/**
 * Represents an HTTP request or response object, depending on subclass. Contains common functionality,
 * namely data structures and methods for handling headers, as well as urlencoded data.
 */
public abstract class GenericHttpPayload {

	protected HttpHeaders headers;
	protected HttpCookies cookies;
	protected HttpBody body;

	public HttpHeaders getHeaders() { return headers; }
	public HttpCookies getCookies() { return cookies; }
	public HttpBody getBody() { return body; }
}
