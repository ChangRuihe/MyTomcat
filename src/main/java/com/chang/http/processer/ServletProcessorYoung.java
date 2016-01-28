/*
 * 文 件 名:  ServletProcessYun.java
 * 版    权:  Copyright © 2011-2014 深圳市房多多科技有限公司 All Rights Reserved
 * 编写人:  changruihe
 * 编 写 时 间:  2016年1月28日
 */
package com.chang.http.processer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import com.chang.http.Request;
import com.chang.http.Response;
import com.chang.http.constant.Constants;

/**
 * @author 13097
 * @since 2016年1月28日
 */
public class ServletProcessorYoung {

    public void process(Request requset, Response response) {
        String uri = requset.getUri();
        String servlerName = "com.chang.http.servlet." + uri.substring(uri.lastIndexOf("/") + 1);
        URLClassLoader loader = null;
        try {
            // create a URLClassLoader
            URL[] urls = new URL[1];

            URLStreamHandler streamHandler = null;

            String classPATH = Constants.ClassPath;
            System.out.println("classPath:" + classPATH);
            File classPath = new File(classPATH);

            String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
            System.out.println("repository:" + repository);
            urls[0] = new URL(null, repository, streamHandler);
            loader = new URLClassLoader(urls);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("classLoader 加载失败！！");
        }
        Class myClass = null;
        try {
            myClass = loader.loadClass(servlerName);
        } catch (ClassNotFoundException e) {
            System.out.println("class:servlerName 无法找到" + e.toString());
            try {
                response.sendErroResouce("error.html");
            } catch (IOException e1) {
                System.out.print("class not found send error file is wrong !!" + e1.toString());
            }
        }

        Servlet servlet = null;
        try {
            servlet = (Servlet) myClass.newInstance();
            servlet.service((ServletRequest) requset, (ServletResponse) response);
        } catch (Exception e) {
            System.out.println(e.toString());
        } catch (Throwable e) {
            System.out.println(e.toString());
        }

    }
}
