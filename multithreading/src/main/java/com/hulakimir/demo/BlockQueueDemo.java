package com.hulakimir.demo;


import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author xiangwei
 * @date 2020-09-04 5:17 下午
 */
public class BlockQueueDemo {

    public static void main(String[] args) throws InterruptedException {
//        ArrayBlockingQueue<String> arrayBlockingQueue =new ArrayBlockingQueue<>(2);
//        System.out.println(arrayBlockingQueue.add("a"));
//        System.out.println(arrayBlockingQueue.add("b"));
//        System.out.println(arrayBlockingQueue.add("c"));
//        System.out.println(arrayBlockingQueue.offer("d"));
        SynchronousQueue<String> strings=new SynchronousQueue<>();
        strings.put("12");
        strings.put("123");
        TimeUnit.SECONDS.sleep(1);
        new Thread(()->{
            try {
                System.out.println(strings.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
