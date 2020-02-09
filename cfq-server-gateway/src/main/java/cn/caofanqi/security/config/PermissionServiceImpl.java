package cn.caofanqi.security.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限控制实现类
 *
 * @author caofanqi
 * @date 2020/2/9 14:51
 */
@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {

    /**
     * 在这里可以去安全中心，获取该请求是否具有相应的权限
     *
     * @param request        请求
     * @param authentication 认证相关信息
     */
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        //这里我们就不写具体的权限判断了，采用随机数模拟,百分之50的机率可以访问
        log.info("request uri : {}", request.getRequestURI());
        log.info("authentication : {}", ReflectionToStringBuilder.toString(authentication));

        /*
         * 如果是没传令牌的话，Authentication 是 AnonymousAuthenticationToken
         * 如果传入令牌经过身份认证 Authentication 是 OAuth2Authentication
         */
        if (authentication instanceof AnonymousAuthenticationToken){
            //要求必须通过身份认证
            throw new AccessTokenRequiredException(null);
        }

        boolean  hasPermission =  RandomUtils.nextInt() % 2 == 0;
        log.info("hasPermission is :{}",hasPermission);
        return hasPermission;
    }

}