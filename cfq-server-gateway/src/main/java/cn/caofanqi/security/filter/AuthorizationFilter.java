package cn.caofanqi.security.filter;

import cn.caofanqi.security.pojo.dto.TokenInfoDTO;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 授权过滤器
 *
 * @author caofanqi
 * @date 2020/2/3 0:15
 */
@Slf4j
@Component
public class AuthorizationFilter extends ZuulFilter implements InitializingBean {

    @Value("${permit.urls}")
    private String permitUrls;

    private Set<String> permitUrlSet = new HashSet<>();

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 4;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        log.info("++++++授权++++++");

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        if (isPermitUrl(request)) {
            return null;
        }

        /*
         * 需要认证
         */
        TokenInfoDTO tokenInfo = (TokenInfoDTO) request.getAttribute("tokenInfo");

        if (tokenInfo != null && tokenInfo.getActive()) {
            if (!hasPermission(tokenInfo, request)) {
                //没权限
                handleError(HttpStatus.FORBIDDEN.value(), requestContext);
            }
            //认证通过，向请求头中放入用户名，供微服务获取
            requestContext.addZuulRequestHeader("username", tokenInfo.getUser_name());
        } else {
            //没认证，或认证信息有误
            handleError(HttpStatus.UNAUTHORIZED.value(), requestContext);
        }

        return null;
    }

    private void handleError(int httpStatus, RequestContext requestContext) {
        requestContext.setResponseStatusCode(httpStatus);
        requestContext.getResponse().setContentType(MediaType.APPLICATION_JSON_VALUE);
        requestContext.setResponseBody("{\"message\":\"auth fail\"}");
        //不继续往下走了，返回
        requestContext.setSendZuulResponse(false);

    }


    private boolean isPermitUrl(HttpServletRequest request) {
        String uri = request.getRequestURI();
        for (String url : permitUrlSet) {
            if (pathMatcher.match(url, uri)) {
                // 不需要认证和权限，直接访问
                return true;
            }
        }

        return false;
    }


    private boolean hasPermission(TokenInfoDTO tokenInfo, HttpServletRequest request) {

        String[] scope = tokenInfo.getScope();
        if (StringUtils.equalsIgnoreCase(request.getMethod(), HttpMethod.GET.name())) {
            return ArrayUtils.contains(scope, "read");
        }

        if (StringUtils.equalsIgnoreCase(request.getMethod(), HttpMethod.POST.name())) {
            return ArrayUtils.contains(scope, "write");
        }

        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Collections.addAll(permitUrlSet, StringUtils.splitByWholeSeparatorPreserveAllTokens(permitUrls, ","));
    }

}
