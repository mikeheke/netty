package com.mikeheke.rpc.common.message;

import lombok.Data;

/**
 * 抽象响应消息
 *
 * @author heke
 * @since 2024-03-23
 */
@Data
public abstract class AbstractResponseMessage extends Message {

    /**
     * 是否成功
     */
    protected boolean success;

    /**
     * 描述
     */
    protected String desc;

}
