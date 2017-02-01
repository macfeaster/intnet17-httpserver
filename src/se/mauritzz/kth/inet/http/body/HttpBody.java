package se.mauritzz.kth.inet.http.body;

public abstract class HttpBody {

	private BodyType bodyType;
	public abstract String getBody();

	public HttpBody(BodyType bodyType) {
		this.bodyType = bodyType;
	}

	public BodyType getBodyType() {
		return bodyType;
	}
}
