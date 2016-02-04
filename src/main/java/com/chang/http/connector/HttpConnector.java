/*
 * 文 件 名:  HttpConnector.java
 * 版    权:  Copyright © 2011-2014 深圳市房多多科技有限公司 All Rights Reserved
 * 编写人:  changruihe
 * 编 写 时 间:  2016年1月29日
 */
package com.chang.http.connector;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;
import java.util.Vector;
import com.chang.container.Lifecycle;
import com.chang.http.processer.HttpProcessor;

public class HttpConnector implements Runnable {

    boolean stopped;
    private String scheme = "http";

    private int curProcessors = 0;

    private Vector<HttpProcessor> created = new Vector<HttpProcessor>();

    private Stack<HttpProcessor> processors = new Stack<HttpProcessor>();

    private int maxProcessors = 20;
    private int minProcessors = 5;

    public String getScheme() {
        return scheme;
    }

    public void start() {
        System.out.println("server starting !!!  new a thread to deal request");
        Thread thread = new Thread(this);

        while (curProcessors < minProcessors) {
            if ((maxProcessors > 0) && (curProcessors >= maxProcessors)) {
                break;
            }
            HttpProcessor processor = newProcessor();
            recycle(processor);
        }
        thread.start();
        System.out.println("httpconnector 启动; httpbootstrap 启动线程 退出");
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            System.exit(1);
        }
        while (!stopped) {
            // Accept the next incoming connection from the server socket
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (Exception e) {
                continue;
            }
            HttpProcessor processor = createProcessor();
            if (processor == null) {
                try {
                    // log(sm.getString("httpConnector.noProcessor"));
                    socket.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
                continue;
            }
            processor.assign(socket);
        }
    }

    private HttpProcessor newProcessor() {
        HttpProcessor processor = new HttpProcessor(this, curProcessors++);
        if (processor instanceof Lifecycle) {
            try {
                ((Lifecycle) processor).start();
            } catch (Exception e) {
                System.out.println("newProcessor" + e);
                return (null);
            }
        }
        created.addElement(processor);
        return (processor);
    }

    private HttpProcessor createProcessor() {
        synchronized (processors) {
            if (processors.size() > 0) {
                return ((HttpProcessor) processors.pop());
            }
            if ((maxProcessors > 0) && (curProcessors < maxProcessors)) {
                return (newProcessor());
            } else {
                if (maxProcessors < 0) {
                    return (newProcessor());
                } else {
                    return (null);
                }
            }
        }
    }

    public void recycle(HttpProcessor item) {
        System.out.println("the processor is push to stack :recycle function is :" + Thread.currentThread().getName());
        processors.push(item);
    }
}
