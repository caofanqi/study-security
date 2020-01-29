package cn.caofanqi.security.web.interceptor;

import cn.caofanqi.security.pojo.doo.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ACL拦截器
 *
 * @author caofanqi
 * @date 2020/1/29 15:31
 */
@Slf4j
//@Component
public class AclInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        log.info("++++++4、授权++++++");

        UserDO user = (UserDO) request.getAttribute("user");
        if (user == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setHeader("WWW-Authenticate", "Basic realm=<authentication required>");
            return false;
        }

        if (!hasPermission(user.getPermissions(), request.getMethod())) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Forbidden");
            response.getWriter().flush();
            return false;
        }

        return true;
    }

    private boolean hasPermission(String permissions, String method) {

        if (StringUtils.equalsIgnoreCase(method, HttpMethod.GET.name())) {
            return StringUtils.containsIgnoreCase(permissions, "read");
        } else {
            return StringUtils.containsIgnoreCase(permissions, "write");
        }
    }

}
