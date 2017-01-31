package se.mauritzz.kth.inet.http.response;

import se.mauritzz.kth.inet.http.GenericHttpPayload;

public class HttpResponse extends GenericHttpPayload {

	private ResponseType responseType;

	public HttpResponse(ResponseType responseType) {
		this.responseType = responseType;
	}

	public String serialize() {
		// Respond with header line, HTTP headers, all Set-Cookie headers and body
		return "HTTP/1.1 " + responseType.getCode() + " " + responseType.toString() + "\n" +
				headers.serialize() + "\n" +
				cookies.serialize() + "\n\n" +
				body.getBody();
	}
}
