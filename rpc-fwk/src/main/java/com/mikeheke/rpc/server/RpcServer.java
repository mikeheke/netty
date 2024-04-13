package com.mikeheke.rpc.server;

import com.mikeheke.rpc.common.constants.RpcConstants;
import com.mikeheke.rpc.common.protocol.CustomLengthFieldBasedFrameDecoder;
import com.mikeheke.rpc.common.protocol.MessageCodec;
import com.mikeheke.rpc.server.handler.QuitHandlerInServer;
import com.mikeheke.rpc.server.handler.RpcRequestMessageHandlerInServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * rpc框架-服务端启动类
 *
 * @author heke
 * @since 2024-03-31
 */
@Slf4j
public class RpcServer {

    public static void main(String[] args) {
        EventLoopGroup bossEventLoopGroup = new NioEventLoopGroup(1); // boss threadPool
        EventLoopGroup workerEventLoopGroup = new NioEventLoopGroup(2); // worker threadPool
        //EventLoopGroup normalEventLoopGroup = new DefaultEventLoopGroup(2); // other threadPool

        ServerBootstrap serverBootstrap = new ServerBootstrap(); // 1、创建服务端启动对象
        serverBootstrap.group(bossEventLoopGroup, workerEventLoopGroup) // 2、设置NioEventLoopGroup，线程池+多Selector模式
                .channel(NioServerSocketChannel.class) // 3、选择服务端Socket实现类
                .childHandler(new ChannelInitializer<NioSocketChannel>() { // 4、初始化SocketChannel处理器
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        log.debug("initChannel... ch: {}", ch);
                        // 获取通道流水线
                        ChannelPipeline pipeline = ch.pipeline();
                        // ------ 添加通道处理器 ------
                        // 日志记录器，方便记录日志
                        pipeline.addLast(new LoggingHandler());
                        // 自定义长度帧解码器，解决TCP半包粘包问题
                        pipeline.addLast(new CustomLengthFieldBasedFrameDecoder());
                        // 自定义协议编码&解码器
                        pipeline.addLast(new MessageCodec());
                        // rpc请求消息处理器
                        pipeline.addLast(new RpcRequestMessageHandlerInServer());
                        // 客户端退出处理器
                        pipeline.addLast(new QuitHandlerInServer());

                        // ------ 【空闲检测】处理器 begin ------
                        if ( RpcConstants.idleStateCheck ) {
                            // 用来判断是不是 读空闲时间过长，或 写空闲时间过长
                            // 5s 内如果没有收到 channel 的数据，会触发一个 IdleState#READER_IDLE 事件
                            ch.pipeline().addLast(new IdleStateHandler(
                                    15, 0, 0));
                            // ChannelDuplexHandler 可以同时作为入站和出站处理器
                            ch.pipeline().addLast(new ChannelDuplexHandler() {
                                // 用来触发特殊事件
                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
                                    IdleStateEvent event = (IdleStateEvent) evt;
                                    // 触发了读空闲事件
                                    if (event.state() == IdleState.READER_IDLE) {
                                        log.debug("已经 15s 没有读到数据了");
                                        // 关闭客户端连接
                                        ctx.channel().close();
                                    }
                                }
                            });
                        }
                        // ------ 【空闲检测】处理器 end ------

                    }
                });

        ChannelFuture channelFuture = serverBootstrap.bind(RpcConstants.SERVER_PORT); // 5、服务端绑定端口
        // 服务端绑定端口成功增加回调监听器
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                log.debug("server bind port success! future: {}", future);
            }
        });
        log.debug("main execute finish!");
    }

}
