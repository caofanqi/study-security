package cn.caofanqi.security.config;

import cn.caofanqi.security.web.interceptor.AuditLogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * web配置类
 *
 * @author caofanqi
 * @date 2020/1/28 22:32
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


//    @Resource
//    private AuditLogInterceptor auditLogInterceptor;
//
//    /**
//     * 注册拦截器
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(auditLogInterceptor);
//    }

}
