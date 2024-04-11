package com.mikeheke.rpc.common.protocol;

/**
 * 序列化算法接口定义
 *
 * @author heke
 * @since 2024-03-31
 */
public interface ISerializationAlgorithm<T> {

    /**
     * 序列化
     *
     * @param msg
     * @return
     */
    byte[] serialize(T msg);

    /**
     * 反序列化
     *
     * @param clazz
     * @param bytes
     * @return
     */
    T deserialize(Class<T> clazz, byte[] bytes);

}
