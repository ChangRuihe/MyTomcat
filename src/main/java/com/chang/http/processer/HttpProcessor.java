/*
 * 文 件 名:  HttpProcessor.java
 * 版    权:  Copyright © 2011-2014 深圳市房多多科技有限公司 All Rights Reserved
 * 编写人:  changruihe
 * 编 写 时 间:  2016年1月29日
 */
package com.chang.http.processer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import com.chang.container.Lifecycle;
import com.chang.http.Request;
import com.chang.http.Response;
import com.chang.http.connector.HttpConnector;

/**
 * @author 13097
 * @since 2016年1月29日
 */
public class HttpProcessor implements Lifecycle, Runnable {

    // the shutdow command received
    private boolean shutdown = false;
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    // 是否活跃 true 再请求中
    private boolean available = false;
    private Socket socket = null;
    private boolean stopped = false;
    private HttpConnector connector = null;

    public HttpProcessor(HttpConnector httpConnector) {
        connector = httpConnector;

    }

    public HttpProcessor(HttpConnector httpConnector, int i) {
        connector = httpConnector;
    }

    // 处理socket
    public void process(Socket socket) {

        InputStream input = null;
        OutputStream output = null;
        Request request = null;
        Response response = null;

        try {
            input = socket.getInputStream();
            request = new Request(input);
            request.parse();
            output = socket.getOutputStream();

            response = new Response(output);
            response.setRequset(request);
            String uri = request.getUri();
            System.out.println("current thread:" + Thread.currentThread().getName() + "request uri is:" + uri);
            if (uri == null || uri == "") {
                System.out.println("current thread:" + Thread.currentThread().getName() + "uri is empty and request MSG :" + request.returnRequestMSG());
            } else if (request.getUri() != null && uri.startsWith("/servlet/")) {
                ServletProcessorYoung processor = new ServletProcessorYoung();
                processor.process(request, response);
            } else {
                // static
                response.sendStaticResouce();
            }
            // Close the socket
            socket.close();
            // check if the previous URI is a shutdown command

            if (request.getUri() != null) {
                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
            }

        } catch (Exception e) {
        }
        try {
            output.close();
            // request.equals("");
        } catch (IOException e) {
        }
    }

    @Override
    public void start() {
        System.out.println("current thread:" + Thread.currentThread().getName() + "处理  初始化 一些配置  一些框架的东西 然后 new线程  启动线程");
        new Thread(this).start();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("a new httpProcessor is new !!!");
    }

    /**
     * Process an incoming TCP/IP connection on the specified socket. Any exception that occurs during processing must be logged and swallowed. <b>NOTE</b>: This method is called from our Connector's
     * thread. We must assign it to our own thread so that multiple simultaneous requests can be handled.
     * 
     * @param socket TCP socket to process
     */
    public synchronized void assign(Socket socket) {
        // Wait for the Processor to get the previous Socket
        System.out.println("current thread:" + Thread.currentThread().getName() + "assign func available is :" + available);
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        // Store the newly available Socket and notify our thread
        this.socket = socket;
        available = true;
        notifyAll();
    }

    /**
     * The background thread that listens for incoming TCP/IP connections and hands them off to an appropriate processor.
     */
    public void run() {
        System.out.println("httpProcessor is running  to await !!!!!!");
        // Process requests until we receive a shutdown signal
        while (!stopped) {
            // Wait for the next socket to be assigned
            Socket socket1 = await();
            if (socket == null) {
                System.out.println("socket is null");
                continue;
            }
            // Process the request from this socket
            try {
                System.out.println("current thread:" + Thread.currentThread().getName() + "process(socket1)");
                process(socket1);
            } catch (Throwable t) {
                System.out.println("current thread:" + Thread.currentThread().getName() + "process.invoke" + t + Thread.currentThread().getName());
            }
            // Finish up this request
            connector.recycle(this);
        }
        // Tell threadStop() we have shut ourselves down successfully
        // synchronized (this) {
        // this.notifyAll();
        // }
    }

    /**
     * Await a newly assigned Socket from our Connector, or <code>null</code> if we are supposed to shut down.
     */
    private synchronized Socket await() {
        // Wait for the Connector to provide a new Socket
        // await not available 要休眠等待

        // 刚开始的线程启动的时候 为false
        System.out.println("current thread:" + Thread.currentThread().getName() + "await () available:" + available);
        while (!available) {

            try {
                // 如果没有准备好就 wait(); 导致run方法的processor线程陷入 等待;
                wait();
            } catch (InterruptedException e) {
            }
        }
        // Notify the Connector that we have received this Socket
        Socket socket = this.socket;

        available = false;
        notifyAll();
        return (socket);
    }
}
