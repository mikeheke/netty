package com.mikeheke.rpc.common.message;

import com.mikeheke.rpc.common.enums.MessageTypeEnum;
import lombok.Data;

import java.util.Arrays;

/**
 * rpc请求消息
 *
 * @author heke
 * @since 2024-03-31
 */
@Data
public class RpcRequestMessage extends AbstractRequestMessage {

    /**
     * 调用的接口全限定名，服务端根据它找到实现
     */
    private String interfaceName;
    /**
     * 调用接口中的方法名
     */
    private String methodName;
    /**
     * 方法返回类型
     */
    private Class<?> returnType;
    /**
     * 方法参数类型数组
     */
    private Class[] parameterTypes;
    /**
     * 方法参数值数组
     */
    private Object[] parameterValue;

    @Override
    public int getMessageType() {
        return MessageTypeEnum.RPC_REQUEST.getValue();
    }

    @Override
    public String toString() {
        return "RpcRequestMessage{" +
                "interfaceName='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", returnType=" + returnType +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", parameterValue=" + Arrays.toString(parameterValue) +
                ", messageType=" + messageType +
                ", serialNumber=" + serialNumber +
                '}';
    }
}
