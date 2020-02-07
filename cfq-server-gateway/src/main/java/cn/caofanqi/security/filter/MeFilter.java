package cn.caofanqi.security.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户判断当前用户是否认证
 *
 * @author caofanqi
 * @date 2020/2/7 21:43
 */
@Component
public class MeFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 6;
    }

    /**
     *  只处理/user/me请求
     */
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        return StringUtils.equals(request.getRequestURI(),"/user/me");
    }

    /**
     *  判断请求头中有没有我们放入的username，后直接返回，不继续往下走
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        String username = requestContext.getZuulRequestHeaders().get("username");
        if(StringUtils.isNotBlank(username)) {
            requestContext.setResponseBody("{\"username\":\""+username+"\"}");
        }
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(HttpStatus.OK.value());
        requestContext.getResponse().setContentType(MediaType.APPLICATION_JSON_VALUE);

        return null;
    }
}
