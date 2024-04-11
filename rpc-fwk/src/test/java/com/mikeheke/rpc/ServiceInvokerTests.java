package com.mikeheke.rpc;

import com.mikeheke.rpc.common.business.IUserService;
import com.mikeheke.rpc.common.message.RpcRequestMessage;
import com.mikeheke.rpc.server.business.ServiceInvoker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author heke
 * @since 2024-04-01
 */
@Slf4j
public class ServiceInvokerTests {

    @Test
    public void testInvoke() throws Exception {
        Method method = null;
        Method[] methods = IUserService.class.getMethods();
        for ( Method m : methods ) {
            if ( m.getName().equals("findByAccount") ) {
                method = m;
                break;
            }
        }

        // 调用的接口全限定名，服务端根据它找到实现
        String interfaceName = IUserService.class.getName();
        // 调用接口中的方法名
        String methodName = method.getName();
        // 方法返回类型
        Class<?> returnType = method.getReturnType();
        // 方法参数类型数组
        Class[] parameterTypes = method.getParameterTypes();
        // 方法参数值数组
        Object[] parameterValue = new Object[]{ "zhangsan" };

        RpcRequestMessage rpcRequestMessage = new RpcRequestMessage();
        rpcRequestMessage.setSerialNumber(1);
        rpcRequestMessage.setInterfaceName(interfaceName);
        rpcRequestMessage.setMethodName(methodName);
        rpcRequestMessage.setReturnType(returnType);
        rpcRequestMessage.setParameterTypes(parameterTypes);
        rpcRequestMessage.setParameterValue(parameterValue);
        log.debug("{}", rpcRequestMessage);

        Object result = ServiceInvoker.invoke(rpcRequestMessage);
        log.debug("result: {}", result);
    }

}
