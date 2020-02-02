package cn.caofanqi.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

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

    @Resource
    private DataSource dataSource;

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
        //从数据库中读取
        clients.jdbc(dataSource);
    }

    /**
     * 配置授权服务器终端的非安全特征
     * authenticationManager 校验用户信息是否合法
     * tokenStore：token存储
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(new JdbcTokenStore(dataSource));
    }

}
