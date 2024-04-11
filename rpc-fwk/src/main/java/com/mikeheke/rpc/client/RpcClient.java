package com.mikeheke.rpc.client;

import com.mikeheke.rpc.client.handler.QuitHandlerInClient;
import com.mikeheke.rpc.client.handler.RpcResponseMessageHandlerInClient;
import com.mikeheke.rpc.common.constants.RpcConstants;
import com.mikeheke.rpc.common.message.PingMessage;
import com.mikeheke.rpc.common.protocol.CustomLengthFieldBasedFrameDecoder;
import com.mikeheke.rpc.common.protocol.MessageCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * rpc框架-客户端启动类
 *
 * @author heke
 * @since 2024-03-31
 */
@Slf4j
public class RpcClient {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup workerEventLoopGroup = new NioEventLoopGroup(1); // worker threadPool

        Bootstrap bootstrap = new Bootstrap(); // 1、创建客户端启动对象
        Channel channel = bootstrap
                .group(workerEventLoopGroup) // 2、设置NioEventLoopGroup，线程池+多Selector模式
                .channel(NioSocketChannel.class) // 3、选择客户 Socket 实现类，NioSocketChannel 表示基于 NIO 的客户端实现
                .handler(new ChannelInitializer<Channel>() { // 4、添加 SocketChannel 的处理器，ChannelInitializer 处理器（仅执行一次），它的作用是待客户端 SocketChannel 建立连接后，执行 initChannel 以便添加更多的处理器
                    @Override
                    protected void initChannel(Channel ch) {
                        log.debug("initChannel... ch: {}", ch);
                        //ch.pipeline().addLast(new StringEncoder()); // 9、消息会经过通道 handler 处理，这里是将 String => ByteBuf 发出
                        // 获取通道流水线
                        ChannelPipeline pipeline = ch.pipeline();
                        // ------ 添加通道处理器 ------
                        // 日志记录器，方便记录日志
                        pipeline.addLast(new LoggingHandler());
                        // 自定义长度帧解码器，解决TCP半包粘包问题
                        pipeline.addLast(new CustomLengthFieldBasedFrameDecoder());
                        // 自定义协议编码&解码器
                        pipeline.addLast(new MessageCodec());
                        // 退出，断开连接处理器
                        pipeline.addLast(new QuitHandlerInClient());
                        // rpc响应消息处理器
                        pipeline.addLast(new RpcResponseMessageHandlerInClient());

                        // ------ 【空闲检测】处理器 begin ------
                        if ( RpcConstants.idleStateCheck ) {
                            // 用来判断是不是 读空闲时间过长，或 写空闲时间过长
                            // 3s 内如果没有向服务器写数据，会触发一个 IdleState#WRITER_IDLE 事件
                            pipeline.addLast(new IdleStateHandler(
                                    0, 5, 0));
                            // ChannelDuplexHandler 可以同时作为入站和出站处理器
                            pipeline.addLast(new ChannelDuplexHandler() {
                                // 用来触发特殊事件
                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
                                    IdleStateEvent event = (IdleStateEvent) evt;
                                    // 触发了写空闲事件
                                    if (event.state() == IdleState.WRITER_IDLE) {
                                        log.debug("5s 没有写数据了，发送一个心跳包");
                                        ctx.writeAndFlush(new PingMessage());
                                    }
                                }
                            });
                        }
                        // ------ 【空闲检测】处理器 end ------

                    }
                })
                .connect("127.0.0.1", RpcConstants.SERVER_PORT) // 5、指定要连接的服务器IP和端口
                .sync() // 6、Netty 中很多方法都是异步的，如 connect，这时需要使用 sync 方法等待 connect 建立连接完毕
                .channel(); // 7、获取 channel 对象，它即为通道抽象，可以进行数据读写操作

        ClientContext.channel = channel;
        RpcClientUI rpcClientUI = new RpcClientUI();
        rpcClientUI.setChannel(channel);
        rpcClientUI.initUI();
    }

}
