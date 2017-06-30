package com.weweb.io.socket;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by wshen on 6/6/2017.
 */
public class TimeSocketClient {
	public static void send() throws IOException {
		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			socket = new Socket("127.0.0.1", 9000);

			System.out.println("socket content begin");

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("socket request-->");
			System.out.println("Send order 2 server succeed.");
			String responseText = in.readLine();
			System.out.println("response:" + responseText);

		} finally {
			System.out.println("关闭");
			if (socket != null) {
				socket.close();
			}
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
		}
	}
	private static Executor executor = Executors.newFixedThreadPool(100);

	public static void main(String[] args) throws IOException {
		for(int i=0;i<10000;i++){
			executor.execute(() -> {
				try {
					send();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
	}
}
