package se.mauritzz.kth.inet.http.body;

public abstract class HttpBody {

	private BodyType bodyType;
	public abstract String getBody();
}
