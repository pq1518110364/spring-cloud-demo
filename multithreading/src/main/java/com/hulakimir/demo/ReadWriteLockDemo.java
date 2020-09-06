package com.hulakimir.demo;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyCache{
    public volatile Map<String,Object> map = Maps.newHashMap();
    public ReentrantReadWriteLock readWriteLock=new ReentrantReadWriteLock();

        public void put(String key,String value){
            readWriteLock.writeLock().lock();
            try{
            System.out.println(Thread.currentThread().getName()+"正在写入。。。");
                map.put(key,value);
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName()+"写入完成。。。");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                readWriteLock.writeLock().unlock();
            }
        }

        public void get(String key){
            readWriteLock.readLock().lock();
            try {
                System.out.println(Thread.currentThread().getName()+"正在读。。。");
                Object r= map.get(key);
                System.out.println("result="+r.toString());
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                readWriteLock.readLock().unlock();
            }
            System.out.println(Thread.currentThread().getName()+"读完成。。。");
        }
}
/**
 * @author xiangwei
 * @date 2020-09-04 1:04 下午
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache  myCache= new MyCache();
        for (int i = 0; i < 5; i++) {
            final int tmp=i;
            new Thread(()->{
                myCache.put(tmp+"",tmp+"");
            },String.valueOf(i)).start();
        }
        for (int i = 0; i < 5; i++) {
            final int tmp=i;
            new Thread(()->{
                myCache.get(tmp+"");
            },String.valueOf(i)).start();
        }
    }
}
