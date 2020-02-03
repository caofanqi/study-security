package cn.caofanqi.security.filter;

import cn.caofanqi.security.pojo.dto.TokenInfoDTO;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * OAuth2认证过滤器
 *
 * @author caofanqi
 * @date 2020/2/2 22:54
 */
@Slf4j
@Component
public class OAuth2Filter extends ZuulFilter {


    private RestTemplate restTemplate = new RestTemplate();


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        log.info("++++++认证++++++");

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        if (StringUtils.startsWith(request.getRequestURI(), "/token")) {
            //发往认证服务器的请求直接放行
            return null;
        }

        String authorization = request.getHeader("Authorization");

        if (StringUtils.isBlank(authorization)) {
            //没有Authorization头的直接放行
            return null;
        }

        if (!StringUtils.startsWithIgnoreCase(authorization, "bearer ")) {
            //不是OAuth认证的直接放行
            return null;
        }

        try {
            request.setAttribute("tokenInfo", getTokenInfo(authorization));
        } catch (Exception e) {
            log.info("check token fail :", e);
        }

        return null;
    }

    /**
     *  向认证服务器校验token的有效性
     */
    private TokenInfoDTO getTokenInfo(String authorization) {

        String token = StringUtils.substringAfter(authorization, "bearer ");
        String checkTokenEndpointUrl = "http://127.0.0.1:9020/oauth/check_token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("gateway", "123456");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("token", token);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        ResponseEntity<TokenInfoDTO> response = restTemplate.exchange(checkTokenEndpointUrl, HttpMethod.POST, httpEntity, TokenInfoDTO.class);
        TokenInfoDTO tokenInfo = response.getBody();

        log.info("tokenInfo : {}", tokenInfo);

        return tokenInfo;

    }


}
