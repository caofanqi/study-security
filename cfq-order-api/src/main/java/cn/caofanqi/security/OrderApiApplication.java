package cn.caofanqi.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * 订单微服务
 *
 * @author caofanqi
 * @date 2020/1/31 14:22
 */
@EnableResourceServer
@SpringBootApplication
public class OrderApiApplication {


    public static void main(String[] args) {
        SpringApplication.run(OrderApiApplication.class,args);
    }

    /**
     *  将OAuth2RestTemplate声明为spring bean，OAuth2ProtectedResourceDetails，OAuth2ClientContext springboot会自动帮我们注入
     */
    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context){
        return new OAuth2RestTemplate(resource,context);
    }

}
