package com.weweb.io.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wshen on 6/6/2017.
 */
public class SocketServerDemo {
	public static void main(String[] args) throws IOException, InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		ServerSocket serverSocket = new ServerSocket(9011);
		Socket socket;
		System.out.println("======socket begin======");
		while (true) {
			socket = serverSocket.accept();
			final Socket finalSocket = socket;
			executor.submit(() -> {
				try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(finalSocket.getInputStream()));
					PrintWriter out = new PrintWriter(finalSocket.getOutputStream(), true);
					String requestText = in.readLine();
					System.out.println("receive request content:" + requestText);
					out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM:ss")));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

		}
		// 关闭操作
	}
}
