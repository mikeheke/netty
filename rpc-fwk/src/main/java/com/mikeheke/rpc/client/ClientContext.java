package com.mikeheke.rpc.client;

import io.netty.channel.Channel;
import io.netty.util.concurrent.Promise;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * rpc客户端上下文
 *
 * @author heke
 * @since 2024-03-31
 */
public class ClientContext {

    public static Channel channel;

    /**
     * 响应结果map
     */
    public static Map<String, Promise<Object>> responseMap = new ConcurrentHashMap<>();

}
