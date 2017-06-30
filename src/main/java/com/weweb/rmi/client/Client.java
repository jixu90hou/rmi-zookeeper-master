package com.weweb.rmi.client;

import com.weweb.service.HelloService;

/**
 * Created by jackshen on 2017/5/21.
 */

public class Client {

    public static void main(String[] args) throws Exception {
        ServiceConsumer consumer = new ServiceConsumer();

        while (true) {
            HelloService helloService = consumer.lookup();
            System.out.println("helloService:"+helloService);
            String result = helloService.sayHello("Jack");
            System.out.println(result);
            Thread.sleep(3000);
        }
    }
}