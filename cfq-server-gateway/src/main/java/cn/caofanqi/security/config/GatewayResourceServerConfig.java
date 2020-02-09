package cn.caofanqi.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import javax.annotation.Resource;

/**
 * 网关资源服务器配置
 *
 * @author caofanqi
 * @date 2020/2/8 22:30
 */
@Configuration
@EnableResourceServer
public class GatewayResourceServerConfig extends ResourceServerConfigurerAdapter {


    @Resource
    private GatewayWebSecurityExpressionHandler gatewayWebSecurityExpressionHandler;

    @Resource
    private GatewayAccessDeniedHandler gatewayAccessDeniedHandler;

    @Resource
    private GatewayAuthenticationEntryPoint gatewayAuthenticationEntryPoint;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                .resourceId("gateway")
                //表达式处理器
                .expressionHandler(gatewayWebSecurityExpressionHandler)
                //403访问拒绝处理器
                .accessDeniedHandler(gatewayAccessDeniedHandler)
                //401身份验证异常处理
                .authenticationEntryPoint(gatewayAuthenticationEntryPoint);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                //将限流过滤器添在SpringSecurity过滤器链之前
                .addFilterBefore(new GatewayRateLimitFilter(), SecurityContextPersistenceFilter.class)
                //将日志过滤器添在授权过滤器之前
                .addFilterBefore(new GatewayAuditLogFilter(), ExceptionTranslationFilter.class)
                .authorizeRequests()
                //放过申请令牌的请求不需要身份认证
                .antMatchers("/token/**").permitAll()
                //其他所有请求是否有权限，要通过permissionService的hasPermission方法进行判断
                .anyRequest().access("#permissionService.hasPermission(request,authentication)");
    }

}