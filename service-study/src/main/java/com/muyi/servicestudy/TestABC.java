package com.muyi.servicestudy;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Muyi
 * 顺序打印ABC
 * @date 2019-04-17.
 */
@Slf4j
public class TestABC {
    private static ReentrantLock lock = new ReentrantLock();
    private static int stat = 0;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor thread = new ThreadPoolExecutor(3,3,20, TimeUnit.MINUTES, new LinkedBlockingQueue<>());




        thread.execute(()->{
            for (int i=0; i< 10;) {
                try {
                    lock.lock();
                    if (stat % 3 == 2) {
                        System.out.println("A");
                        stat++;
                        i++;
                    }
                } finally {
                    lock.unlock();
                }
            }
        });
        thread.execute(()->{
            for (int i=0; i< 10;) {
                try {
                    lock.lock();
                    if (stat % 3 == 0) {
                        System.out.println("C");
                        stat++;
                        i++;
                    }
                } finally {
                    lock.unlock();
                }
            }
        });

        thread.execute(()->{
            for (int i=0; i< 10;) {
                try {
                    lock.lock();
                    if (stat % 3 == 1) {
                        System.out.println("B");
                        stat++;
                        i++;
                    }
                } finally {
                    lock.unlock();
                }
            }
        });
    }
}
