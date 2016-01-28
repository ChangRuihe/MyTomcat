/*
 * 文 件 名:  SocketTest.java
 * 版    权:  Copyright © 2011-2014 深圳市房多多科技有限公司 All Rights Reserved
 * 编写人:  changruihe
 * 编 写 时 间:  2016年1月26日
 */
package com.chang.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author 13097
 * @since 2016年1月26日
 */
public class SocketTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            Socket so = new Socket("www.baidu.com", 80);
            OutputStream output = so.getOutputStream();
            boolean autoflush = true;
            PrintWriter out = new PrintWriter(output, autoflush);
            BufferedReader in = new BufferedReader(new InputStreamReader(so.getInputStream()));
            // send an HTTP request to the web server
            out.println("GET /index.html HTTP/1.1");
            out.println("Host: www.baidu.com:80");
            out.println("Connection:   Close");
            out.println();
            // read the response
            boolean loop = true;
            StringBuffer sb = new StringBuffer(8096);
            while (loop) {
                if (in.ready()) {
                    int i = 0;
                    while (i != -1) {
                        i = in.read();
                        sb.append((char) i);
                    }
                    loop = false;
                }
                Thread.currentThread();
                Thread.sleep(50);
            }
            System.out.println(sb);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("UnknownHostException e");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
