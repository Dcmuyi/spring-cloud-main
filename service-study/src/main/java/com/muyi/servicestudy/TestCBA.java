package com.muyi.servicestudy;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Muyi
 * 顺序打印ABC
 * @date 2019-04-17.
 */
@Slf4j
public class TestCBA {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition conditionA = lock.newCondition();
    private static Condition conditionB = lock.newCondition();
    private static Condition conditionC = lock.newCondition();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor thread = new ThreadPoolExecutor(3,3,20, TimeUnit.MINUTES, new LinkedBlockingQueue<>());

        thread.execute(()->{
            for (int i=0; i< 3;) {
                try {
                    lock.lock();
                    log.info("a wait");
                    conditionA.await();
                    System.out.println("A");
                    conditionB.signal();
                    i++;
                } catch (InterruptedException e) {
                    log.info("catch A exception"+e.getMessage());
                } finally {
                    log.info("A unlock");
                    lock.unlock();
                }
            }
        });
        thread.execute(()->{
            for (int i=0; i< 3;) {
                try {
                    lock.lock();
                    log.info("B wait");
                    conditionB.await();
                    System.out.println("B");
                    conditionC.signal();
                    i++;
                } catch (InterruptedException e) {
                    log.info("catch B exception"+e.getMessage());
                } finally {
                    log.info("B unlock");
                    lock.unlock();
                }
            }
        });

        thread.execute(()->{
            for (int i=0; i< 3;) {
                try {
                    lock.lock();
                    log.info("C wait");
                    conditionC.await();
                    System.out.println("C");
                    conditionA.signal();
                    i++;
                } catch (InterruptedException e) {
                    log.info("catch C exception"+e.getMessage());
                } finally {
                    log.info("C unlock");
                    lock.unlock();
                }
            }
        });

        try {
            log.info("sleep");
            Thread.sleep(5000);
        } catch (Exception ignore) {

        }

        try {
            lock.lock();
            conditionC.signal();
        } finally {
            log.info("C unlock");
            lock.unlock();
        }

        return;

    }
}
