package com.mikeheke.rpc.common.protocol;


import com.mikeheke.rpc.common.enums.SerializationAlgorithmEnum;

/**
 * 序列化工厂类
 *
 * @author heke
 * @since 2024-03-26
 */
public class SerializationFactory {

    private static final ISerializationAlgorithm serializationByJdk = new SerializationByJdk();
    private static final ISerializationAlgorithm serializationByJson = new SerializationByJson();

    /**
     * 根据类型获取序列化实现类
     *
     * @param type
     * @return
     */
    public static ISerializationAlgorithm getSerializationByType(int type) {
        if (SerializationAlgorithmEnum.JDK.getValue() == type) {
            return serializationByJdk;
        } else if (SerializationAlgorithmEnum.JSON.getValue() == type) {
            return serializationByJson;
        }
        return null;
    }

}
