package se.mauritzz.kth.inet.server;

import se.mauritzz.kth.inet.application.Application;
import se.mauritzz.kth.inet.http.body.PlainTextResponseBody;
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

			// Guard against dumb application code
			if (res == null)
				throw new IOException("HttpResponse cannot be null.");

			// Send it back to the client
			out.write(res.serialize().getBytes());
			out.flush();
			socket.close();
		} catch (IOException e) {
			// Try to send 500 response
			try {
				// Build 500 response
				OutputStream out = socket.getOutputStream();
				StringWriter error = new StringWriter();
				PrintWriter writer = new PrintWriter(error);
				e.printStackTrace(writer);
				HttpResponse res = new HttpResponse(ResponseType.SERVER_ERROR, new PlainTextResponseBody(error.toString()));

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
		StringBuilder buf = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		// Read all headers
		while ((line = reader.readLine()) != null) {
			if (line.length() == 0)
				break;
			buf.append(line).append("\n");
		}

		// Parse the HTTP request and its headers
		HttpRequest req = HttpRequest.parseRequest(buf.toString());

		// Read and parse request body
		if (req.getRequestType() == RequestType.POST) {
			int length = Integer.parseInt(req.getHeaders().get("Content-Length"));
			char[] buffer = new char[length];
			if (reader.read(buffer, 0, length) != -1)
				req.parseBody(new String(buffer));
		}

		return req;
	}

	private HttpResponse handleRequest(HttpRequest req) throws IOException {
		Application app = new Application();

		// Call different application methods depending on protocol
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
