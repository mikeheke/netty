package com.mikeheke.rpc.server.handler;

import com.mikeheke.rpc.common.message.RpcRequestMessage;
import com.mikeheke.rpc.common.message.RpcResponseMessage;
import com.mikeheke.rpc.server.business.ServiceInvoker;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 【服务端】rpc请求消息处理器
 *
 * @author heke
 * @since 2024-04-01
 */
@Slf4j
public class RpcRequestMessageHandlerInServer extends SimpleChannelInboundHandler<RpcRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage rpcRequestMessage) throws Exception {
        log.debug("RpcRequestMessageHandlerInServer.channelRead0(), ctx: {}, rpcRequestMessage: {}", ctx, rpcRequestMessage);
        RpcResponseMessage rpcResponseMessage = new RpcResponseMessage();
        rpcResponseMessage.setSerialNumber(rpcRequestMessage.getSerialNumber());
        try {
            Object result = ServiceInvoker.invoke(rpcRequestMessage);
            log.debug("result: {}", result);
            rpcResponseMessage.setSuccess(true);
            rpcResponseMessage.setReturnValue(result);

            //Thread.sleep(10000);

        } catch (Exception e) {
            log.error("RpcRequestMessageHandlerInServer.channelRead0() error!", e);
            rpcResponseMessage.setSuccess(false);
            rpcResponseMessage.setExceptionValue(e);
        }
        ctx.writeAndFlush(rpcResponseMessage);
    }

}
