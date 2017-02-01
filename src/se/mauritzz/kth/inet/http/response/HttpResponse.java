package se.mauritzz.kth.inet.http.response;

import se.mauritzz.kth.inet.http.GenericHttpPayload;
import se.mauritzz.kth.inet.http.body.HtmlResponseBody;
import se.mauritzz.kth.inet.http.body.HttpBody;
import se.mauritzz.kth.inet.http.common.HttpCookies;
import se.mauritzz.kth.inet.http.common.HttpHeaders;

public class HttpResponse extends GenericHttpPayload {

	private ResponseType responseType;

	public HttpResponse(ResponseType responseType) {
		this.headers = new HttpHeaders();
		this.cookies = new HttpCookies();
		this.responseType = responseType;
		this.body = new HtmlResponseBody("OK");
	}

	public HttpResponse(ResponseType responseType, HttpBody body) {
		this(responseType);
		this.body = body;
	}

	public String serialize() {
		// Fill in Content-Type header
		headers.put("Content-Type", body.getBodyType().toString());

		// Respond with header line, HTTP headers, all Set-Cookie headers and body
		return "HTTP/1.1 " + responseType.getCode() + " " + responseType.toString() + "\n" +
				headers.serialize() + "\n" +
				cookies.serialize() + "\n\n" +
				body.getBody();
	}
}
