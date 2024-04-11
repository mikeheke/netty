package com.mikeheke.rpc.common.protocol;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * json序列化&反序列化
 *
 * @author heke
 * @since 2024-03-25
 */
@Slf4j
public class SerializationByJson<T> implements ISerializationAlgorithm<T> {

    @Override
    public byte[] serialize(T msg) {
        try {
            String msgJson = JSON.toJSONString(msg);
            log.debug("msgJson: {}", msgJson);
            return msgJson.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("serialize error!", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public T deserialize(Class<T> clazz, byte[] bytes) {
        try {
            String messageJson = new String(bytes, StandardCharsets.UTF_8);
            return JSON.parseObject(messageJson, clazz);
        } catch (Exception e) {
            log.error("deserialize error!", e);
            throw new RuntimeException(e);
        }
    }
}
