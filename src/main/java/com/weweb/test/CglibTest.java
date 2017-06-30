package com.weweb.test;

/**
 * Created by wshen on 6/6/2017.
 */
public class CglibTest {
	public static void main(String[] args) {
		CglibProxy proxy = new CglibProxy();
		// 通过生成子类的方式创建代理类
		SayHello proxyImp = (SayHello) proxy.getProxy(SayHello.class);
		proxyImp.say();
	}
}

class SayHello {
	public void say() {
		System.out.println("hello everyone");
	}
}