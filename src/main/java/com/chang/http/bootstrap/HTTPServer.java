/*
 * 文 件 名:  HTTPServer.java
 * 版    权:  Copyright © 2011-2014 深圳市房多多科技有限公司 All Rights Reserved
 * 编写人:  changruihe
 * 编 写 时 间:  2016年1月27日
 */
package com.chang.http.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import com.chang.http.Request;
import com.chang.http.Response;
import com.chang.http.processer.ServletProcessorYoung;

/**
 * @author 13097
 * @since 2016年1月27日
 */
public class HTTPServer {

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
            System.out.println("监听服务出错！！！\n" + e.toString());
            System.exit(1);
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

                // check uri 判断它的请求资源类型 调用不同的处理器处理
                // a static resource
                // a request for a servlet begins with ("/servlet/")

                // dynamic
                String uri = requset.getUri();
                if (uri == null || uri == "") {
                    System.out.println("uri is empty");
                } else if (requset.getUri() != null && uri.startsWith("/servlet/")) {
                    ServletProcessorYoung processor = new ServletProcessorYoung();
                    processor.process(requset, response);
                } else {
                    // static
                    response.sendStaticResouce();
                }
                // Close the socket
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
