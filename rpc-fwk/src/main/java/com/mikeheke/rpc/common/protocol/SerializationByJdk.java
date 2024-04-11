package com.mikeheke.rpc.common.protocol;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * jdk序列化&反序列化
 *
 * @author heke
 * @since 2024-03-25
 */
@Slf4j
public class SerializationByJdk<T> implements ISerializationAlgorithm<T> {

    @Override
    public byte[] serialize(T msg) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(msg);
            return bos.toByteArray();
        } catch (Exception e) {
            log.error("serialize error!", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public T deserialize(Class<T> clazz, byte[] bytes) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return (T)ois.readObject();
        } catch (Exception e) {
            log.error("deserialize error!", e);
            throw new RuntimeException(e);
        }
    }
}
