package cn.caofanqi.security.service.impl;

import cn.caofanqi.security.pojo.doo.UserDO;
import cn.caofanqi.security.pojo.dto.UserDTO;
import cn.caofanqi.security.repository.UserRepository;
import cn.caofanqi.security.service.UserService;
import com.google.common.collect.Maps;
import com.lambdaworks.crypto.SCryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
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
    @Transactional(rollbackFor = Exception.class)
    public UserDTO create(UserDTO userDTO) {
        /*
         * 将密码加密成密文
         */
        userDTO.setPassword(BCrypt.hashpw(userDTO.getPassword(),BCrypt.gensalt()));
//        userDTO.setPassword(SCryptUtil.scrypt(userDTO.getPassword(),2 << 14,8,1));
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userDTO, userDO);
        userRepository.save(userDO);
        userDTO.setId(userDO.getId());
        return userDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO update(UserDTO userDTO) {
        Optional<UserDO> userOp = userRepository.findById(userDTO.getId());
        UserDO userDO = userOp.orElseThrow(() -> new RuntimeException("该用户不存在"));
        BeanUtils.copyProperties(userDTO, userDO);
        return userDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchCreate(List<UserDTO> userDTOS) {
        List<UserDO> userList = userDTOS.stream().map(userDTO -> {
            userDTO.setPassword(BCrypt.hashpw(userDTO.getPassword(),BCrypt.gensalt()));
//        userDTO.setPassword(SCryptUtil.scrypt(userDTO.getPassword(),2 << 14,8,1));
            UserDO userDO = new UserDO();
            BeanUtils.copyProperties(userDTO, userDO);
            return userDO;
        }).collect(Collectors.toList());
        userRepository.saveAll(userList);
    }

    @Override
    public Map<String, String> login(UserDTO userDTO, HttpServletRequest request) {

        Map<String,String> result = Maps.newHashMap();

        UserDO userDO = userRepository.findByUsername(userDTO.getUsername());
        if (userDO == null){
            result.put("message","用户名错误");
        }else if (!BCrypt.checkpw(userDTO.getPassword(),userDO.getPassword())){
            result.put("message","密码错误");
        }else {
            HttpSession session = request.getSession(false);
            //将之前的session失效掉
            if (session != null){
                session.invalidate();
            }
            //将用户信息放到新的session中
            request.getSession(true).setAttribute("user",userDO.buildUserDTO());

            result.put("message","登陆成功");
        }

        return result;
    }


}
