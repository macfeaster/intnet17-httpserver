package se.mauritzz.kth.inet.http.response;

import se.mauritzz.kth.inet.http.GenericHttpPayload;

public class HttpResponse extends GenericHttpPayload {

	private ResponseType responseType;

	public HttpResponse(ResponseType responseType) {
		this.responseType = responseType;
	}

	public String serialize() {
		StringBuilder sb = new StringBuilder();

		// Append header line, HTTP headers, all Set-Cookie headers and body
		sb.append("HTTP/1.1 ").append(responseType.getCode()).append(" ").append(responseType.toString()).append("\n");
		sb.append(headers.serialize()).append("\n");
		sb.append(cookies.serialize()).append("\n").append("\n");
		sb.append(body.getBody());

		return sb.toString();
	}
}
