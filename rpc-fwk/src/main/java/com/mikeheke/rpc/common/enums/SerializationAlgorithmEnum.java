package com.mikeheke.rpc.common.enums;

import lombok.Getter;

/**
 * 序列化算法 枚举类
 *
 * @author heke
 * @since 2024-03-31
 */
@Getter
public enum SerializationAlgorithmEnum {
    JDK( (byte)0 , "jdk自带序列化"),
    JSON( (byte)1 , "json序列化"),
    PROTOBUF( (byte)2 , "google protobuf序列化"),
    XML( (byte)3 , "xml序列化"),
    HESSIAN( (byte)4 , "hessian序列化");

    private byte value;
    private String desc;

    SerializationAlgorithmEnum(byte value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
