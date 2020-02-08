package cn.caofanqi.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

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

    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 配置授权服务器的安全性
     * checkTokenAccess:验证令牌需要什么条件，isAuthenticated()：需要经过身份认证。
     * 此处的passwordEncoders是为client secrets配置的。
     * tokenKeyAccess: 设置对获取令牌签名的验证密钥，要求的权限
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .checkTokenAccess("isAuthenticated()")
                .passwordEncoder(new BCryptPasswordEncoder())
                .tokenKeyAccess("isAuthenticated()");
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
     * userDetailsService:配合刷新令牌使用
     * tokenEnhancer:令牌增强器
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
//                .tokenStore(new JdbcTokenStore(dataSource))
                .tokenStore(new JwtTokenStore(jwtTokenEnhancer()))
                .tokenEnhancer(jwtTokenEnhancer())
                .userDetailsService(userDetailsService);
    }


    /**
     *  jwt令牌增强器，使用KeyPair提高安全度。
     *  声明为spring bean是为了让资源服务器可以获取令牌签名的验证密钥 ，TokenKeyEndpoint类中的 /oauth/token_key
     */
    @Bean
    public JwtAccessTokenConverter jwtTokenEnhancer() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //jwtAccessTokenConverter.setSigningKey("123456");
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("cfq.key"), "123456".toCharArray());
        jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("cfq"));
        return jwtAccessTokenConverter;
    }

}
