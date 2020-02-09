package cn.caofanqi.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 401身份验证处理
 *
 * @author caofanqi
 * @date 2020/2/9 23:13
 */
@Slf4j
@Component
public class GatewayAuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        if(authException instanceof AccessTokenRequiredException){
            //是我们抛出的必须经过身份认证，说明没有传令牌，此时是匿名用户，请求经过了日志过滤器
            log.info("2、update log to 401");
        }else {
            //说明令牌是错误的，认证那里就不对，没有经过日志过滤器
            log.info("1、create log to 401");
        }

        //做一个标记，让日志过滤器知道已经更新日志了
        request.setAttribute("updateLog","yes");
        super.commence(request, response, authException);
    }
}
