package se.mauritzz.kth.inet;

import java.io.InputStream;
import java.io.OutputStream;
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


}
