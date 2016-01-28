/*
 * 文 件 名:  HTTPServer.java
 * 版    权:  Copyright © 2011-2014 深圳市房多多科技有限公司 All Rights Reserved
 * 编写人:  changruihe
 * 编 写 时 间:  2016年1月27日
 */
package com.chang.http.bootstrap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import com.chang.http.Request;
import com.chang.http.Response;

/**
 * @author 13097
 * @since 2016年1月27日
 */
public class HTTPServer {

    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
    {
        System.out.println("user.dir:" + WEB_ROOT);
    }
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    // the shutdow command received
    private boolean shutdown = false;

    public static void main(String[] args) {
        HTTPServer httpServer = new HTTPServer();
        httpServer.await();
    }

    public void await() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {

            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
            System.out.println("Server Start !!!");
        } catch (IOException e) {
            System.exit(1);
            System.out.println("监听服务出错！！！");
        }
        // Looop waiting for a request
        while (!shutdown) {
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;

            try {
                socket = serverSocket.accept();

                input = socket.getInputStream();
                output = socket.getOutputStream();

                Request requset = new Request(input);
                requset.parse();
                // create Request object and parse

                Response response = new Response(output);
                response.setRequset(requset);
                response.sendStaticResouce();
                // Close the socket
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                socket.close();

                // check if the previous URI is a shutdown command
                if (requset.getUri() != null) {
                    shutdown = requset.getUri().equals(SHUTDOWN_COMMAND);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
}
