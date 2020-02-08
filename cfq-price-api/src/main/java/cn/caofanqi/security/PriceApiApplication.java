package cn.caofanqi.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * 价格微服务
 *
 * @author caofanqi
 * @date 2020/1/31 14:40
 */
@EnableResourceServer
@SpringBootApplication
public class PriceApiApplication {


    public static void main(String[] args) {
        SpringApplication.run(PriceApiApplication.class,args);
    }


}
