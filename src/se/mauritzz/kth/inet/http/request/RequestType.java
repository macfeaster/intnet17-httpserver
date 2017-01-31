package se.mauritzz.kth.inet.http.request;

public enum RequestType {
	GET,
	HEAD,
	POST,
	PUT,
	DELETE;

	public static RequestType fromString(String input) {
		switch (input.trim()) {
			case "GET":
				return GET;
			case "HEAD":
				return HEAD;
			case "POST":
				return POST;
			case "PUT":
				return PUT;
			case "DELETE":
				return DELETE;
			default:
				return GET;
		}
	}
}
