package se.mauritzz.kth.inet.application;

import se.mauritzz.kth.inet.http.body.HtmlResponseBody;
import se.mauritzz.kth.inet.http.request.HttpRequest;
import se.mauritzz.kth.inet.http.response.HttpResponse;
import se.mauritzz.kth.inet.http.response.ResponseType;
import se.mauritzz.kth.inet.server.Process;

import java.io.IOException;

public class Application implements Process {

	@Override
	public HttpResponse doGet(HttpRequest request) throws IOException {
		HtmlResponseBody html = new HtmlResponseBody("");
		HttpResponse res = new HttpResponse(ResponseType.OK, html);

		return res;
	}

	@Override
	public HttpResponse doPost(HttpRequest request) throws IOException {
		return null;
	}
}
