package com.mikeheke.rpc.common.business;

import com.mikeheke.rpc.common.business.dto.UserDTO;
import com.mikeheke.rpc.common.business.vo.UserVO;

import java.util.List;

/**
 * 用户服务接口
 *
 * @author heke
 * @since 2024-03-31
 */
public interface IUserService {

    /**
     * 根据用户账号查询用户信息
     *
     * @param account
     * @return
     */
    UserVO findByAccount(String account);

    /**
     * 用户列表
     *
     * @param userDTO
     * @return
     */
    List<UserVO> listUser(UserDTO userDTO);

}
