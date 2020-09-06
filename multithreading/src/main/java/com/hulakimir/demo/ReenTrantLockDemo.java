package com.hulakimir.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiangwei
 * @date 2020-09-04 6:41 下午
 */
class ShareResource {
    private int anInt = 0;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();


    public void print5(){
        lock.lock();

            try {
                while (anInt!=0) {
                    c1.await();
                }
                anInt=1;
                for (int i = 0; i <5 ; i++) {
                    System.out.println(Thread.currentThread().getName()+"---"+i);
                }
                c2.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

    }

    public void print10(){
        lock.lock();
            try {
                while (anInt!=1) {
                    c2.await();
                }
                anInt=2;
                for (int i = 0; i <10 ; i++) {
                    System.out.println(Thread.currentThread().getName()+"---"+i);
                }
                c3.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
    }

    public void print15(){

        lock.lock();
        try {
            while (anInt!=2) {
                c3.await();
            }
            anInt=1;
            for (int i = 0; i <15 ; i++) {
                System.out.println(Thread.currentThread().getName()+"---"+i);
            }
            c1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
public class ReenTrantLockDemo {

    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(() ->{
            for (int i = 0; i <5 ; i++) {
                shareResource.print5();
            }
        },"A").start();
        new Thread(() ->{
            for (int i = 0; i < 10; i++) {
                shareResource.print10();
            }
        },"B").start();
        new Thread(() ->{
            for (int i = 0; i < 15; i++) {
                shareResource.print15();
            }
        },"C").start();
    }
}
