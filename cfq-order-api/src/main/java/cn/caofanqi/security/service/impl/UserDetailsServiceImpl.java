package cn.caofanqi.security.service.impl;

import cn.caofanqi.security.pojo.doo.UserDO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 查找用户信息
 *
 * @author caofanqi
 * @date 2020/2/2 0:34
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO userDO = new UserDO();
        userDO.setId(1234L);
        userDO.setUsername(username);
        userDO.setPassword(new BCryptPasswordEncoder().encode("123456"));
        return userDO;
    }
}
