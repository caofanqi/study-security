package cn.caofanqi.security.service;

import cn.caofanqi.security.pojo.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 用户业务层接口
 * @author caofanqi
 * @date 2020/1/20 13:51
 */
public interface UserService {


    List<UserDTO> query(String name);

    UserDTO get(Long id);

    UserDTO create(UserDTO userDTO);

    UserDTO update(UserDTO userDTO);

    void batchCreate(List<UserDTO> userDTOS);

    Map<String, String> login(UserDTO userDTO, HttpServletRequest request);
}
