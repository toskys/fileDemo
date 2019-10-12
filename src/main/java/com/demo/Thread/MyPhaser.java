package com.demo.Thread;

import java.util.concurrent.Phaser;

/**
 * Phaser，翻译为阶段，它适用于这样一种场景，一个大任务可以分为多个阶段完成，
 * 且每个阶段的任务可以多个线程并发执行，但是必须上一个阶段的任务都完成了才可以执行下一个阶段的任务。
 * 这种场景虽然使用CyclicBarrier或者CountryDownLatch也可以实现，但是要复杂的多。
 */
public class MyPhaser {

    private static final int PARTIES = 3;
    private static final int PHASES = 4;

    public static void main(String[] args) {
        Phaser phaser = new Phaser(PARTIES) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("=======phase: " + phase + " finished=============");
                switch (phase) {
                    case 0:
                        System.out.println("线程1");
                        break;
                    case 1:
                        System.out.println("线程2");
                        break;
                    case 2:
                        System.out.println("线程3");
                        break;
                    case 3:
                        System.out.println("线程4");
                        break;
                        default:
                            System.out.println("结束");
                }
                return super.onAdvance(phase, registeredParties);
            }
        };
        for (int i = 0; i < PARTIES; i++) {
            new Thread(()->{
                for (int j = 0; j < PHASES; j++) {
                    System.out.println(String.format("%s: phase: %d", Thread.currentThread().getName(), j));
                    phaser.arriveAndAwaitAdvance();
                }
            }, "Thread " + i).start();
        }
    }
}
