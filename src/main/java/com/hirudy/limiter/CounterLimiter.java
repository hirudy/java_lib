package com.hirudy.limiter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: Rudy Tan
 * Date: 2018/10/1
 *
 * 计数器limiter
 */
public class CounterLimiter extends RateLimiter {
    // 计数器
    private volatile int counter = 0;

    // 上次计数时间
    private volatile long lastTime = 0;

    // cas自旋锁
    private Lock lock = new ReentrantLock();

    public CounterLimiter(int rate) {
        super(rate);
    }

    public boolean tryAcquire() {
        lock.lock();
        try{
            // 是否已经过期了
            if (System.currentTimeMillis() - lastTime > 1000){
                counter = 0;
                lastTime = System.currentTimeMillis();
            }

            // 计数
            counter ++;

            // 是否超过速率
            if (counter <= rate){
                return true;
            }
        }finally {
            lock.unlock();
        }

        return false;
    }
}
