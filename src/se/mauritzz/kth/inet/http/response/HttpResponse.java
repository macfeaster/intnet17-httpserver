package se.mauritzz.kth.inet.http.response;

import se.mauritzz.kth.inet.http.GenericHttpPayload;
import se.mauritzz.kth.inet.http.body.HtmlResponseBody;
import se.mauritzz.kth.inet.http.body.HttpBody;

public class HttpResponse extends GenericHttpPayload {

	private ResponseType responseType;

	public HttpResponse(ResponseType responseType) {
		this.responseType = responseType;
		this.body = new HtmlResponseBody("OK");
	}

	public HttpResponse(ResponseType responseType, HttpBody body) {
		this.responseType = responseType;
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
