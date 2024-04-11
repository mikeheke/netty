package com.mikeheke.rpc.common.protocol;


import com.mikeheke.rpc.common.constants.ProtocolConstants;
import com.mikeheke.rpc.common.constants.RpcConstants;
import com.mikeheke.rpc.common.enums.MessageTypeEnum;
import com.mikeheke.rpc.common.enums.ProtocolVersionEnum;
import com.mikeheke.rpc.common.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 消息编码&解码器
 * 必须和 LengthFieldBasedFrameDecoder 一起使用，确保接到的 ByteBuf 消息是完整的
 *
 * @author heke
 * @since 2024-03-31
 */
@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {

    /**
     * 编码 object--->bytes
     *
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    public void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        log.debug("encode(), ctx: {}, msg: {}, out: {}", ctx, msg, out);

        // -----------------------------------------------------------------------------------------------------------------
        // 魔数（5字节） + 协议版本号（1字节） + 序列化算法（1字节） + 指令类型（1字节） + 请求序列号（4字节） + 正文长度（4字节） + 消息正文
        // -----------------------------------------------------------------------------------------------------------------

        // 1. 魔数字节数，5个字节(char类型)，HiNio
        out.writeBytes(ProtocolConstants.MAGIC_NUM_CONTENT.getBytes());
        // 2. 版本号字节数，1个字节(byte类型)，枚举类 ProtocolVersionEnum
        out.writeByte(ProtocolVersionEnum.V1.getValue());
        // 3. 序列化算法字节数，1个字节(byte类型)，枚举类 SerializationAlgorithmEnum
        out.writeByte(RpcConstants.serializationAlgorithm.getValue());
        // 4. 指令类型(消息类型)字节数，1个字节(byte类型)，枚举类：MessageTypeEnum
        out.writeByte(msg.getMessageType());
        // 5. 请求序列号字节数，4个字节(int类型)
        out.writeInt(msg.getSerialNumber());

        // 获取序列化算法
        ISerializationAlgorithm serialization = SerializationFactory.getSerializationByType(RpcConstants.serializationAlgorithm.getValue());
        byte[] bytes = serialization.serialize(msg);

        // 6. 正文长度字节数，4个字节(int类型)
        out.writeInt(bytes.length);
        // 7. 消息正文
        out.writeBytes(bytes);

        log.debug("encode() success! out: {}", out);
    }

    /**
     * 解码 bytes--->object
     *
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.debug("decode(), ctx: {}, in: {}, out: {}", ctx, in, out);

        // 1. 魔数字节数，5个字节(char类型)
        byte[] magicNumBytes = new byte[5];
        in.readBytes(magicNumBytes);
        String magicNum = new String(magicNumBytes);
        log.debug("magicNum: {}", magicNum);

        // 2. 版本号字节数，1个字节(byte类型)，枚举类 ProtocolVersionEnum
        byte version = in.readByte();
        log.debug("version: {}", version);

        // 3. 序列化算法字节数，1个字节(byte类型)，枚举类 SerializationAlgorithmEnum
        byte serializationAlgorithm = in.readByte();
        log.debug("serializationAlgorithm: {}", serializationAlgorithm);

        // 4. 指令类型(消息类型)字节数，1个字节(byte类型)，枚举类：MessageTypeEnum
        byte messageType = in.readByte();
        log.debug("messageType: {}", messageType);

        // 5. 请求序列号字节数，4个字节(int类型)
        int serialNumber = in.readInt();
        log.debug("serialNumber: {}", serialNumber);

        // 6. 正文长度字节数，4个字节(int类型)
        int msgLength = in.readInt();
        log.debug("msgLength: {}", msgLength);

        // 7. 消息正文
        byte[] msgBytes = new byte[msgLength];
        in.readBytes(msgBytes, 0, msgLength);

        Class<? extends Message> messageClass = MessageTypeEnum.getMessageClassByType(messageType);
        ISerializationAlgorithm serialization = SerializationFactory.getSerializationByType(serializationAlgorithm);
        Message message = (Message) serialization.deserialize(messageClass, msgBytes);
        log.debug("{}", message);

        log.debug("decode(), magicNum: {}, version: {}, serializationAlgorithm: {}, messageType: {}, serialNumber: {}, msgLength: {}, message: {}",
                magicNum, version, serializationAlgorithm, messageType, serialNumber, msgLength, message);

        // 8. 把消息往后传递
        out.add(message);
        log.debug("decode() success! ctx: {}, in: {}, out: {}", ctx, in, out);
    }

}
