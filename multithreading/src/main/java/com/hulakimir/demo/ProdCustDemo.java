package com.hulakimir.demo;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiangwei
 * @date 2020-09-04 5:36 下午
 */

class ShareData{
    private int anInt=0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment(){
        lock.lock();
            try {
                while (anInt!=0) {
                    condition.await();
                }
                anInt++;
                condition.signal();
                System.out.println(Thread.currentThread().getName()+"---"+anInt);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
    }

    public void decrement(){
        lock.lock();
        try {
            while (anInt==0) {
                condition.await();
            }
            anInt--;
            condition.signal();
            System.out.println(Thread.currentThread().getName()+"---"+anInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

public class ProdCustDemo {

    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        new Thread(()->{
            for (int i = 0; i <5 ; i++) {
                shareData.increment();
            }
        },"A").start();
        new Thread(()->{
            for (int i = 0; i <5 ; i++) {
                shareData.decrement();
            }
        },"B").start();

        new Thread(()->{
            for (int i = 0; i <5 ; i++) {
                shareData.increment();
            }
        },"C").start();
        new Thread(()->{
            for (int i = 0; i <5 ; i++) {
                shareData.decrement();
            }
        },"D").start();
    }

}
