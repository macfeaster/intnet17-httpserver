package se.mauritzz.kth.inet.application;

import se.mauritzz.kth.inet.http.request.HttpRequest;
import se.mauritzz.kth.inet.http.response.HttpResponse;
import se.mauritzz.kth.inet.http.response.ResponseType;
import se.mauritzz.kth.inet.server.Process;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Application implements Process {

	@Override
	public HttpResponse doGet(HttpRequest request) throws IOException {
		Map<String, String> model = new HashMap<>();
		model.put("variable", "Variable :D");

		return new HttpResponse(ResponseType.OK, TemplateEngine.render("start.html", model));
	}

	@Override
	public HttpResponse doPost(HttpRequest request) throws IOException {
		return null;
	}
}
