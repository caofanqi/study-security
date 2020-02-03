package cn.caofanqi.security.config;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.RateLimitKeyGenerator;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.RateLimitUtils;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.properties.RateLimitProperties;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.DefaultRateLimiterErrorHandler;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.RateLimiterErrorHandler;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.support.DefaultRateLimitKeyGenerator;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.support.RateLimitExceededEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import javax.servlet.http.HttpServletRequest;

/**
 * 限流自定义配置 https://github.com/marcosbarbero/spring-cloud-zuul-ratelimit
 *
 * @author caofanqi
 * @date 2020/2/3 15:34
 */
@Slf4j
@Configuration
public class RateLimitConfig {


    /**
     * 自定义限流key生成规则
     */
    @Bean
    public RateLimitKeyGenerator ratelimitKeyGenerator(RateLimitProperties properties, RateLimitUtils rateLimitUtils) {
        return new DefaultRateLimitKeyGenerator(properties, rateLimitUtils) {
            @Override
            public String key(HttpServletRequest request, Route route, RateLimitProperties.Policy policy) {
                /*
                 * 可以根据自己的需求自定义
                 */
                return super.key(request, route, policy) + ":custom";
            }
        };
    }

    /**
     * 自定义错误处理
     */
    @Bean
    public RateLimiterErrorHandler rateLimitErrorHandler() {
        return new DefaultRateLimiterErrorHandler() {
            @Override
            public void handleSaveError(String key, Exception e) {
                // 自定义代码
                super.handleSaveError(key, e);
            }

            @Override
            public void handleFetchError(String key, Exception e) {
                // 自定义代码
                super.handleFetchError(key, e);
            }

            @Override
            public void handleError(String msg, Exception e) {
                // 自定义代码
                super.handleError(msg, e);
            }
        };
    }


    /**
     * 超速事件监听
     */
    @EventListener
    public void observe(RateLimitExceededEvent event) {
        log.info("监听到超速了...");
    }

}
