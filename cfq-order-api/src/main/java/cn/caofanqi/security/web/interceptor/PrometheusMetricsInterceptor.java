package cn.caofanqi.security.web.interceptor;

import io.prometheus.client.Counter;
import io.prometheus.client.Summary;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 监控指标拦截器
 *
 * @author caofanqi
 * @date 2020/2/15 12:22
 */
@Component
public class PrometheusMetricsInterceptor extends HandlerInterceptorAdapter {


    @Resource
    private Counter requestCounter;

    @Resource
    private Summary requestLatency;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        request.setAttribute("start",System.currentTimeMillis());
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        requestCounter.labels(request.getRequestURI(),request.getMethod(),String.valueOf(response.getStatus())).inc();

        long duration = System.currentTimeMillis() - (Long) request.getAttribute("start");

        requestLatency.labels(request.getRequestURI(),request.getMethod(),String.valueOf(response.getStatus())).observe(duration);
    }
}
