package com.mikeheke.rpc.server.business;

import com.mikeheke.rpc.common.business.IUserService;
import com.mikeheke.rpc.common.business.dto.UserDTO;
import com.mikeheke.rpc.common.business.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户服务接口实现类
 *
 * @author heke
 * @since 2024-03-31
 */
@Slf4j
public class UserServiceImpl implements IUserService {

    private static Map<String, UserVO> userMap = new HashMap<>();

    static {
        userMap.put("zhangsan", new UserVO(1L, "zhangsan", "张三", "Java开发工程师"));
        userMap.put("lisi", new UserVO(2L, "lisi", "李四", "前端开发工程师"));
        userMap.put("wangwu", new UserVO(3L, "wangwu", "王五", "服务端运维工程师"));
    }

    @Override
    public UserVO findByAccount(String account) {
        log.debug("findByAccount(), account: {}", account);
        UserVO userVO = userMap.get(account);
        log.debug("findByAccount(), account: {}, {}", account, userVO);
        return userVO;
    }

    @Override
    public List<UserVO> listUser(UserDTO userDTO) {
        log.debug("listUser(), {}", userDTO);
        List<UserVO> userVOList = new ArrayList<>();
        userVOList.add(userMap.get(userDTO.getAccount()));
        log.debug("listUser(), {}, {}", userDTO, userVOList);
        return userVOList;
    }

}
