/*
 * 文 件 名:  SocketHelper.java
 * 版    权:  Copyright © 2011-2014 深圳市房多多科技有限公司 All Rights Reserved
 * 编写人:  changruihe
 * 编 写 时 间:  2016年1月27日
 */
package com.chang.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author 13097
 * @since 2016年1月27日
 */
public class SocketHelper {

    public static void PrintSocketInputInfo(InputStream inputStream) {
        StringBuffer sb = null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            // read the response
            boolean loop = true;
            sb = new StringBuffer(8096);
            while (loop) {

                if (in.ready()) {
                    int i = 0;
                    while (i != -1) {
                        i = in.read();
                        sb.append((char) i);
                    }
                    loop = false;
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(sb);
        return;

    }
}
