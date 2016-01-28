/*
 * 文 件 名:  Response.java
 * 版    权:  Copyright © 2011-2014 深圳市房多多科技有限公司 All Rights Reserved
 * 编写人:  changruihe
 * 编 写 时 间:  2016年1月27日
 */
package com.chang.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import com.chang.http.bootstrap.HTTPServer;

/*
 * HTTP Response = Status-Line
 * ((general-header | response-header |entity-header) CRLF)
 * CRLG
 * [message-body]
 * Status-Line = HTTP-Version SP status-code SP Reasion-Phrase CRLF
 */

public class Response {

    private static final int BUFFER_SIZE = 256;
    Request request;
    OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequset(Request requset) {
        this.request = requset;
    }

    public void sendStaticResouce() throws IOException {
        // System.out.println("sendStaticResouce is startting !!");
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            String uri = request.getUri();
            uri = "\\" + uri.substring(1);
            File file = new File(HTTPServer.WEB_ROOT, uri);
            // System.out.println("out put file is" + uri);
            if (file.exists()) {
                // System.out.println("file 存在" + HTTPServer.WEB_ROOT + uri);
                fis = new FileInputStream(file);
                int ch = fis.read(bytes, 0, BUFFER_SIZE);
                while (ch != -1) {
                    output.write(bytes, 0, ch);
                    ch = fis.read(bytes, 0, BUFFER_SIZE);
                }

            } else {
                // System.out.println("file 不存在" + HTTPServer.WEB_ROOT + uri);
                // file not found
                String errorMessage = "HTTP/1.1 404 File Not Found \r\n" + "Connection: Close\r\n" + "Content-Type: text/html\r\n" + "Content-Length: 23\r\n" + "\r\n" + "<h1>File Not Found</h1>";
                output.write(errorMessage.getBytes());
            }

        } catch (Exception e) {
            // thrown if cannot instantiate a File object
            System.out.println(e.toString());
        } finally {
            if (fis != null) {
                fis.close();
            }

        }

    }
}
