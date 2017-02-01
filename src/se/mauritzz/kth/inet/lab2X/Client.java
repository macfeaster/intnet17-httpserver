package se.mauritzz.kth.inet.lab2X;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple client that connects to the HTTP server and guesses numbers.
 */
public class Client {
	private static String baseUrl = "http://localhost:4000/";

	public static void main(String[] args) throws Exception {
		CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

		// Run 100 times
		int runs = 100;
		double sum = 0.0;
		int[] results = new int[runs];

		// Create new sessions and start guessing
		for (int i = 0; i < runs; i++) {
			Client client = new Client();
			client.sendGet();
			results[i] = client.makeGuesses();
			sum += results[i];
		}

		// Present average
		System.out.println(Arrays.toString(results));
		System.out.println("Average is: " + sum / 100);
	}

	// Continuously make guesses until the right number is found
	private int makeGuesses() throws Exception {
		Tuple guess = makeGuess(50);
		int i = 1;

		while (guess.x != guess.y) {
			int middle = (guess.x + guess.y) / 2;
			guess = makeGuess(middle);
			i++;
		}

		return i;
	}

	// Make a given guess and return the new range as a tuple
	// If the guess is correct, return a tuple where x = y
	private Tuple makeGuess(int guess) throws Exception {
		String res = sendPost("guess=" + guess);
		Pattern pattern = Pattern.compile("Make a new guess between <strong>(\\d+).* and <strong>(\\d+)");
		Matcher matcher = pattern.matcher(res);

		if (!matcher.find())
			return new Tuple(guess, guess);
		else
			return new Tuple(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
	}

	@SuppressWarnings("unused")
	private void sendGet() throws Exception {
		// Make a simple GET request to obtain the session cookie; ignores the result
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

		// Write the form POST data
		DataOutputStream out = new DataOutputStream(init.getOutputStream());
		out.writeBytes(data);
		out.flush();
		out.close();

		// Read the response
		BufferedReader in = new BufferedReader(new InputStreamReader(init.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = in.readLine()) != null)
			sb.append(line);

		// Return response as string
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
