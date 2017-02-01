package se.mauritzz.kth.inet.application;

import se.mauritzz.kth.inet.http.body.FormRequestBody;
import se.mauritzz.kth.inet.http.body.PlainTextResponseBody;
import se.mauritzz.kth.inet.http.common.UrlEncodedData;
import se.mauritzz.kth.inet.http.request.HttpRequest;
import se.mauritzz.kth.inet.http.response.HttpResponse;
import se.mauritzz.kth.inet.http.response.ResponseType;
import se.mauritzz.kth.inet.server.Process;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Application implements Process {

	@Override
	public HttpResponse doGet(HttpRequest req) throws IOException {
		Map<String, String> model = new HashMap<>();
		model.put("title", "Guess The Number");
		model.put("plopp", req.getQueryString().get("plopp"));

		return new HttpResponse(ResponseType.OK, TemplateEngine.render("start", model));
	}

	@Override
	public HttpResponse doPost(HttpRequest req) throws IOException {
		UrlEncodedData body = ((FormRequestBody) req.getBody()).getData();
		String guess = body.get("guess");
		return new HttpResponse(ResponseType.OK, new PlainTextResponseBody("You guessed " + guess));
	}
}
