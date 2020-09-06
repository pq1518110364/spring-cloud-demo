package com.hulakimir.demo;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author xiangwei
 * @date 2020-09-04 12:36 下午
 */
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference= new AtomicReference<>();

    public static void main(String[] args) {
        Executors.newCachedThreadPool();
        System.out.println(Runtime.getRuntime().availableProcessors());

        new Thread(()->{
            try {
                 new SpinLockDemo().myLock();
                TimeUnit.SECONDS.sleep(2);
                new SpinLockDemo().myUnLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"AA").start();

        try {
        TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
        new Thread(()->{
                new SpinLockDemo().myLock();
                new SpinLockDemo().myUnLock();
        },"BB").start();
    }

    public void myLock(){
        Thread thread= Thread.currentThread();
        System.out.println(thread.getName());
        while (atomicReference.compareAndSet(null,thread)){

        }
    }

    public void myUnLock(){
        Thread thread= Thread.currentThread();
        System.out.println(thread.getName());
        atomicReference.compareAndSet(thread,null);
    }
}
