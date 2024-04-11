package com.mikeheke.rpc.common.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义长度帧解码器，解决TCP半包粘包问题
 *
 * @author heke
 * @since 2024-03-31
 */
@Slf4j
public class CustomLengthFieldBasedFrameDecoder extends LengthFieldBasedFrameDecoder {
    // param1: 发送的数据包最大长度
    private final static int maxFrameLength = 1024;
    // param2: 长度域偏移量，指的是长度域位于整个数据包字节数组中的下标
    // 取值：魔数（5字节） + 协议版本号（1字节） + 序列化算法（1字节） + 指令类型（1字节） + 请求序列号（4字节）
    private final static int lengthFieldOffset = 12;
    // param3: 长度域的自己的字节数长度
    // 取值：正文长度（4字节）
    private final static int lengthFieldLength = 4;
    // param4: 长度调整，添加到长度字段数值的补偿值，一般用于长度字段代表整个消息长度
    private final static int lengthAdjustment = 0;
    // param5: 跳过指定长度字节，一般用于跳过消息头，只将消息体保留
    private final static int initialBytesToStrip = 0;

    public CustomLengthFieldBasedFrameDecoder() {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
        log.debug("CustomLengthFieldBasedFrameDecoder(), maxFrameLength: {}, lengthFieldOffset: {}, lengthFieldLength: {}, lengthAdjustment: {}, initialBytesToStrip: {}",
                maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

}
