package com.weweb.io.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by wshen on 6/6/2017.
 */
public class Handler implements Runnable {
	private Socket socket;

	public Handler(Socket socket) {
		this.socket = socket;
	}

	PrintWriter out = null;
	BufferedReader in = null;

	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(),true);
			String requestText = in.readLine();
			System.out.println("requestText:" + requestText);
			String responseText = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM:ss"));
			out.println(responseText);
            System.out.println("send success");
        } catch (IOException e) {
			e.printStackTrace();
		}
	}
}
