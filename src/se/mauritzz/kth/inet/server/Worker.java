package se.mauritzz.kth.inet.server;

import se.mauritzz.kth.inet.http.request.HttpRequest;
import se.mauritzz.kth.inet.http.request.RequestType;
import se.mauritzz.kth.inet.http.response.HttpResponse;
import se.mauritzz.kth.inet.http.response.ResponseType;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class Worker implements Runnable {
	Socket socket;

	public Worker(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			InputStream in = socket.getInputStream();
			HttpRequest req = capture(in);

			OutputStream out = socket.getOutputStream();


		} catch (IOException e) {
			try {
				OutputStream out = socket.getOutputStream();
				HttpResponse res = new HttpResponse(ResponseType.SERVER_ERROR);
				out.write(res.serialize().getBytes());
				out.flush();
				socket.close();
			} catch (IOException ex) {
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


}
