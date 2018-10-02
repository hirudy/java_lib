package com.hirudy.limiter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: Rudy Tan
 * Date: 2018/10/1
 *
 * 令牌桶算法-限速器
 */
public class TokenBucketLimiter extends RateLimiter {
    // cas自旋锁
    private Lock lock = new ReentrantLock();

    // 当前桶中令牌个数
    private volatile double token =0;

    // 上次添加令牌的时间
    private volatile long lastTime = 0;

    // 桶容量大小
    private int capacity = 0;

    public TokenBucketLimiter(int rate) {
        super(rate);
    }

    public boolean tryAcquire() {
        lock.lock();
        try {
            // 计算放入桶中的令牌数
            long now = System.currentTimeMillis();
            double inToken = (double) (now - lastTime)/1000 * rate;
            if (inToken > 0){
                lastTime = now;
            }

            // 计算桶中的令牌数
            token = token + inToken;
            token = Math.min(token, capacity);

            // 能否取到令牌
            if (token-1 >= 0){
                token--;
                return true;
            }
        }finally {
            lock.unlock();
        }
        return false;
    }
}
