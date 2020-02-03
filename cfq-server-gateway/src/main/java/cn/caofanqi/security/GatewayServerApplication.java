package cn.caofanqi.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 网关
 *
 * @author caofanqi
 * @date 2020/2/2 15:36
 */
@EnableZuulProxy
@EnableJpaAuditing
@SpringBootApplication
public class GatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

}
