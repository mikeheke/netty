package com.mikeheke.rpc.common.message;

import java.io.Serializable;

/**
 * 消息抽象类
 *
 * @author heke
 * @since 2024-03-23
 */
public abstract class Message implements Serializable {

    /**
     * 消息类型（指令类型）
     */
    protected int messageType;

    /**
     * 请求序列号
     */
    protected int serialNumber;

    public abstract int getMessageType();

    public Message() {
        messageType = getMessageType();
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

}
