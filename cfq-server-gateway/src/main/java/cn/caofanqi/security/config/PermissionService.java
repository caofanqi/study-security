package cn.caofanqi.security.config;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限控制
 * @author caofanqi
 * @date 2020/2/9 14:48
 */
public interface PermissionService {

    /**
     * 判断当前请求是否有权限
     * @param request 请求
     * @param authentication 认证相关信息
     * @return boolean
     */
    boolean hasPermission(HttpServletRequest request, Authentication authentication);

}
