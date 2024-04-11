package com.mikeheke.rpc;

import com.mikeheke.rpc.common.business.IUserService;
import com.mikeheke.rpc.common.business.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 用户服务单元测试
 *
 * @author heke
 * @since 2024-03-31
 */
@Slf4j
public class UserServiceImplTests2 {

    @Before
    public void before() {

    }

    @Test
    public void testFindByAccount() {
        String account = "zhangsan";
        IUserService proxyService = (IUserService) getProxyService(IUserService.class);
        UserVO userVO = proxyService.findByAccount(account);
        log.debug("{}", userVO);
    }

    private Object getProxyService(Class clazz) {
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                log.debug("invoke(), method: {}", method);
                log.debug("invoke(), args: {}", args);
                //System.out.println("realObject: " + realObject);
                //System.out.println("proxy: " + proxy); 无法打印！
                //System.out.println(proxy.getClass());

                log.debug(this + " do something begin...");
                //Object result = method.invoke(realObject, args);
                log.debug(this + " do something end...");

                return null;
            }
        };

        ClassLoader classLoader = clazz.getClassLoader();
        Class<?>[] interfaces = new Class<?>[] { clazz };
        Object proxyObj = Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
        log.debug("proxyObjClass: {}", proxyObj.getClass());
        return proxyObj;
    }

}
