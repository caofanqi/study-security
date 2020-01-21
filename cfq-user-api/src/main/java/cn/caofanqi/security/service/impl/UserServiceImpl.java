package cn.caofanqi.security.service.impl;

import cn.caofanqi.security.pojo.doo.UserDO;
import cn.caofanqi.security.pojo.dto.UserDTO;
import cn.caofanqi.security.repository.UserRepository;
import cn.caofanqi.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
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

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private UserRepository userRepository;


    /*
     * SQL拼接时，不要将参数一并拼接，要使用占位符替代，防止SQL注入。
     */
    @Override
    public List<UserDTO> query(String name) {

        // 1、
//        String sql = "SELECT * FROM user WHERE name = '" + name + "'";
//        log.info("执行的SQL为:{}",sql);
//        List<UserDO> queryResult = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(UserDO.class));

        // 2、
//        String sql = "SELECT * FROM user WHERE name = ? ";
//        List<UserDO> queryResult = jdbcTemplate.query(sql, new Object[]{name}, BeanPropertyRowMapper.newInstance(UserDO.class));

        // 3、
//        String sql = "SELECT * FROM user WHERE name = '" + name + "'";
//        Query nativeQuery = entityManager.createNativeQuery(sql, UserDO.class);
//        List<UserDO> queryResult = nativeQuery.getResultList();

        // 4、
//        String sql = "SELECT * FROM user WHERE name = ? ";
//        Query nativeQuery = entityManager.createNativeQuery(sql, UserDO.class);
//        nativeQuery.setParameter(1, name);
//        List<UserDO> queryResult = nativeQuery.getResultList();

        // 5、
        List<UserDO> queryResult = userRepository.findByName(name);

        List<UserDTO> result = queryResult.stream().map(UserDO::buildUserDTO).collect(Collectors.toList());

        return result;
    }

    @Override
    public UserDTO get(Long id) {
        Optional<UserDO> userOp = userRepository.findById(id);
        UserDO userDO = userOp.orElse(new UserDO());
        return userDO.buildUserDTO();
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userDTO,userDO);
        userRepository.save(userDO);
        userDTO.setId(userDO.getId());
        return userDTO;
    }


}
