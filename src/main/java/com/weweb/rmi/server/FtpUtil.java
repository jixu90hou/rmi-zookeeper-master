package com.weweb.rmi.server;

/**
 * Created by wshen on 6/5/2017.
 */

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jackshen on 2017/6/4.
 */
public class FtpUtil {

	public static void main(String[] args) {

		FTPClient ftpClient = new FTPClient();

		try {
			// 连接指定服务器，默认端口为21
			ftpClient.connect("10.3.1.222", 9921);

			System.out.println("connect to server");
			// 获取响应字符串（FTP服务器上可设置）
			String replyString = ftpClient.getReplyString();
			System.out.println("replyString: " + replyString);
			// 获取响应码用于验证是否连接成功
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				System.out.println("");
				System.exit(1);
			}
			// 设置链接编码，windows主机UTF-8会乱码，需要使用GBK或gb2312编码
			ftpClient.setControlEncoding("UTF-8");

			// 登录服务器
			boolean login = ftpClient.login("admin", "admin");
			if (login) {
				System.out.println("登录成功!");
			} else {
				System.out.println("登录失败!");
			}

			// 获取所有文件和文件夹的名字
			FTPFile[] files = ftpClient.listFiles();

			for (FTPFile file : files) {
				if (file.isDirectory()) {
					System.out.println(file.getName() + " 是文件夹");
				}
				if (file.isFile()) {
					System.out.println(file.getName() + " 是文件");
				}
			}
			String filePath = "/Users/jackshen/Desktop/test.png";
			filePath= "D://test/product1.png";

			// 生成InputStream用于上传本地文件
			ftpClient.enterLocalPassiveMode();
			InputStream in = new FileInputStream(filePath);

			// 上传文件
			boolean flag = ftpClient.storeFile("aaa.png", in);
			if (flag) {
				System.out.println("上传成功");
			}
			in.close();

			// 注销登录
			boolean logout = ftpClient.logout();
			if (logout) {
				System.out.println("注销成功!");
			} else {
				System.out.println("注销失败!");
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			// 关闭链接需要放在finally语句块中
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
