package cn.caofanqi.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 网关资源服务器配置
 *
 * @author caofanqi
 * @date 2020/2/8 22:30
 */
@Configuration
@EnableResourceServer
public class GatewayResourceServerConfig extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("gateway");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //放过申请令牌的请求不需要身份认证
                .antMatchers("/token/**").permitAll()
                .anyRequest().authenticated();
    }

}
