/*
 * 文 件 名:  MyThreadTarget.java
 * 版    权:  Copyright © 2011-2014 深圳市房多多科技有限公司 All Rights Reserved
 * 编写人:  changruihe
 * 编 写 时 间:  2016年2月1日
 */
package com.chang.thread;

/**
 * @author 13097
 * @since 2016年2月1日
 */
public class MyThreadTarget implements Runnable {

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            Runnable r = new MyThreadTarget();
            new Thread(r, "thread" + i).start();
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println(Thread.currentThread().getName() + ";" + i);
        }

    }

}
