package com.weweb.io.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by wshen on 6/6/2017.
 */
public class TimeSocketServer {
	public static Boolean flag=new Boolean(true);
	public static void main(String[] args) throws IOException, InterruptedException {

		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		System.out.println("======server begin=====");
		ServerSocket serverSocket = new ServerSocket(9000);
		while (true) {
				socket = serverSocket.accept();
				//Thread.sleep(1000);
		/*		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream());
				String requestText = in.readLine();
				String responseText = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM:ss"));
				System.out.println("receive response:" + requestText);
				out.println(responseText);*/
				new Thread(new Handler(socket)).start();


		}
	}
}
