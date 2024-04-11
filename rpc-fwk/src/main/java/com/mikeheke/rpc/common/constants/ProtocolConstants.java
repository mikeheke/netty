package com.mikeheke.rpc.common.constants;

/**
 * 自定义协议常量类
 *
 * 【自定义通信协议构成，共16字节】
 * 魔数（5字节） + 协议版本号（1字节） + 序列化算法（1字节） + 指令类型（1字节） + 请求序列号（4字节） + 正文长度（4字节） + 消息正文
 * 如：
 * HiNio 0 1 1 1234 5 hello
 * ->
 * 字节数组：0001110010101010...
 *
 * @author heke
 * @since 2024-03-22
 */
public class ProtocolConstants {
    /**
     * 魔数字节数，5个字节(char类型)，HiNio
     */
    public final static int MAGIC_NUM_BYTE_SIZE = 5;
    /**
     * 版本号字节数，1个字节(byte类型)，枚举类 ProtocolVersionEnum
     */
    public final static int VERSION_BYTE_SIZE = 1;
    /**
     * 序列化算法字节数，1个字节(byte类型)，枚举类 SerializationAlgorithmEnum
     */
    public final static int SERIALIZATION_ALGORITHM_BYTE_SIZE = 1;
    /**
     * 指令类型(消息类型)字节数，1个字节(byte类型)，枚举类：MessageTypeEnum
     */
    public final static int MSG_TYPE_BYTE_SIZE = 1;
    /**
     * 请求序列号字节数，4个字节(int类型)
     */
    public final static int REQ_SERIAL_NUMBER_BYTE_SIZE = 4;
    /**
     * 正文长度字节数，4个字节(int类型)
     */
    public final static int CONTENT_LENGTH_BYTE_SIZE = 4;
    /**
     * 魔数内容
     */
    public final static String MAGIC_NUM_CONTENT = "HiRpc";

}
