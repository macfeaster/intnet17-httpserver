package se.mauritzz.kth.inet.server;

import se.mauritzz.kth.inet.application.Application;
import se.mauritzz.kth.inet.http.body.HtmlResponseBody;
import se.mauritzz.kth.inet.http.request.HttpRequest;
import se.mauritzz.kth.inet.http.request.RequestType;
import se.mauritzz.kth.inet.http.response.HttpResponse;
import se.mauritzz.kth.inet.http.response.ResponseType;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class Worker implements Runnable {
	private Socket socket;

	public Worker(Socket socket) {
		this.socket = socket;
	}

	/**
	 * Handle a request-response lifecycle.
	 */
	@Override
	public void run() {
		try {
			// Capture request and build response
			InputStream in = socket.getInputStream();
			HttpRequest req = capture(in);
			OutputStream out = socket.getOutputStream();
			HttpResponse res = handleRequest(req);

			// Send it back to the client
			out.write(res.serialize().getBytes());
			out.flush();
			socket.close();
		} catch (IOException e) {
			// Try to send 500 response
			try {
				// Build 500 response
				OutputStream out = socket.getOutputStream();
				HttpResponse res = new HttpResponse(ResponseType.SERVER_ERROR, new HtmlResponseBody(e.getMessage()));

				// Send it back to the client
				out.write(res.serialize().getBytes());
				out.flush();
				socket.close();
			} catch (IOException ex) {
				// If everything fails, just log it
				Logger.getGlobal().severe("Failed to send Server Error response: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}

	private HttpRequest capture(InputStream in) throws IOException {
		String line;
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		while ((line = reader.readLine()) != null) {
			if (line.length() == 0)
				break;

			buffer.append(line).append("\n");
		}

		// Parse the HTTP request and its headers
		HttpRequest req = HttpRequest.parseRequest(buffer.toString());

		if (req.getRequestType() == RequestType.POST) {

		}

		return req;
	}

	private HttpResponse handleRequest(HttpRequest req) throws IOException {
		Application app = new Application();

		switch (req.getRequestType()) {
			case GET:
				return app.doGet(req);
			case POST:
				return app.doPost(req);
			default:
				throw new IOException("HTTP method not implemented");
		}
	}


}
