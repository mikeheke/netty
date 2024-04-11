package com.mikeheke.rpc.common.enums;

import com.mikeheke.rpc.common.message.Message;
import com.mikeheke.rpc.common.message.PingMessage;
import com.mikeheke.rpc.common.message.RpcRequestMessage;
import com.mikeheke.rpc.common.message.RpcResponseMessage;
import lombok.Getter;

/**
 * 指令类型（消息类型） 枚举类
 *
 * @author heke
 * @since 2024-03-31
 */
@Getter
public enum MessageTypeEnum {
    RPC_REQUEST( (byte)0, RpcRequestMessage.class, "rpc请求消息"),
    RPC_RESPONSE( (byte)1, RpcResponseMessage.class, "rpc响应消息"),
    PING((byte)2, PingMessage.class,"空闲检测ping消息"),
    ;

    private byte value;
    private Class<? extends Message> messageClass;
    private String desc;

    MessageTypeEnum(byte value, Class<? extends Message> messageClass, String desc) {
        this.value = value;
        this.messageClass = messageClass;
        this.desc = desc;
    }

    /**
     * 根据消息类型值获取消息class
     *
     * @param type
     * @return
     */
    public static Class<? extends Message> getMessageClassByType(byte type) {
        for ( MessageTypeEnum messageTypeEnum : values() ) {
            if ( messageTypeEnum.getValue() == type ) {
                return messageTypeEnum.getMessageClass();
            }
        }
        return null;
    }

}
