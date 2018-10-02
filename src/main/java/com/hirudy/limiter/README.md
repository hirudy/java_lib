# 限速器集中策略和方法

## 限流方式之计数器(滑动窗口协议)
**思路**：限速，我们可能第一个想到的应该是，我通过一个计数器，进行技术，如果超过了计数器阀值，表示速度太快了。一秒一个计数器。
![计数器.png](https://github.com/hirudy/java_lib/blob/master/doc/limiter/counter.png)

## 限速方式之漏桶算法
在生活中，如果一桶有一个细眼，我们往里面装水，可以看到水是一滴一滴匀速的下落的，哪我们能不能通过程序来实现这种方式呢。
   
**思路**：桶为容器，一滴水为一请求。如果桶满了就拒绝请求，没满处理请求。
![漏桶.png](https://github.com/hirudy/java_lib/blob/master/doc/limiter/leaky_bucket.png)

## 限速方式之令牌桶算法
**思路**：匀速的产生令牌，往桶里面丢，每次请求来，看是否有多余的令牌。如果有获取令牌执行正常业务，偌没有限速。
![令牌桶.png](https://github.com/hirudy/java_lib/blob/master/doc/limiter/token_bucket.png)