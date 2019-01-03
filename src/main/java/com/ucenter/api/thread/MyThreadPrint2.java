package com.ucenter.api.thread;

/**
 * Created by wangfeng on 2018/12/25.
 */
public class MyThreadPrint2 implements Runnable {

    private String name;

    private Object prev;

    private Object self;

    private MyThreadPrint2(String name, Object prev, Object self) {
        this.name = name;
        this.prev = prev;
        this.self = self;
    }

    @Override
    public void run() {
        int count = 10;
        while (count > 0) {
            synchronized (prev) {
                synchronized (self) {
                    System.out.println(name);
                    count--;
                    self.notify();
                }
                try {
                    prev.wait();                                                
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {

        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        MyThreadPrint2 printa = new MyThreadPrint2("A", c, a);
        MyThreadPrint2 printb = new MyThreadPrint2("B", a, b);
        MyThreadPrint2 printc = new MyThreadPrint2("C", b, c);

        new Thread(printa).start();
        Thread.sleep(100);
        new Thread(printb).start();
        Thread.sleep(100);
        new Thread(printc).start();
        Thread.sleep(100);


    }

}
