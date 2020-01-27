package cn.caofanqi.security.web.filter;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 流控过滤器
 * 继承OncePerRequestFilter，保证一次请求只执行一次该过滤器
 *
 * @author caofanqi
 * @date 2020/1/20 23:29
 */
@Component
@SuppressWarnings("ALL")
public class RateLimiterFilter extends OncePerRequestFilter {

    /**
     * 限制每秒只允许一个请求
     */
    private RateLimiter rateLimiter = RateLimiter.create(1);


    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(rateLimiter.tryAcquire()) {
            filterChain.doFilter(request, response);
        }else {
            /*
             * 请求过Http状态码返回429
             */
            response.setContentType(MediaType.TEXT_HTML_VALUE);
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too Many Requests");
            response.getWriter().flush();
        }

    }

}
