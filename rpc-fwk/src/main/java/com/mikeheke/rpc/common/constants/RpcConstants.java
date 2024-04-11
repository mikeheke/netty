package com.mikeheke.rpc.common.constants;


import com.mikeheke.rpc.common.enums.SerializationAlgorithmEnum;

/**
 * rpc框架常量类
 *
 * @author heke
 * @since 2024-03-31
 */
public class RpcConstants {

    /**
     * 服务端端口号
     */
    public final static int SERVER_PORT = 39999;

    /**
     * 消息正文序列化算法
     */
    public final static SerializationAlgorithmEnum serializationAlgorithm = SerializationAlgorithmEnum.JDK;

    /**
     * 是否开启：空闲检测
     */
    public final static boolean idleStateCheck = false;

}
