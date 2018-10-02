package com.hirudy.limiter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * User: Rudy Tan
 * Date: 2018/10/2
 *
 * 漏桶算法-限速器
 */
public class LeakyBucketLimiter extends RateLimiter {
    // cas自旋锁
    private Lock lock = new ReentrantLock();

    // 当前桶中的剩余的水数量
    private volatile double water = 0;

    // 上次漏水的时间
    private volatile long lastTime = 0;

    // 桶容量大小
    private int capacity = 0;

    public LeakyBucketLimiter(int rate) {
        super(rate);
        capacity = rate;
    }

    public boolean tryAcquire() {
        lock.lock();
        try{
            // 计算这段时间，漏掉了多少水
            long now = System.currentTimeMillis();
            double outWater = (double) (now - lastTime)/1000 * rate;
            if (outWater > 0){
                lastTime = now;
            }

            // 计算桶中剩余的水
            water = Math.max(0, water - outWater);

            // 如果桶没满，返回成功
            if (water < capacity){
                water ++;
                return true;
            }else {
                return false;
            }
        }finally {
            lock.unlock();
        }
    }
}
