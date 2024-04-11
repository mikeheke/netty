package com.mikeheke.rpc;

import com.mikeheke.rpc.common.business.IUserService;
import com.mikeheke.rpc.common.business.dto.UserDTO;
import com.mikeheke.rpc.common.business.vo.UserVO;
import com.mikeheke.rpc.server.business.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

/**
 * 用户服务单元测试
 *
 * @author heke
 * @since 2024-03-31
 */
@Slf4j
public class UserServiceImplTests {

    private IUserService userService = new UserServiceImpl();

    @Test
    public void testFindByAccount() {
        String account = "zhangsan";
        UserVO userVO = userService.findByAccount(account);
        log.debug("{}", userVO);
    }

    @Test
    public void testListUser() {
        UserDTO userDTO = new UserDTO("zhangsan");
        List<UserVO> userList = userService.listUser(userDTO);
        log.debug("userList: {}", userList);
    }

}
