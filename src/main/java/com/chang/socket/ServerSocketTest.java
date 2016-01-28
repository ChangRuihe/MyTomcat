/*
 * 文 件 名:  ServerSocketTest.java
 * 版    权:  Copyright © 2011-2014 深圳市房多多科技有限公司 All Rights Reserved
 * 编写人:  changruihe
 * 编 写 时 间:  2016年1月26日
 */
package com.chang.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author 13097
 * @since 2016年1月26日
 */
public class ServerSocketTest {

    public static void main(String[] args) {
        try {
            InetAddress IAdress = InetAddress.getByName("127.0.0.1");
            ServerSocket serverSocket = new ServerSocket(8080, 1, IAdress);
            Socket sc = serverSocket.accept();
            sc.getOutputStream();
            sc.getInputStream();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }

    }
}
