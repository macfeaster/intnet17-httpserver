package se.mauritzz.kth.inet.http.body;

public class PlainTextResponseBody extends HttpBody {

	private String body;

	public PlainTextResponseBody(String body) {
		super(BodyType.TEXT_PLAIN);
		this.body = body;
	}

	@Override
	public String getBody() {
		return body;
	}
}
