package cn.caofanqi.security.web.interceptor;

import cn.caofanqi.security.pojo.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * ACL拦截器
 *
 * @author caofanqi
 * @date 2020/1/29 15:31
 */
@Slf4j
//@Component
public class AclInterceptor extends HandlerInterceptorAdapter  implements InitializingBean {

    @Value("${permit.urls}")
    private String permitUrls;

    private Set<String> permitUrlSet = new HashSet<>();

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        log.info("++++++4、授权++++++");

        if (isPermitUrl(request)){
            return true;
        }

        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
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


    private boolean isPermitUrl(HttpServletRequest request) {
        String uri = request.getRequestURI();
        for (String url : permitUrlSet){
            if (pathMatcher.match(url,uri)){
                // 不需要认证和权限，直接访问
                return true;
            }
        }

        return false;
    }

    private boolean hasPermission(String permissions, String method) {

        if (StringUtils.equalsIgnoreCase(method, HttpMethod.GET.name())) {
            return StringUtils.containsIgnoreCase(permissions, "read");
        } else {
            return StringUtils.containsIgnoreCase(permissions, "write");
        }
    }

    @Override
    public void afterPropertiesSet() {
        Collections.addAll(permitUrlSet,StringUtils.splitByWholeSeparatorPreserveAllTokens(permitUrls,","));
    }

}
