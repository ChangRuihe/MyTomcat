/*
 * 文 件 名:  Constants.java
 * 版    权:  Copyright © 2011-2014 深圳市房多多科技有限公司 All Rights Reserved
 * 编写人:  changruihe
 * 编 写 时 间:  2016年1月28日
 */
package com.chang.http.constant;

import java.io.File;

/**
 * @author 13097
 * @since 2016年1月28日
 */
public class Constants {

    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
    {
        System.out.println("user.dir:" + WEB_ROOT);
    }
    public static final String ClassPath = System.getProperty("user.dir") + File.separator + "target" + File.separator;
}
