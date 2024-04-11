package com.mikeheke.rpc.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 【服务端】客户端退出处理器
 *
 * @author heke
 * @since 2024-03-31
 */
@Slf4j
public class QuitHandlerInServer extends ChannelInboundHandlerAdapter {

    // 在连接断开时触发
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("QuitHandlerInServer.channelInactive(), ctx: {}", ctx);
//        ServerContext.removeUserSession(ctx.channel());
    }

    // 在出现异常时触发
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.debug("QuitHandlerInServer.exceptionCaught(), ctx: {}", ctx, cause);
//        ServerContext.removeUserSession(ctx.channel());
    }

}
