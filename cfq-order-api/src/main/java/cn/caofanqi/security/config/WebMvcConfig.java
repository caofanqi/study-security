package cn.caofanqi.security.config;

import cn.caofanqi.security.web.interceptor.PrometheusMetricsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * webmvc配置类
 *
 * @author caofanqi
 * @date 2020/2/15 12:27
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Resource
    private PrometheusMetricsInterceptor prometheusMetricsInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(prometheusMetricsInterceptor).addPathPatterns("/**");
    }
}
