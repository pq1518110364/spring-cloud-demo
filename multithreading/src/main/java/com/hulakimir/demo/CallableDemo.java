package com.hulakimir.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

class  MyCallable implements Callable<Integer> {


    @Override
    public Integer call() throws Exception {
        System.out.println("1234");
        return 111;
    }
}
/**
 * @author xiangwei
 * @date 2020-09-05 9:52 上午
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new MyCallable());
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }
}
