package cn.caofanqi.security.config;

import cn.caofanqi.security.pojo.dto.UserDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
            HttpSession session = request.getSession(false);
            String username = "anonymous";
            if (session != null) {
                UserDTO user = (UserDTO) session.getAttribute("user");
                if (user != null) {
                    username = user.getUsername();
                }
            }

            return Optional.of(username);
        };
    }

}
