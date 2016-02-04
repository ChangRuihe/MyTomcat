/*
 * 文 件 名:  BootStrap.java
 * 版    权:  Copyright © 2011-2014 深圳市房多多科技有限公司 All Rights Reserved
 * 编写人:  changruihe
 * 编 写 时 间:  2016年1月29日
 */
package com.chang.http.bootstrap.startup;

import com.chang.http.connector.HttpConnector;

/**
 * @author 13097
 * @since 2016年1月29日
 */
public class BootStrap {

    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        connector.start();
    }
}
