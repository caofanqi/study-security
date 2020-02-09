package cn.caofanqi.security.config;

import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 将权限服务表达式添加到评估上下文中
 *
 * @author caofanqi
 * @date 2020/2/9 14:58
 */
@Component
public class GatewayWebSecurityExpressionHandler extends OAuth2WebSecurityExpressionHandler {

    @Resource
    private PermissionService permissionService;

    @Override
    protected StandardEvaluationContext createEvaluationContextInternal(Authentication authentication, FilterInvocation invocation) {
        StandardEvaluationContext sc = super.createEvaluationContextInternal(authentication, invocation);
        sc.setVariable("permissionService",permissionService);
        return sc;
    }

}