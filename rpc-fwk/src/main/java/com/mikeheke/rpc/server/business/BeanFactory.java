package com.mikeheke.rpc.server.business;

import com.mikeheke.rpc.common.business.IUserService;

import java.util.HashMap;
import java.util.Map;

/**
 * bean工厂
 *
 * @author heke
 * @since 2024-04-01
 */
public class BeanFactory {

    private static final Map<String, Object> beanMap = new HashMap<>();

    static {
        beanMap.put(IUserService.class.getName(), new UserServiceImpl());
    }

    /**
     * 根据接口名获取bean实例
     *
     * @param interfaceName
     * @return
     */
    public static Object getBean(String interfaceName) {
        return beanMap.get(interfaceName);
    }

}
