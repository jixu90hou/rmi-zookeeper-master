package com.weweb.io.socket1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wshen on 6/6/2017.
 */
public class SocketClientDemo {
	private static Executor executor = Executors.newFixedThreadPool(10);
	private static AtomicLong count=new AtomicLong(0);
	public static void main(String[] args) throws IOException, InterruptedException {
	    for(int i=0;i<12;i++){
            executor.execute(() -> {
                try {
                    send();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

	}

	public static void send() throws IOException {
		Socket socket = new Socket("127.0.0.1", 9091);
		System.out.println("client begin");
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		Long a=count.incrementAndGet();
        System.out.println("current number:"+a);
        out.println("client send request"+a);
		String responseText = in.readLine();
		System.out.println("responseText:" + responseText);

	}
}
