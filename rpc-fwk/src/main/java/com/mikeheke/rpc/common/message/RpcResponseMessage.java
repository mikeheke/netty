package com.mikeheke.rpc.common.message;

import com.mikeheke.rpc.common.enums.MessageTypeEnum;
import lombok.Data;

/**
 * rpc响应消息
 *
 * @author heke
 * @since 2024-03-31
 */
@Data
public class RpcResponseMessage extends AbstractResponseMessage {

    /**
     * 返回值
     */
    private Object returnValue;
    /**
     * 异常值
     */
    private Exception exceptionValue;

    @Override
    public int getMessageType() {
        return MessageTypeEnum.RPC_RESPONSE.getValue();
    }

    @Override
    public String toString() {
        return "RpcResponseMessage{" +
                "returnValue=" + returnValue +
                ", exceptionValue=" + exceptionValue +
                ", success=" + success +
                ", desc='" + desc + '\'' +
                ", messageType=" + messageType +
                ", serialNumber=" + serialNumber +
                '}';
    }
}
