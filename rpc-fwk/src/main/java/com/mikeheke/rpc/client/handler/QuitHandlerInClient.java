package com.mikeheke.rpc.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 【客户端】退出处理器
 *
 * @author heke
 * @since 2024-03-31
 */
@Slf4j
public class QuitHandlerInClient extends ChannelInboundHandlerAdapter {

    // 在连接断开时触发
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("QuitHandlerInClient.channelInactive(), ctx: {}", ctx);
//        ClientContext.connected.set(false);
//        ClientContext.chatUI.getAreaShow().append("\n" + "退出，服务端已正常关闭连接！" + "\n");
    }

    // 在出现异常时触发
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.debug("QuitHandlerInClient.exceptionCaught(), ctx: {}", ctx, cause);
//        ClientContext.connected.set(false);
//        ClientContext.chatUI.getAreaShow().append("\n" + "退出，服务端异常关闭连接！！！" + "\n");
    }

}
