package com.weweb.rmi.client;

import com.weweb.service.HelloService;

import java.rmi.Naming;

/**
 * Created by jackshen on 2017/5/21.
 */
public class RmiClient {
    public static void main(String[] args) throws Exception {
        String url = "rmi://localhost:1099/com.weweb.service.HelloServiceImpl";
        System.out.println("*********RMI Client begin******");
        HelloService helloService = (HelloService) Naming.lookup(url);
        String result = helloService.sayHello("Jack");
        System.out.println(result);
    }
}
