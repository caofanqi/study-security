package cn.caofanqi.security.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 查询用户信息
 * @author caofanqi
 * @date 2020/2/1 3:41
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {


    /**
     *  此处为了简便，没有连接数据库，不管什么用户名，只要密码是123456就会通过验证
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return User.withUsername(username)
                .password(new BCryptPasswordEncoder().encode("123456"))
                .authorities("ROLE_ADMIN").build();
    }

}
