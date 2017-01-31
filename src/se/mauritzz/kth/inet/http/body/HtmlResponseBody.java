package se.mauritzz.kth.inet.http.body;

public class HtmlResponseBody extends HttpBody {

	String body;

	public HtmlResponseBody(String body) {
		this.body = body;
	}

	@Override
	public String getBody() {
		return body;
	}
}
