package se.mauritzz.kth.inet;

import se.mauritzz.kth.inet.http.HttpRequest;

import java.io.*;
import java.net.Socket;

public class Worker implements Runnable {
	Socket socket;


	public Worker(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		InputStream in = socket.getInputStream();
		OutputStream out = socket.getOutputStream();


	}

	public static HttpRequest capture(InputStream in) throws IOException {
		String line;
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		while ((line = reader.readLine()) != null)
			buffer.append(line);

		return HttpRequest.parseRequest(buffer.toString());
	}


}
