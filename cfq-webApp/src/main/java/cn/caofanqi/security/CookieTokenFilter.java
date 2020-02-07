package cn.caofanqi.security;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 将cookie中的token取出放到请求头中
 *
 * @author caofanqi
 * @date 2020/2/6 0:34
 */
@Slf4j
@Component
public class CookieTokenFilter extends ZuulFilter {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletResponse response = requestContext.getResponse();

        String accessToken = getCookie("access_token");
        if (StringUtils.isNotBlank(accessToken)) {
            // 有值说明没过期
            requestContext.addZuulRequestHeader("Authorization", "bearer " + accessToken);
        } else {
            //使用refresh_token刷新令牌
            String refreshToken = getCookie("refresh_token");
            if (StringUtils.isNotBlank(refreshToken)) {
                //去认证服务器刷新令牌
                String oauthTokenUrl = "http://gateway.caofanqi.cn:9010/token/oauth/token";

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.setBasicAuth("webApp", "123456");

                MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                params.set("grant_type", "refresh_token");
                params.set("refresh_token", refreshToken);

                HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

                try {
                    ResponseEntity<TokenInfoDTO> refreshTokenResult = restTemplate.exchange(oauthTokenUrl, HttpMethod.POST, httpEntity, TokenInfoDTO.class);
                    requestContext.addZuulRequestHeader("Authorization", "bearer " + refreshTokenResult.getBody().getAccess_token());

                    Cookie accessTokenCookie = new Cookie("access_token", refreshTokenResult.getBody().getAccess_token());
                    accessTokenCookie.setMaxAge(refreshTokenResult.getBody().getExpires_in().intValue() - 5);
                    accessTokenCookie.setDomain("caofanqi.cn");
                    accessTokenCookie.setPath("/");
                    response.addCookie(accessTokenCookie);

                    Cookie refreshTokenCookie = new Cookie("refresh_token", refreshTokenResult.getBody().getRefresh_token());
                    refreshTokenCookie.setMaxAge(2592000);
                    refreshTokenCookie.setDomain("caofanqi.cn");
                    refreshTokenCookie.setPath("/");
                    response.addCookie(refreshTokenCookie);

                    log.info("refresh_token......");
                } catch (Exception e) {
                    //刷新令牌失败
                    log.info("token refresh fail");
                    requestContext.setSendZuulResponse(false);
                    requestContext.setResponseStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                    requestContext.getResponse().setContentType(MediaType.APPLICATION_JSON_VALUE);
                    requestContext.setResponseBody("{\"message\":\"token refresh fail\"}");
                }
            } else {
                //过期了，无法刷新令牌
                log.info("refresh_token not exist");
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                requestContext.getResponse().setContentType(MediaType.APPLICATION_JSON_VALUE);
                requestContext.setResponseBody("{\"message\":\"token refresh fail\"}");
            }
        }

        return null;
    }

    /**
     * 获取cookie的值
     */
    private String getCookie(String cookieName) {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (StringUtils.equals(cookieName, cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
