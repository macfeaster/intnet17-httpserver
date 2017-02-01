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
import java.util.Random;

public class Application implements Process {

	private Map<String, Session> sessionStore = new HashMap<>();

	@Override
	public HttpResponse doGet(HttpRequest req) throws IOException {
		if (!req.getPath().equals("/"))
			return new HttpResponse(ResponseType.NOT_FOUND, new PlainTextResponseBody("404: Resource " + req.getPath()));

		HttpResponse res = new HttpResponse(ResponseType.OK);
		Map<String, String> model = new HashMap<>();

		String sessionId = "S" + new Random().nextInt();
		Session session = new Session();
		sessionStore.put(sessionId, session);

		model.put("title", "Guess The Number");
		model.put("plopp", req.getQueryString().get("plopp"));
		model.put("sessionID", sessionId);

		res.getCookies().put("SessionID", sessionId);
		res.setBody(TemplateEngine.render("start", model));
		return res;
	}

	@Override
	public HttpResponse doPost(HttpRequest req) throws IOException {
		// Pick up session and form data
		String sessionID = req.getCookies().get("SessionID");
		Session session = sessionStore.get(sessionID);
		UrlEncodedData body = ((FormRequestBody) req.getBody()).getData();

		// Make a guess
		int guess = Integer.parseInt(body.get("guess"));
		boolean result = session.makeGuess(guess);

		Map<String, String> model = new HashMap<>();
		model.put("guess", "" + guess);
		model.put("count", "" + session.getCount());
		model.put("sessionID", sessionID);
		model.put("title", "You're Guessing...");

		if (result) {
			sessionStore.remove(sessionID);
			return new HttpResponse(ResponseType.OK, TemplateEngine.render("done", model));
		} else {
			int diff = session.compareTo(guess);
			if (diff > 0)
				model.put("message", "Too low!");
			else
				model.put("message", "Too high!");

			return new HttpResponse(ResponseType.OK, TemplateEngine.render("guess", model));
		}
	}
}
