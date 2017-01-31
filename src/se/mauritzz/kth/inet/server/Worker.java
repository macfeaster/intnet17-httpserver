package se.mauritzz.kth.inet.server;

import se.mauritzz.kth.inet.http.request.HttpRequest;

import java.io.*;
import java.net.Socket;

public class Worker implements Runnable {
	Socket socket;

	public Worker(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			HttpRequest req = capture(in);

			System.out.println(req);
		} catch (IOException e) {
			e.printStackTrace();
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
		return HttpRequest.parseRequest(buffer.toString());

		/* prep for POST
		if (req.getRequestType() == RequestType.POST) {
			if (req.getmHeaders().get("Content-Type"))
		}
		*/
	}


}
