package cn.caofanqi.security.service.impl;

import cn.caofanqi.security.pojo.doo.UserDO;
import cn.caofanqi.security.pojo.dto.UserDTO;
import cn.caofanqi.security.repository.UserRepository;
import cn.caofanqi.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户业务层实现类
 *
 * @author caofanqi
 * @date 2020/1/20 13:52
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {


    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private UserRepository userRepository;

    @Override
    public List<UserDTO> query(String name) {

//
//        String sql = "SELECT * FROM user WHERE name = '" + name + "'";
//        List<UserDO> queryResult = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(UserDO.class));

//        String sql = "SELECT * FROM user WHERE name = ? ";
//        List<UserDO> queryResult = jdbcTemplate.query(sql, new Object[]{name}, BeanPropertyRowMapper.newInstance(UserDO.class));
//        log.info("执行的sql: [{}]", sql);

        List<UserDO> queryResult = userRepository.findByName(name);

        List<UserDTO> result = queryResult.stream().map(UserDO::buildUserDTO).collect(Collectors.toList());

        return result;
    }
}
