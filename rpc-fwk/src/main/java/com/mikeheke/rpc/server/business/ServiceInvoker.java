package com.mikeheke.rpc.server.business;

import com.mikeheke.rpc.common.message.RpcRequestMessage;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * 服务执行者
 * 描述：
 * 通过反射机制实现O
 *
 * @author heke
 * @since 2024-04-01
 */
@Slf4j
public class ServiceInvoker {

    /**
     * 调用服务方法
     *
     * @param rpcRequestMessage
     * @return
     */
    public static Object invoke(RpcRequestMessage rpcRequestMessage) throws Exception {
        String interfaceName = rpcRequestMessage.getInterfaceName();
        Object target = BeanFactory.getBean(interfaceName);
        Class clazz = Class.forName(interfaceName);

        Method method = null;
        Method[] methods = clazz.getMethods();
        for ( Method m : methods ) {
            if ( m.getName().equals(rpcRequestMessage.getMethodName())
                    && m.getParameterCount() == rpcRequestMessage.getParameterTypes().length  ) {
                method = m;
                break;
            }
        }

        if ( method != null ) {
            return method.invoke(target, rpcRequestMessage.getParameterValue());
        }

        throw new RuntimeException("method not found!O");
    }

}
