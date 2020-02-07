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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public TokenInfoDTO me(HttpServletRequest request) {
        return (TokenInfoDTO) request.getSession().getAttribute("token");
    }


    /**
     * 回调方法
     * 接收认证服务器发来的授权码，并换取令牌
     *
     * @param code  授权码
     * @param state 请求授权服务器时发送的state
     */
    @GetMapping("/oauth/callback")
    public void oauthCallback(@RequestParam String code, String state, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String oauthTokenUrl = "http://gateway.caofanqi.cn:9010/token/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("webApp", "123456");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("code", code);
        params.set("grant_type", "authorization_code");
        params.set("redirect_uri", "http://web.caofanqi.cn:9000/oauth/callback");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        ResponseEntity<TokenInfoDTO> authResult = restTemplate.exchange(oauthTokenUrl, HttpMethod.POST, httpEntity, TokenInfoDTO.class);

        request.getSession().setAttribute("token", authResult.getBody().init());
        log.info("tokenInfo : {}", authResult.getBody());

        log.info("state :{}", state);
        //一般会根据state记录需要登陆时的路由
        response.sendRedirect("/");
    }

    /**
     * 退出
     */
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

}
