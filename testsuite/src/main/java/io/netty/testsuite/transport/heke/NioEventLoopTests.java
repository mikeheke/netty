package io.netty.testsuite.transport.heke;

import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * NioEventLoop单元测试
 *
 * @author heke
 * @since 2024-04-11
 */
@Slf4j
public class NioEventLoopTests {

    public static void main(String[] args) throws Exception {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        log.debug("nioEventLoopGroup: {}", nioEventLoopGroup);
        //EventLoop eventLoop = nioEventLoopGroup.next();
        NioEventLoop eventLoop = (NioEventLoop)nioEventLoopGroup.next();
        log.debug("eventLoop: {}", eventLoop);
//
//        // 1
//        eventLoop.execute(new Runnable() {
//            @Override
//            public void run() {
//                log.debug("run task1 begin!");
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                log.debug("run task1 end!");
//            }
//        });
//
//        // 2
//        eventLoop.execute(new Runnable() {
//            @Override
//            public void run() {
//                log.debug("run task2 begin!");
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                log.debug("run task2 end!");
//            }
//        });
//
//        // 3
//        Future<String> future = eventLoop.submit(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                log.debug("run task3 begin!");
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                log.debug("run task3 end!");
//                return "task3-value";
//            }
//        });
//
//        String result = future.get();
//        log.debug("task3 result: {}", result);

        // 4
        Future<String> future2 = eventLoop.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.debug("run task4 begin!");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.debug("run task4 end!");
                return "task4-value";
            }
        });

        log.debug("future2: {}", future2);

        future2.addListener(new GenericFutureListener<Future<? super String>>() {
            @Override
            public void operationComplete(Future<? super String> future) throws Exception {
                log.debug("future: {}", future);
                log.debug("task4 result: {}", future.get());
            }
        });


        log.debug("main end...............................");
    }

}
