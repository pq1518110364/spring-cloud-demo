package com.bishop.common.utils;

import java.util.concurrent.*;

/**
 * 1.任务独立。 如何任务依赖于其他任务，那么可能产生死锁。
 *  例如某个任务等待另一个任务的返回值或执行结果，那么除非线程池足够大，否则将发生线程饥饿死锁。
 *
 * 2.合理配置阻塞时间过长的任务。 如果任务阻塞时间过长，那么即使不出现死锁，线程池的性能也会变得很糟糕。
 *  在Java并发包里可阻塞方法都同时定义了限时方式和不限时方式。 例如
 *  Thread.join,BlockingQueue.put, CountDownLatch.await等，如果任务超时，
 *  则标识任务失败，然后中止任务或者将任务放回队列以便随后执行，这样，无论任务的最终结果是否成功，这种办法都能够保证任务总能继续执行下去。
 *
 * 3.设置合理的线程池大小。 只需要避免过大或者过小的情况即可，上文的公式线程池大小=NCPU *UCPU(1+W/C)。
 *
 * 4.选择合适的阻塞队列。
 * newFixedThreadPool和newSingleThreadExecutor都使用了无界的阻塞队列，无界阻塞队列会有消耗很大的内存，
 * 如果使用了有界阻塞队列，它会规避内存占用过大的问题， 但是当任务填满有界阻塞队列，新的任务该怎么办？
 * 在使用有界队列是，需要选择合适的拒绝策略，队列的大小和线程池的大小必须一起调节。
 * 对于非常大的或者无界的线程池，可以使用SynchronousQueue来避免任务排队，以直接将任务从生产者提交到工作者线程
 *
 * @Author: bishop
 * Create by Poseidon on 2019-04-21
 */
@SuppressWarnings("all")
public class ThreadPoolUtil<T> {
    /**
     * private static final int RUNNING    = -1 << COUNT_BITS;
     *     private static final int SHUTDOWN   =  0 << COUNT_BITS;
     *     private static final int STOP       =  1 << COUNT_BITS;
     *     private static final int TIDYING    =  2 << COUNT_BITS;
     *     private static final int TERMINATED =  3 << COUNT_BITS;
     */

    /**
     * 根据cpu的数量动态的配置核心线程数和最大线程数
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    /**
     * 核心线程数 = CPU核心数 + 1
     */
    private static final int CORE_POOL_SIZE    = CPU_COUNT + 1;
    /**
     * 线程池最大线程数 = CPU核心数 * 2 + 1
     */
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    /**
     * 非核心线程闲置时超时1s
     */
    private static final int KEEP_ALIVE = 1;
    /**
     *  线程池的对象
     */
    private ThreadPoolExecutor executor;

    /**
     * 要确保该类只有一个实例对象，避免产生过多对象消费资源，所以采用单例模式
     */
    private ThreadPoolUtil() {
    }

    private static class ThreadPoolInstant{
        private static final ThreadPoolUtil THREAD_POOL_UTIL = new ThreadPoolUtil();
    }

    public static ThreadPoolUtil getInstance(){
        return ThreadPoolInstant.THREAD_POOL_UTIL;
    }

    /**
     * 开启一个无返回结果的线程
     * @param r
     */
    public void execute(Runnable r) {
        if (executor == null) {
            /**
             * corePoolSize:核心线程数
             * maximumPoolSize：线程池所容纳最大线程数(workQueue队列满了之后才开启)
             * keepAliveTime：非核心线程闲置时间超时时长
             * unit：keepAliveTime的单位
             * workQueue：等待队列，存储还未执行的任务
             * threadFactory：线程创建的工厂
             * handler：异常处理机制
             *
             */
            executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                    KEEP_ALIVE, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20),
                    Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
        }
        // 把一个任务丢到了线程池中
        executor.execute(r);
    }

    /**
     * 开启一个有返回结果的线程
     * @param r
     * @return
     */
    public Future<T> submit(Callable<T> r) {
        if (executor == null) {
            /**
             * corePoolSize:核心线程数
             * maximumPoolSize：线程池所容纳最大线程数(workQueue队列满了之后才开启)
             * keepAliveTime：非核心线程闲置时间超时时长
             * unit：keepAliveTime的单位
             * workQueue：等待队列，存储还未执行的任务
             * threadFactory：线程创建的工厂
             * handler：异常处理机制
             *
             */
            executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                    KEEP_ALIVE, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20),
                    Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        }
        // 把一个任务丢到了线程池中
        return executor.submit(r);
    }

    /**
     * 把任务移除等待队列
     * @param r
     */
    public void cancel(Runnable r) {
        if (r != null) {
            executor.getQueue().remove(r);
        }
    }


}
