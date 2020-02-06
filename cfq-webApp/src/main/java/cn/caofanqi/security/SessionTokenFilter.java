package cn.caofanqi.security;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 将session中的token取出放到请求头中
 *
 * @author caofanqi
 * @date 2020/2/6 0:34
 */
@Component
public class SessionTokenFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        TokenInfoDTO token = (TokenInfoDTO) request.getSession().getAttribute("token");

        if (token != null) {
            requestContext.addZuulRequestHeader("Authorization","bearer " + token.getAccess_token());
        }

        return null;
    }
}
