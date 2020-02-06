package cn.caofanqi.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * 模拟前端服务器
 *
 * @author caofanqi
 * @date 2020/2/5 16:34
 */
@Slf4j
@RestController
@EnableZuulProxy
@SpringBootApplication
public class WebAppApplication {

    private RestTemplate restTemplate = new RestTemplate();


    public static void main(String[] args) {
        SpringApplication.run(WebAppApplication.class, args);
    }

    /**
     * 获取当前认证的token信息
     */
    @GetMapping("/me")
    public TokenInfoDTO me(HttpServletRequest request){
        return (TokenInfoDTO)request.getSession().getAttribute("token");
    }


    /**
     * 登陆方法
     * 向认证服务器获取令牌，将token信息放到session中
     */
    @PostMapping("/login")
    public void login(@RequestBody UserDTO userDTO, HttpServletRequest request) {

        String oauthTokenUrl = "http://gateway.caofanqi.cn:9010/token/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("webApp", "123456");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("username", userDTO.getUsername());
        params.set("password", userDTO.getPassword());
        params.set("grant_type", "password");
        params.set("scope", "read write");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        ResponseEntity<TokenInfoDTO> response = restTemplate.exchange(oauthTokenUrl, HttpMethod.POST, httpEntity, TokenInfoDTO.class);

        request.getSession().setAttribute("token", response.getBody());
        log.info("tokenInfo : {}",response.getBody());

    }

    /**
     *  退出
     */
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request){
        request.getSession().invalidate();
    }

}
