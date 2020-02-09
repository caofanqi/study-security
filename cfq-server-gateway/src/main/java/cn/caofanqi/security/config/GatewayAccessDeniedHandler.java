package cn.caofanqi.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 403 拒绝访问处理器
 *
 * @author caofanqi
 * @date 2020/2/9 22:37
 */
@Slf4j
@Component
public class GatewayAccessDeniedHandler extends OAuth2AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {

        log.info("2、update log to 403");
        //做一个标记，让日志过滤器知道已经更新日志了
        request.setAttribute("updateLog","yes");
        //这里可以自定义返回内容，我们就不改了，使用OAuth2AccessDeniedHandler默认的
        super.handle(request, response, authException);

    }
}
