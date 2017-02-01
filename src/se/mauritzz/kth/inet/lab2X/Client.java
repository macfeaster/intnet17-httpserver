package se.mauritzz.kth.inet.lab2X;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
	private static String baseUrl = "http://localhost:4000/";
	private static final String charset = StandardCharsets.UTF_8.name();

	public static void main(String[] args) throws Exception {
		Client client = new Client();
		CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
		client.sendGet();
		client.makeGuesses();
	}

	private void makeGuesses() throws Exception {
		makeGuess(50);
	}

	private Tuple makeGuess(int guess) throws Exception {
		String res = sendPost("guess=" + guess);
		Pattern pattern = Pattern.compile("Make a new guess between <strong>(\\d+)<\\/strong> and <strong>(\\d+)<\\/strong>");
		Matcher matcher = pattern.matcher(res);

		return new Tuple(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
	}

	@SuppressWarnings("unused")
	private void sendGet() throws Exception {
		HttpURLConnection init = (HttpURLConnection) new URL(baseUrl).openConnection();
		init.connect();
		InputStream response = init.getInputStream();
		init.disconnect();
	}

	private String sendPost(String data) throws Exception {
		// Set up POST request
		HttpURLConnection init = (HttpURLConnection) new URL(baseUrl).openConnection();
		init.setRequestMethod("POST");
		init.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		init.setFixedLengthStreamingMode(data.length());
		init.setDoOutput(true);

		DataOutputStream out = new DataOutputStream(init.getOutputStream());
		out.writeBytes(data);
		out.flush();
		out.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(init.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = in.readLine()) != null)
			sb.append(line);

		in.close();
		init.disconnect();
		return sb.toString();
	}

	private class Tuple {
		Tuple(int x, int y) {
			this.x = x;
			this.y = y;
		}

		int x;
		int y;
	}
}
