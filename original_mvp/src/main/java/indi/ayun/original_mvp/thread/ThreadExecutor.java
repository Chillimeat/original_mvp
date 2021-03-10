package indi.ayun.original_mvp.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadExecutor {
    private ExecutorService service;

    //单例模式
    private static ThreadExecutor instance;

    /**
     * 单列堆栈集合对象
     * @return ActivityMgr 单列堆栈集合对象
     */
    public static ThreadExecutor getInstance() {
        if (instance == null) {
            synchronized (ThreadExecutor.class){
                if (instance == null) {
                    instance = new ThreadExecutor();
                }
            }
        }
        return instance;
    }

    public void setCachedExecutor(){
        service= Executors.newCachedThreadPool();
    }

    public void setSingleExecutor(){
        service= Executors.newSingleThreadExecutor();
    }

    public void setFixedExecutor(int nThreads){
        service= Executors.newFixedThreadPool(nThreads);
    }

    public void setScheduledExecutor(int corePoolSize, ThreadFactory threadFactory){
        service= Executors.newScheduledThreadPool(corePoolSize,threadFactory);
    }

    public void setExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime){
        service = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5));
    }

    public void addCommand(Runnable command){
        service.execute(command);
        //MLog.out("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+ executor.getQueue().size()+"，已完成的任务数目："+executor.getCompletedTaskCount());
    }

    public void shutdown(){
        service.shutdown();
    }

    public void shutdownNow(){
        service.shutdownNow();
    }
}
