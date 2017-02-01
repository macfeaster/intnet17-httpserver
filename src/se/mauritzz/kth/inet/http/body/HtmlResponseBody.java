package se.mauritzz.kth.inet.http.body;

public class HtmlResponseBody extends HttpBody {

	String body;

	public HtmlResponseBody(String body) {
		super(BodyType.TEXT_HTML);
		this.body = body;
	}

	@Override
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void appendBody(String body) {
		this.body += body;
	}
}
