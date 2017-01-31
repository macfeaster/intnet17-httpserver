package se.mauritzz.kth.inet.http;

import java.util.HashMap;
import java.util.Map;

public abstract class GenericHttpPayload {
	private Map<String, String> headers;

	public GenericHttpPayload() {
		this.headers = new HashMap<>();
	}

	public abstract String getHeaderLine();

	public Map<String, String> getHeaders() {
		return headers;
	}
}
