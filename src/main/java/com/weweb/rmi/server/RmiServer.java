package com.weweb.rmi.server;

import com.weweb.service.HelloServiceImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * Created by jackshen on 2017/5/21.
 */
public class RmiServer {
    public static void main(String[] args) throws Exception {
        int port = 1099;
        String url = "rmi://localhost:1099/com.weweb.service.HelloServiceImpl";
        System.out.println("*********RMI Server begin******");
        LocateRegistry.createRegistry(port);
        Naming.rebind(url, new HelloServiceImpl());
    }
}
