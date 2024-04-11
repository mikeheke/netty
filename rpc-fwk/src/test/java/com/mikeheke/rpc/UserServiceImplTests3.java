package com.mikeheke.rpc;

import com.mikeheke.rpc.client.proxy.RemoteServiceProxyFactory;
import com.mikeheke.rpc.common.business.IUserService;
import com.mikeheke.rpc.common.business.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

/**
 * 用户服务单元测试
 *
 * @author heke
 * @since 2024-03-31
 */
@Slf4j
public class UserServiceImplTests3 {

    @Before
    public void before() {

    }

    @Test
    public void testFindByAccount() {
        String account = "zhangsan";
        IUserService proxyService =
                (IUserService) RemoteServiceProxyFactory.getProxyServiceInstance(IUserService.class);
        UserVO userVO = proxyService.findByAccount(account);
        log.debug("{}", userVO);
    }
}
