package com.weweb.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by jackshen on 2017/5/21.
 */
public interface HelloService extends Remote {

    String sayHello(String name) throws RemoteException;
}