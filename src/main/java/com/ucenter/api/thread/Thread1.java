package com.ucenter.api.thread;

/**
 * Created by wangfeng on 2018/12/25.
 */
public class Thread1 extends Thread {

    private String name;

    public Thread1(String name) {
        this.name = name;
    }

    public void run() {
        System.out.println("run thead1 " + name + ":start");
        for (int i = 0; i <= 50; i++) {
            System.out.println("run thead1 " + name + ":" + i);
            if (i == 30) {
                this.yield();
            }
            try {
                sleep((int) Math.random() * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }
        System.out.println("run thead1 " + name + ":end");
    }

    public static void main(String[] args) {
        Thread1 mTh1 = new Thread1("A");
        Thread1 mTh2 = new Thread1("B");
        mTh1.start();
        mTh2.start();
        try {
            mTh1.join();
            mTh2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("run main end ");
    }

}
