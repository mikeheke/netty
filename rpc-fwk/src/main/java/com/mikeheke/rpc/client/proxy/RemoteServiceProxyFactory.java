package com.mikeheke.rpc.client.proxy;

import com.mikeheke.rpc.client.ClientContext;
import com.mikeheke.rpc.common.enums.SerializationAlgorithmEnum;
import com.mikeheke.rpc.common.message.RpcRequestMessage;
import com.mikeheke.rpc.common.protocol.ISerializationAlgorithm;
import com.mikeheke.rpc.common.protocol.SerializationFactory;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 远程服务代理工厂
 *
 * @author heke
 * @since 2024-03-31
 */
@Slf4j
public class RemoteServiceProxyFactory {

    private static AtomicInteger serialNumber = new AtomicInteger();

    /**
     * 获取代理服务对象
     *
     * @param clazz
     * @return
     */
    public static Object getProxyServiceInstance(Class clazz) {
        InvocationHandler invocationHandler = new InvocationHandlerImpl(clazz);
        ClassLoader classLoader = clazz.getClassLoader();
        Class<?>[] interfaces = new Class<?>[] { clazz };
        Object proxyObj = Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
        log.debug("proxyObjClass: {}", proxyObj.getClass());
        return proxyObj;
    }

    @Slf4j
    static class InvocationHandlerImpl implements InvocationHandler {

        private Class interfaceType;

        public InvocationHandlerImpl(Class interfaceType) {
            this.interfaceType = interfaceType;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            log.debug("invoke(), method: {}", method);
            log.debug("invoke(), args: {}", args);
            log.debug("invoke(), interfaceClass: {}", interfaceType.getName());
            //System.out.println("realObject: " + realObject);
            //System.out.println("proxy: " + proxy); 无法打印！


            // 调用的接口全限定名，服务端根据它找到实现
            String interfaceName = interfaceType.getName();
            // 调用接口中的方法名
            String methodName = method.getName();
            // 方法返回类型
            Class<?> returnType = method.getReturnType();
            // 方法参数类型数组
            Class[] parameterTypes = method.getParameterTypes();
            // 方法参数值数组
            Object[] parameterValue = args;

            RpcRequestMessage rpcRequestMessage = new RpcRequestMessage();
            rpcRequestMessage.setSerialNumber(serialNumber.addAndGet(1));
            rpcRequestMessage.setInterfaceName(interfaceName);
            rpcRequestMessage.setMethodName(methodName);
            rpcRequestMessage.setReturnType(returnType);
            rpcRequestMessage.setParameterTypes(parameterTypes);
            rpcRequestMessage.setParameterValue(parameterValue);
            log.debug("{}", rpcRequestMessage);

            //ISerializationAlgorithm serializationAlgorithm =
            //        SerializationFactory.getSerializationByType(SerializationAlgorithmEnum.JDK.getValue());
            //byte[] bytes = serializationAlgorithm.serialize(rpcRequestMessage);
            //RpcRequestMessage _rpcRequestMessage = (RpcRequestMessage)serializationAlgorithm.deserialize(RpcRequestMessage.class, bytes);
            //log.debug("_rpcRequestMessage, {}", _rpcRequestMessage);

            ClientContext.channel.writeAndFlush(rpcRequestMessage);

            DefaultPromise<Object> promise = new DefaultPromise<>(ClientContext.channel.eventLoop());
            ClientContext.responseMap.put(rpcRequestMessage.getSerialNumber()+"", promise);
            promise.await();

            Object result;
            if ( promise.isSuccess() ) {
                result = promise.getNow();
            } else {
                throw new RuntimeException(promise.cause());
            }
            log.debug("result: {}", result);
            return result;
        }

    }


}
