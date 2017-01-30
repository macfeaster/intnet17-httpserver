/*
 * Bootstrap.java
 *   _            _
 *  (_)          | |
 *   _ _ __   ___| |_
 *  | | '_ \ / _ \ __|
 *  | | | | |  __/ |_
 *  |_|_| |_|\___|\__|
 */

package se.mauritzz.kth.inet;

import se.mauritzz.kth.inet.util.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Bootstrap {

	/**
	 * Launch the server on designated port, or the default port 4000
	 *
	 * @param args  Command line args, first argument is port number
	 */
	public static void main(String[] args)
	{
		if (args.length != 1) {
			System.out.println("Usage: Server [port]");
			System.exit(-1);
		}

		// Configure the server and required instances
		Logger logger = Logger.getInstance();

		// Set up required variables
		int port = (Integer.parseInt(args[0]) > 0) ? Integer.parseInt(args[0]) : 4000;
		int maxConnections = 250;
		int i = 0;

		// Log startup
		logger.info("Bootstrapping server...")
				.info("Server starting on port " + port)
				.info("Maximum number of clients is " + maxConnections);

		try
		{
			// Open a ServerSocket to listen in on
			ServerSocket listener = new ServerSocket(port);
			Socket connection;

			// Unless we've reached the max number of simultaneous connections,
			// start a new thread to handle a request
			while (i++ < maxConnections)
			{
				// Set up structures
				connection = listener.accept();

				// Run connection handler
				Worker worker = new Worker();
				Thread thread = new Thread(worker);
				thread.start();
				logger.info("Thread " + thread.getId() + " assigned to incoming client " + connection.hashCode() + ".");
			}
		}
		catch (IOException e)
		{
			logger.severe("Exception on Socket listen in Bootstrap.java: " + e.getMessage());
			e.printStackTrace();
		}
	}
}