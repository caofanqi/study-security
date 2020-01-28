package cn.caofanqi.security.config;

import cn.caofanqi.security.pojo.doo.UserDO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * JPA相关配置
 *
 * @author caofanqi
 * @date 2020/1/29 1:13
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {

    /**
     * 获取当前登陆用户
     */
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            UserDO user = (UserDO) request.getAttribute("user");
            if (user != null) {
                return Optional.of(user.getUsername());
            } else {
                return Optional.of("anonymous");
            }
        };
    }

}
