package cn.caofanqi.security.web.filter;

import cn.caofanqi.security.pojo.doo.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ACL过滤器,这需要审计也是基于Filter实现的
 *
 * @author caofanqi
 * @date 2020/1/29 15:04
 */
@Slf4j
@Order(4)
@Component
@SuppressWarnings("ALL")
public class AclFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("++++++4、授权++++++");

        /*
         * 要求请求都必须经过认证才能访问
         */
        UserDO user = (UserDO) request.getAttribute("user");
        if (user == null) {
            //说明没有进行认证，返回401和WWW-Authenticate，让浏览器弹出输入框
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setHeader("WWW-Authenticate", "Basic realm=<authentication required>");
            return;
        }

        /*
         * 要求有对应的权限才可以进行访问
         */
        if (!hasPermission(user.getPermissions(), request.getMethod())) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Forbidden");
            response.getWriter().flush();
            return;
        }


        filterChain.doFilter(request, response);

    }

    private boolean hasPermission(String permissions, String method) {

        if (StringUtils.equalsIgnoreCase(method, HttpMethod.GET.name())) {
            //要有读权限
            return StringUtils.containsIgnoreCase(permissions, "read");
        } else {
            //要有写权限
            return StringUtils.containsIgnoreCase(permissions, "write");
        }

    }

}
