package com.mikeheke.rpc.common.enums;

import lombok.Getter;

/**
 * 协议版本号 枚举类
 *
 * @author heke
 * @since 2024-03-31
 */
@Getter
public enum ProtocolVersionEnum {
    V1( (byte)1 , "版本v1"),
    V2( (byte)2 , "版本v2"),
    V3( (byte)3 , "版本v3");

    private byte value;
    private String desc;

    ProtocolVersionEnum(byte value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
