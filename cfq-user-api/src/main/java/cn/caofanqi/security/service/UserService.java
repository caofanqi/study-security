package cn.caofanqi.security.service;

import cn.caofanqi.security.pojo.dto.UserDTO;

import java.util.List;

/**
 * 用户业务层接口
 * @author caofanqi
 * @date 2020/1/20 13:51
 */
public interface UserService {


    List<UserDTO> query(String name);

    UserDTO get(Long id);

    UserDTO create(UserDTO userDTO);

}
