/*
 * 文 件 名:  ThreadProject.java
 * 版    权:  Copyright © 2011-2014 深圳市房多多科技有限公司 All Rights Reserved
 * 编写人:  changruihe
 * 编 写 时 间:  2016年2月2日
 */
package com.chang.thread;

public class ThreadProject {

    public static void main(String[] args) {
        Object obj = new Object();
        // try {
        // synchronized (obj) {
        // obj.wait();
        // obj.notify();
        // }
        obj.notify();
        Thread thread = Thread.currentThread();
        try {

            ThreadLocal lo;
            for (int i = 0; i <= 100; i++) {
                thread.sleep(100L);
                System.out.println(i + "\t\tllllll");
                // thread.suspend();
                // thread.resume();
                // thread.join();
                //
                // thread.interrupt();
                // thread.stop();
                if (i == 50) {
                    System.exit(1);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public synchronized void stsdfj() {
    }
}
