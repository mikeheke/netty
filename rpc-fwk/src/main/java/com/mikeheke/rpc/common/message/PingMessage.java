package com.mikeheke.rpc.common.message;

import com.mikeheke.rpc.common.enums.MessageTypeEnum;
import lombok.Data;

/**
 * 空闲检测ping消息
 *
 * @author heke
 * @since 2024-03-29
 */
@Data
public class PingMessage extends AbstractRequestMessage {

    @Override
    public int getMessageType() {
        return MessageTypeEnum.PING.getValue();
    }

}
