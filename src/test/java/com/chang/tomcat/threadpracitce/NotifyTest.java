/*
 * 文 件 名:  threadtest.java
 * 版    权:  Copyright © 2011-2014 深圳市房多多科技有限公司 All Rights Reserved
 * 编写人:  changruihe
 * 编 写 时 间:  2016年2月3日
 */
package com.chang.tomcat.threadpracitce;

public class NotifyTest {

    // private String flag = "true";
    private String flag[] = {
        "true"
    };

    class NotifyThread extends Thread {

        public NotifyThread(String name) {
            super(name);
        }

        public void run() {
            synchronized (flag) {
                flag[0] = "false";
                flag.notify();
                try {
                    sleep(100);// 推迟3秒钟通知
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    class WaitThread extends Thread {

        public WaitThread(String name) {
            super(name);
        }

        public void run() {

            System.out.println(getName() + "等待控制权");
            synchronized (flag) {
                while (flag[0] != "false") {
                    System.out.println(getName() + " begin waiting!");
                    long waitTime = System.currentTimeMillis();
                    try {
                        flag.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    waitTime = System.currentTimeMillis() - waitTime;
                    System.out.println("wait time :" + waitTime);
                }
                System.out.println(getName() + " end waiting!");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main Thread Run!");
        NotifyTest test = new NotifyTest();

        NotifyThread notifyThread = test.new NotifyThread("notify01");
        WaitThread waitThread01 = test.new WaitThread("waiter01");
        WaitThread waitThread02 = test.new WaitThread("waiter02");
        WaitThread waitThread03 = test.new WaitThread("waiter03");

        notifyThread.start();

        waitThread01.start();
        waitThread02.start();
        waitThread03.start();
    }

}
