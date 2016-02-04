/*
 * 文 件 名:  MyThread.java
 * 版    权:  Copyright © 2011-2014 深圳市房多多科技有限公司 All Rights Reserved
 * 编写人:  changruihe
 * 编 写 时 间:  2016年2月1日
 */
package com.chang.thread;

public class MyThread extends Thread {

    public MyThread(String name) {
        super(name);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++)
            new MyThread("thread" + i).start();
    }

    public void run() {
        for (int i = 0; i < 50; i++)
            System.out.println(this.getName() + ":" + i);
    }

}
