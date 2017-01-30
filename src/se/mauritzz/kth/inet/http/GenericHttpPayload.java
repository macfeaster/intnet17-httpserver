package se.mauritzz.kth.inet.http;

import java.util.HashMap;
import java.util.Map;

public class GenericHttpPayload {
	private RequestType requestType;
	private Map<String, String> headers;
	private Map<String, String> cookies;

	public GenericHttpPayload(RequestType requestType) {
		this.requestType = requestType;
		this.headers = new HashMap<>();
		this.cookies = new HashMap<>();
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public Map<String, String> getCookies() {
		return cookies;
	}
}
