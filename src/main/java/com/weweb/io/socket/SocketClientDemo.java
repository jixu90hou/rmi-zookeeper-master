package com.weweb.io.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by wshen on 6/6/2017.
 */
public class SocketClientDemo {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket=new Socket("127.0.0.1",9011);
        System.out.println("client begin");
        BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
        out.println("client send request");
        String responseText=in.readLine();
        System.out.println("responseText:"+responseText);
    }
}
