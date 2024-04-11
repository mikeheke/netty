package com.mikeheke.rpc.client.handler;

import com.mikeheke.rpc.client.ClientContext;
import com.mikeheke.rpc.common.message.RpcResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

/**
 * 【客户端】rpc响应消息处理器
 *
 * @author heke
 * @since 2024-04-01
 */
@Slf4j
public class RpcResponseMessageHandlerInClient extends SimpleChannelInboundHandler<RpcResponseMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponseMessage rpcResponseMessage) throws Exception {
        log.debug("RpcResponseMessageHandlerInClient.channelRead0(), ctx: {}, rpcResponseMessage: {}", ctx, rpcResponseMessage);
        Promise<Object> promise = ClientContext.responseMap.get(rpcResponseMessage.getSerialNumber()+"");
        if ( promise == null ) {
            return;
        }

        if ( rpcResponseMessage.isSuccess() ) {
            promise.setSuccess(rpcResponseMessage.getReturnValue());
        } else {
            promise.setFailure(rpcResponseMessage.getExceptionValue());
        }

        ClientContext.responseMap.remove(rpcResponseMessage.getSerialNumber());
    }

}
