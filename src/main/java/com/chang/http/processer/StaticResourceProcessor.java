/*
 * 文 件 名:  StaticResourceProcessor.java
 * 版    权:  Copyright © 2011-2014 深圳市房多多科技有限公司 All Rights Reserved
 * 编写人:  changruihe
 * 编 写 时 间:  2016年1月28日
 */
package com.chang.http.processer;

import java.io.IOException;
import com.chang.http.Request;
import com.chang.http.Response;

/**
 * @author 13097
 * @since 2016年1月28日
 */
public class StaticResourceProcessor {

    public void process(Request requset, Response response) {
        try {
            response.sendStaticResouce();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("静态资源你发送失败");
        }
    }
}
