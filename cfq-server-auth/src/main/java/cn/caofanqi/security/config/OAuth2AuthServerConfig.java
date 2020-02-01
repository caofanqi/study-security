package cn.caofanqi.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import javax.annotation.Resource;

/**
 * OAuth2认证服务器配置类
 * 需要继承AuthorizationServerConfigurerAdapter类，覆盖里面三个configure方法
 * 并添加@EnableAuthorizationServer注解，指定当前应用做为认证服务器
 *
 * @author caofanqi
 * @date 2020/1/31 18:04
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthServerConfig extends AuthorizationServerConfigurerAdapter {


    @Resource
    private AuthenticationManager authenticationManager;


    /**
     * 配置授权服务器的安全性
     * checkTokenAccess:验证令牌需要什么条件，isAuthenticated()：需要经过身份认证。
     * 此处的passwordEncoders是为client secrets配置的。
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()").passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * 配置客户端服务
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                //配置在内存中
                .inMemory()
                //客户端应用的用户名
                .withClient("webApp")
                //客户端应用加密过的密码
                .secret(new BCryptPasswordEncoder().encode("123456"))
                //orderApp可以获取所有的权限集合，用于做ACL的权限控制
                .scopes("read", "write")
                //发出去令牌的有效期，单位秒
                .accessTokenValiditySeconds(3600)
                //可以访问哪些资源服务器
                .resourceIds("order-server")
                //授权的方式
                .authorizedGrantTypes("password")
                .and()
                //订单服务要访问资源服务器验证令牌，所以也需要配置相关信息
                .withClient("orderService")
                .secret(new BCryptPasswordEncoder().encode("123456"))
                .scopes("read", "write")
                .accessTokenValiditySeconds(3600)
                .resourceIds("order-server")
                .authorizedGrantTypes("password");

    }

    /**
     * 配置授权服务器终端的非安全特征
     * authenticationManager 校验用户信息是否合法
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

}
