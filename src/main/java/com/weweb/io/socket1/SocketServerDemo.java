package com.weweb.io.socket1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;

/**
 * Created by wshen on 6/6/2017.
 */
public class SocketServerDemo {
	public static void main(String[] args) throws IOException, InterruptedException {
		TimeServerHandlerExecutePool executor = new TimeServerHandlerExecutePool(50, 10000);
		ServerSocket serverSocket = new ServerSocket(9091);
		Socket socket;
		System.out.println("======socket begin======");
		while (true) {
			socket = serverSocket.accept();
			final Socket finalSocket = socket;
			executor.execute(() -> {
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

class TimeServerHandlerExecutePool {
	private ExecutorService executor;

	public TimeServerHandlerExecutePool(int maxPoolSize, int queueSize) {
		executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L,
				TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize));
	}

	public void execute(Runnable task) {
		executor.execute(task);
	}
}