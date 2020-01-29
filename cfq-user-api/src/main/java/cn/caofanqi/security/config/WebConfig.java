package cn.caofanqi.security.config;

import cn.caofanqi.security.web.interceptor.AclInterceptor;
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
//    @Resource
//    private AclInterceptor aclInterceptor;
//
//    /**
//     * 注册拦截器，拦截器的执行顺序取决于add顺序
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(auditLogInterceptor);
//        registry.addInterceptor(aclInterceptor);
//    }

}
