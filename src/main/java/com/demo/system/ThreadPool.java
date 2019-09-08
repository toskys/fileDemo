package com.demo.system;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//线程池和非线程池的区别
public class ThreadPool {

    public static int times = 100;//100,1000,10000

    public static ArrayBlockingQueue arrayWorkQueue = new ArrayBlockingQueue(1000);
    public static ExecutorService threadPool = new ThreadPoolExecutor(5, //corePoolSize线程池中核心线程数
            10,
            60,
            TimeUnit.SECONDS,
            arrayWorkQueue,
            new ThreadPoolExecutor.DiscardOldestPolicy()
    );
    public static void useThreadPool() {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            threadPool.execute(new Runnable() {
                public void run() {
                    System.out.println("说点什么吧...");
                }
            });
        }
        threadPool.shutdown();
        while (true) {
            if (threadPool.isTerminated()) {
                Long end = System.currentTimeMillis();
                System.out.println(end - start);
                break;
            }
        }
    }

    public static void createNewThread() {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {

            new Thread() {
                public void run() {
                    System.out.println("说点什么吧...");
                }
            }.start();
        }
        Long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    public static void main(String args[]) {
        createNewThread();
//        useThreadPool();
    }
}
