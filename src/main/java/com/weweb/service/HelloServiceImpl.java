package com.weweb.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jackshen on 2017/5/21.
 */
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {
    private AtomicInteger count=new AtomicInteger(0);
    public HelloServiceImpl() throws RemoteException {
    }

    @Override
    public String sayHello(String name) throws RemoteException {
        System.out.println("=============received================="+count.incrementAndGet());
        return String.format("Hello %s", name);
    }
}
